package com.black.security.filter;

import com.alibaba.fastjson2.JSON;
import com.black.security.security.TokenManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author black
 * @date 2023/4/20 10:19
 * 授权过滤器
 */
public class TokenAuthFilter extends BasicAuthenticationFilter {

    private TokenManager tokenManager;

    private RedisTemplate redisTemplate;

    public TokenAuthFilter(AuthenticationManager authenticationManager,TokenManager tokenManager, RedisTemplate redisTemplate) {
        super(authenticationManager);
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.info("uri:"+request.getRequestURI());
        //如果是登录接口直接放行
        if("/system/admin/login".equals(request.getRequestURI())){
            chain.doFilter(request,response);
            return;
        }
        //获取当前认证成功用户权限信息
        UsernamePasswordAuthenticationToken authRequest = getAuthentication(request);
        //判断信息不为空，则放入权限上下文
        if (authRequest != null){
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authRequest);
            SecurityContextHolder.setContext(context);
        }
        chain.doFilter(request,response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        //获取token
        String token = request.getHeader("token");
        //如果token不为空,则获取username
        if(token != null){
            String username = tokenManager.getUserName(token);
            //通过username获取redis中的权限数据
            String permissions = (String)redisTemplate.opsForValue().get(username);
            List<Map> mapList = JSON.parseArray(permissions,Map.class);
            //将权限数据转换为Collection<? extends GrantedAuthority>
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            mapList.forEach(permission ->{
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority((String)permission.get("authority"));
                authorities.add(authority);
            });

            //返回UsernamePasswordAuthenticationToken
            return new UsernamePasswordAuthenticationToken(username,token,authorities);
        }
        return null;
    }

}
