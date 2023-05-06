package com.black.security.security;

import io.jsonwebtoken.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author black
 * @date 2023/4/19 17:31
 * token生成工具类
 */
@Component
public class TokenManager {

    //token有效时长
    private static long tokenExpiration = 24 * 60 * 60 * 1000;

    //编码密钥
    private static String tokenSignKey = "black";

    //根据用户名生成token
    public static String createToken(Integer userId, String username){
        String token = Jwts.builder().setSubject("AUTH-USER")
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .claim("userId",userId)
                .claim("userName",username)
                .signWith(SignatureAlgorithm.HS512,tokenSignKey)
                .compressWith(CompressionCodecs.GZIP).compact();
        return token;
    }

    //根据token字符串得到用户信息
    public static Integer getUserId(String token){
        try {
            if(StringUtils.isEmpty(token)) return null;

            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
            Integer userId = (Integer)claims.get("userId");
            return userId;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static String getUserName(String token){
        try {
            if(StringUtils.isEmpty(token)) return null;

            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
            String userName = (String)claims.get("userName");
            return userName;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
