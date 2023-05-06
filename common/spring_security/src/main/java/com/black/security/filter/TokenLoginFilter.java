package com.black.security.filter;

import com.alibaba.fastjson2.JSON;
import com.black.security.entity.SecurityUser;
import com.black.security.entity.SysUser;
import com.black.security.security.TokenManager;
import com.black.utils.utils.R;
import com.black.utils.utils.ResponseUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author black
 * @date 2023/4/20 9:23
 * 认证过滤器
 */
public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {

    private TokenManager tokenManager;

    private RedisTemplate redisTemplate;

    private AuthenticationManager authenticationManager;

    public TokenLoginFilter(TokenManager tokenManager, RedisTemplate redisTemplate, AuthenticationManager authenticationManager){
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
        this.authenticationManager = authenticationManager;
        this.setPostOnly(false);
        //设置登录路径
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/system/index/login","POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            //从请求表单中获取用户数据
            SysUser sysUser = new ObjectMapper().readValue(request.getInputStream(), SysUser.class);
            //设置用户名、密码、权限
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(sysUser.getUsername(),sysUser.getPassword()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //成功
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SecurityUser user = (SecurityUser)authResult.getPrincipal();
        Integer userId = user.getCurrentUserInfo().getId();
        String username = user.getCurrentUserInfo().getUsername();
        //根据当前登录的用户的用户名创建token
        String token = tokenManager.createToken(userId, username);
        //将用户权限放入redis缓存中
        redisTemplate.opsForValue().set(username, JSON.toJSONString(user.getAuthorities()));

        Map<String,Object> data = new HashMap<>();
        data.put("token",token);
        ResponseUtil.out(response,R.ok(data));
    }

    //失败
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        ResponseUtil.out(response, R.fail("用户名或密码错误"));
    }
}
