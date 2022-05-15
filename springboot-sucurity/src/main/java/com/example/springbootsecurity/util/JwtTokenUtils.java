package com.example.springbootsecurity.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;
import java.util.HashMap;

/**
 * <h3>springboot-study</h3>
 * <p>JWT工具类</p>
 *
 * @author : ZhangYuJie
 * @date : 2022-05-15 16:54
 **/
@Slf4j
public class JwtTokenUtils {

    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    private static final String SECRET = "jwtsecretdemo";
    private static final String ISS = "echisan";

    /**
     * 过期时间是3600秒，既是1个小时
     */
    private static final long EXPIRATION = 3600L;

    /**
     * 选择了记住我之后的过期时间为7天
     */
    private static final long EXPIRATION_REMEMBER = 604800L;

    // 添加角色的key
    private static final String ROLE_CLAIMS = "rol";

    /**
     * 修改一下创建token的方法
     *
     * @param username
     * @param role
     * @param isRememberMe
     * @return
     */
    public static String createToken(String username, String role, boolean isRememberMe) {
        String token = null;
        try {
            long expiration = isRememberMe ? EXPIRATION_REMEMBER : EXPIRATION;
            HashMap<String, Object> map = new HashMap<>();
            map.put(ROLE_CLAIMS, role);
            token = Jwts.builder()
                    .signWith(SignatureAlgorithm.HS512, SECRET)
                    // 这里要早set一点，放到后面会覆盖别的字段
                    .setClaims(map)
                    .setIssuer(ISS)
                    .setSubject(username)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                    .compact();
        } catch (ExpiredJwtException e) {
            log.error("token异常：{}", e.getMessage(), e);
        }
        return token;
    }


    /**
     * 从token中获取用户名
     *
     * @param token
     * @return
     */
    public static String getUsername(String token) {
        return getTokenBody(token).getSubject();
    }

    /**
     * 从token中获取roles
     *
     * @param token
     * @return
     */
    public static String getUserRole(String token) {
        return (String) getTokenBody(token).get(ROLE_CLAIMS);
    }

    /**
     * 是否已过期
     *
     * @param token
     * @return
     */
    public static boolean isExpiration(String token) {
        return getTokenBody(token).getExpiration().before(new Date());
    }

    private static Claims getTokenBody(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String user = encoder.encode("admin");
        System.out.println(user);
    }
}
