package com.black.security.config;

import com.black.security.filter.TokenAuthFilter;
import com.black.security.filter.TokenLoginFilter;
import com.black.security.security.DefaultPasswordEncoder;
import com.black.security.security.TokenLogoutHandler;
import com.black.security.security.TokenManager;
import com.black.security.security.UnAuthEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * @author black
 * @date 2023/4/20 10:52
 * 核心配置类
 */
@Configuration
@EnableWebSecurity  //开启security默认行为
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启注解功能，默认禁用注解
public class TokenWebSecurityConfig {

    @Resource
    private TokenManager tokenManager;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private DefaultPasswordEncoder defaultPasswordEncoder;

    @Resource
    private UserDetailsService userDetailsService;

    /**
     * 配置设置
     * @param http
     * @return
     * @throws Exception
     * 设置退出的地址和token，redis操作地址
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.exceptionHandling()
                .authenticationEntryPoint(new UnAuthEntryPoint());  //无权限访问处理
        http.csrf().disable()
                .cors().and()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and().logout().logoutUrl("system/index/logout")  //退出url
                .addLogoutHandler(new TokenLogoutHandler(tokenManager,redisTemplate)).and() //退出处理器设置
                .addFilter(new TokenLoginFilter(tokenManager,redisTemplate,authenticationManager()))  //认证过滤器
                //TokenAuthenticationFilter放到UsernamePasswordAuthenticationFilter的前面，这样做是为了除了登录的时候去查询数据库外，其他时候都用token进行认证
                .addFilterBefore(new TokenAuthFilter(authenticationManager(),tokenManager,redisTemplate), UsernamePasswordAuthenticationFilter.class)
                .httpBasic();

        //禁用session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }

    /**
     * 基于数据库定义UserDetailService
     * @param httpSecurity
     * @return
     * @throws Exception
     * 配置自定义密码加密
     */
    @Bean
    AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        AuthenticationManager authenticationManager = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(defaultPasswordEncoder)
                .and()
                .build();
        return authenticationManager;
    }

    /**
     * 忽略请求
     * @return
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/favicon.ico","/swagger-resources/**","/webjars/**","/v2/api-docs","/swagger-ui.html/**","/doc.html");
    }

    @Bean
    protected AuthenticationManager authenticationManager() {
        return new ProviderManager();
    }

}
