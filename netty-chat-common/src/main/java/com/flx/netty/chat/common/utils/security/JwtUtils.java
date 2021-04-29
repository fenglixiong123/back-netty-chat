package com.flx.netty.chat.common.utils.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.flx.netty.chat.common.utils.date.DateUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/28 15:19
 * @Description:
 */
public class JwtUtils {

    /**
     * 默认过期时间5分钟
     */
    private static final long DEFAULT_EXPIRE_TIME = 5;

    /**
     * jwt 密钥
     */
    private static final String SECRET = "jwt_secret";

    public static String createJWT() {
        return createJWT(DEFAULT_EXPIRE_TIME,TimeUnit.MINUTES);
    }

    public static String createJWT(long expire, TimeUnit unit) {
        try {
            Date date = new Date(System.currentTimeMillis() + DateUtils.toMillis(expire,unit));
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            Map<String,Object> headerMap = new HashMap<>();
            headerMap.put("alg", "HS256");
            headerMap.put("typ", "JWT");
            return JWT.create()
                    .withHeader(headerMap)
                    // 将 user id 保存到 token 里面
                    //只能保留一个
                    .withAudience("zhengwei").withAudience("地址")
                    //.withAudience()
                    .withClaim("userId", "zhengwei") // payload
                    .withClaim("age", "30") // payload
                    .withClaim("phone", "1810824293X") // payload
                    // 五分钟后token过期
                    .withExpiresAt(date)
                    //.withNotBefore() 在这个时间之前，不能用
                    // token 的密钥
                    .sign(algorithm);
        } catch (Exception e) {
            return null;
        }
    }

}