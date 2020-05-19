package com.knowology.util;

import io.jsonwebtoken.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author : Conan
 * @Description
 * @date : 2019-05-20 15:05
 **/

public class JwtUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    /**
     * token桶，存储user的token，token校验时验证user的token和桶里的token是否一致，不一致说明该user异地登陆，旧token废弃了
     */
    private static Map<String,String> tokenBucket = new HashMap<>();
    /**
     * httpheader取出token
     */
    public static final String AUTHORIZATION = "Authorization";

    private static final String SECRET = "ZKGL";

    private static final String USERNAME = "username";

    private static final String FULL_NAME = "fullName";

    private static final String USER_ID = "userId";

    private static final String TOKEN_PREFIX = "Bearer";

    /**
     * 生成token
     *
     * @param username
     * @return
     */
    public static String generateToken(String username,String fullName,Integer userId) {
        HashMap<String, Object> map = new HashMap<>(2);
        //you can put any data in the map
        map.put(USERNAME, username);
        map.put(FULL_NAME,fullName);
        map.put(USER_ID,userId);
        //expiration time is 24 hour
        String jwt = Jwts.builder()
                .setClaims(map)
                .setExpiration(new Date(System.currentTimeMillis() + 3600_000L * 24))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        return TOKEN_PREFIX + jwt;
    }

    /**
     * 校验token有效性(包含是否过期) 有效true，无效false
     *
     * @param token
     * @return
     */
    public static boolean validateToken(String token) {
        try {
            final Claims tokenBody = getTokenBody(token);
            if (tokenBody == null || tokenBody.getExpiration() == null) {
                return false;
            }
            // token未过期返回true，其实在getTokenBody的时候已经判断了token是否过期
            return new Date().before(tokenBody.getExpiration());
        } catch (ExpiredJwtException e) {
            //token已过期
            logger.warn("Token is expired");
            return false;
        }
    }

    /**
     * // parseClaimsJws 会抛出异常,接口声明有抛出但是实现类没有显示抛
     *
     * @param token
     * @return
     * @throws ExpiredJwtException
     * @throws UnsupportedJwtException
     * @throws MalformedJwtException
     * @throws SignatureException
     * @throws IllegalArgumentException
     */
    public static Claims getTokenBody(String token) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {
        // parse the token.
        if (StringUtils.isBlank(token)) {
            return null;
        }
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody();
        System.out.println("token body is:" + claims);

        return claims;
    }

    /**
     * 获取username
     * @param token
     * @return
     */
    public static String getUsername(String token) {
        return  (String) Optional.ofNullable(getTokenBody(token)).map(c -> c.get(USERNAME)).orElse(null);
    }

    /**
     * 从token中获取真实姓名
     * @param token
     * @return
     */
    public static String getFullName(String token) {
        return  (String) Optional.ofNullable(getTokenBody(token)).map(c -> c.get(FULL_NAME)).orElse(null);
    }
    /**
     * 从token中获取UserId
     * @param token
     * @return
     */
    public static Integer getUserId(String token) {
        return  (Integer) Optional.ofNullable(getTokenBody(token)).map(c -> c.get(USER_ID)).orElse(null);
    }

    public static void addTokenToBucket(String username,String token) {
        tokenBucket.put(username,token);
    }

    public static String getTokenFromBucket(String username) {
        return tokenBucket.get(username);
    }

}
