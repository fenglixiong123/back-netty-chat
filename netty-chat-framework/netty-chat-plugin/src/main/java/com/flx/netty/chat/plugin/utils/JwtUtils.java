package com.flx.netty.chat.plugin.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import com.flx.netty.chat.common.utils.CollectionUtils;
import com.flx.netty.chat.common.utils.date.DateUtils;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/28 15:19
 * @Description: jwt单点登录
 *
 * 流程思想：
 *
 * 1.客户端发起请求登陆
 * 2.服务端验证身份，根据算法，将用户标识符打包生成token，并且返回给客户端
 * 3.客户端发起请求获取用户资料，把刚刚拿到的token一起发送给服务器
 * 4.服务器发现数据中有token，验明正身，服务器返回该用户的用户资料
 *
 */
@Slf4j
public class JwtUtils {

    public static void main(String[] args) {
        Map<String,Object> map = new HashMap<>();
        map.put("feng","fenglixiong");
        map.put("li",false);
        map.put("xiong", Arrays.asList(1,2,3,4,5));
        String token = createJWT(build().setPlayLoad(map).setSecretKey("123456"));
        System.out.println("token = "+token);

        //解码
        DecodedJWT decode = JWT.decode(token);
        System.out.println("token = "+decode.getToken());
        System.out.println(decode.getHeader());
        System.out.println(decode.getPayload());
        System.out.println(decode.getSignature());
        System.out.println(decode.getAlgorithm());
        System.out.println(decode.getExpiresAt());
        System.out.println(decode.getClaim("feng").asString());
        System.out.println(decode.getClaim("li").asBoolean());
        System.out.println(decode.getClaim("xiong").asList(Integer.class));
        //验证
        String t1 = createJWT(build().setUserKey("marry").setSecretKey("123456"));
        String t2 = createJWT(build().setUserKey("jack").setSecretKey("123456"));
        System.out.println(t1);
        System.out.println(t2);

        try {
            boolean verify1 = verify(build().setToken(t1).setSecretKey("123456"));
            System.out.println(verify1);
            boolean verify2 = verify(build().setToken(t2).setSecretKey("123456"));
            System.out.println(verify2);
            System.out.println(getUserKey(t1));
            System.out.println(getUserKey(t2));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String,Object> map1 = new HashMap<>();

        map1.put("1","123");
        map1.put("2",1);
        map1.put("3",2L);
        map1.put("4",new Date());
        map1.put("5",true);

        System.out.println(map1.get("5") instanceof Boolean);
        System.out.println((Date) map1.get("4"));

    }

    /**
     * 默认过期时间5分钟
     */
    public static final long DEFAULT_EXPIRE_TIME = 5;

    /**
     * 面向的用户
     */
    public static final String DEFAULT_SUBJECT = "phone+pad+pc";

    /**
     * 颁发者身份标识
     */
    public static final String DEFAULT_ISSUER = "http//www.fenglixiong.com";

    public static String createJWT(JwtVO entity) {
        return createJWT(entity.getSecretKey(),entity.getSubject(),entity.getIssuer(),entity.getUserKey(),entity.getPlayLoad(),entity.getExpire(),entity.getUnit());
    }

    public static String createJWT(String secretKey,String subject,String issuer,String userKey,Map<String,Object> playLoad,long expire, TimeUnit unit) {
        try {
            Date expireDate = new Date(System.currentTimeMillis() + DateUtils.toMillis(expire,unit));
            return JWT.create()
                    .withSubject(subject)
                    .withAudience(userKey)//接收方信息，一般是登录用户
                    .withPayload(playLoad)//其他自定义信息
                    .withIssuer(issuer)//token签发人
                    .withIssuedAt(new Date())//token签发时间
                    .withNotBefore(new Date())//在此时间之前不可用
                    .withExpiresAt(expireDate)//过期时间
                    .sign(Algorithm.HMAC256(secretKey));
        } catch (Exception e) {
            return null;
        }
    }

    public static String getUserKey(String token) throws Exception{
        return JWT.decode(token).getAudience().get(0);
    }

    public static boolean verify(JwtVO entity) throws Exception{
        return verify(entity.getToken(),entity.getSecretKey(),entity.getSubject(),entity.getIssuer(),entity.getPlayLoad());
    }

    public static boolean verify(String token,String secretKey,
                                 String subject, String issuer, Map<String,Object> playLoad) throws Exception{
        Verification verification = JWT.require(Algorithm.HMAC256(secretKey));
        verification.withSubject(subject);
        verification.withIssuer(issuer);
        if(CollectionUtils.isNotEmpty(playLoad)){
            playLoad.forEach((k,v)->{
                if(v instanceof String){
                    verification.withClaim(k,(String) v);
                }
                if(v instanceof Integer){
                    verification.withClaim(k,(Integer) v);
                }
                if(v instanceof Long){
                    verification.withClaim(k,(Long) v);
                }
                if(v instanceof Double){
                    verification.withClaim(k,(Double) v);
                }
                if(v instanceof Date){
                    verification.withClaim(k,(Date) v);
                }
                if(v instanceof Boolean){
                    verification.withClaim(k,(Boolean) v);
                }
            });
        }
        JWTVerifier verifier = verification.build();
        verifier.verify(token);
        return true;
    }

    @Data
    @Accessors(chain = true)
    public static class JwtVO{
        private String token;
        private String secretKey;
        private String userKey;
        private String subject;
        private String issuer;
        private long expire;
        private TimeUnit unit;
        private Map<String,Object> playLoad;


    }

    public static JwtVO build(){
        return new JwtVO();
    }

}
