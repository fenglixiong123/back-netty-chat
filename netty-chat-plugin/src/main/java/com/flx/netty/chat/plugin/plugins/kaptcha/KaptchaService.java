package com.flx.netty.chat.plugin.plugins.kaptcha;

import com.flx.netty.chat.common.utils.IPUtils;
import com.flx.netty.chat.plugin.plugins.kaptcha.config.CustomKptcha;
import com.flx.netty.chat.plugin.plugins.redis.service.RedisBaseService;
import com.flx.netty.chat.plugin.plugins.redis.service.RedisStringService;
import com.flx.netty.chat.plugin.utils.CryptoTool;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/2 12:32
 * @Description:
 */
@Slf4j
@Component
public class KaptchaService {

    /**
     * 最大请求次数
     */
    private static final int MAX_REQUEST_COUNT = 10;

    private static final String PNG_PREFIX = "data:image/png;base64,";

    private static final String REDIS_IP_PREFIX = "kaptcha:request.ip";

    private static final String REDIS_PIC_ID_PREFIX = "kaptcha:pic.id:";

    @Autowired
    private RedisBaseService redisBaseService;
    @Autowired
    private RedisStringService redisStringService;

    private DefaultKaptcha customKaptcha = new CustomKptcha().getKaptcha();

    /**
     * 获取验证码图片，一分钟最多十次
     * @param request
     * @throws Exception
     */
    public void getVerifyPicLimit(HttpServletRequest request,int limit)throws Exception {
        if(limit <= 0){
            limit = MAX_REQUEST_COUNT;
        }
        String redisIpKey = REDIS_IP_PREFIX + IPUtils.getIpAddress(request);
        int ipCounter = Optional.ofNullable((Integer)redisStringService.get(redisIpKey)).orElse(0);
        if (ipCounter > limit) {
            throw new Exception("Request too much,please try later ！");
        }
        PicResult picResult = getVerifyPic();
        if(picResult!=null){
            updateKaptchaRedis(picResult.getId(), picResult.getText(), picResult.getImage());
        }else {
            throw new Exception("Generate image error !");
        }
    }

    /**
     * 获取验证码图片
     * @return
     * @throws Exception
     */
    public PicResult getVerifyPic()throws Exception{

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            //生成验证码代码
            String text = customKaptcha.createText();
            //生成验证码图片
            BufferedImage challenge = customKaptcha.createImage(text);
            ImageIO.write(challenge, "png", bos);
            byte[] bytes = bos.toByteArray();
            String image = PNG_PREFIX + CryptoTool.getCrypto().encode64(bytes);
            String picId = UUID.randomUUID().toString();
            return new PicResult(picId, text, image);
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }

    }

    /**
     * 更新redis中验证码相关内容
     * @param picId
     * @param createText
     * @param redisIpKey
     */
    private void updateKaptchaRedis(String picId, String createText, String redisIpKey) {
        Object count = redisStringService.get(redisIpKey);
        redisStringService.incr(redisIpKey,1);
        if (count==null) {
            redisBaseService.expire(redisIpKey, 1, TimeUnit.MINUTES);
        }
        String redisPicKey = REDIS_PIC_ID_PREFIX + picId;
        redisStringService.setIfAbsent(redisPicKey, createText, 10, TimeUnit.MINUTES);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PicResult{
        private String id;
        private String text;
        private String image;
    }

}
