package com.flx.netty.chat.common.utils.security.sign;

import com.flx.netty.chat.common.utils.security.ByteUtils;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/28 18:04
 * @Description:
 *
 * HMAC是密钥相关的哈希运算消息认证码（Hash-based Message Authentication Code），
 * HMAC运算利用哈希算法(MD5、SHA1)，以一个密钥和一个消息为输入，生成一个消息摘要作为输出。
 *
 * HMAC发送方和接收方都有的 key进行计算，而没有这把key的第三方，则是无法计算出正确的散列值的，这样就可以防止数据被篡改。
 *
 * MAC算法可选以下多种算法:
 * HmacMD5/HmacSHA1/HmacSHA256/HmacSHA384/HmacSHA512
 */
@Slf4j
public class HmacUtils {

    private static final String DEFAULT_KEY_MAC = "HmacMD5";


    public static String getSign(String content,String secretKey) {
        return getSign(content.getBytes(StandardCharsets.UTF_8),secretKey,DEFAULT_KEY_MAC);
    }

    public static String getSign(byte[] content,String secretKey) {
        return getSign(content,secretKey,DEFAULT_KEY_MAC);
    }

    /**
     * 签名
     * @param content 需要签名的内容
     * @return 签名后的签证
     */
    public static String getSign(byte[] content,String secretKey,String macKey) {
        try {
            SecretKey sk = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), macKey);
            Mac mac = Mac.getInstance(sk.getAlgorithm());
            mac.init(sk);
            return ByteUtils.byte2hex(Objects.requireNonNull(mac,"Mac is null")
                    .doFinal(content));
        } catch (Exception e) {
            log.error("Create hmac failed , error = {}", e.getMessage());
        }
        return null;
    }

}
