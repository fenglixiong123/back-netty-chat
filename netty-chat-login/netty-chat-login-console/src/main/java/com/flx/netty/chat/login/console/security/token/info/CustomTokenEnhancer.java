package com.flx.netty.chat.login.console.security.token.info;

import com.flx.netty.chat.common.utils.json.JsonUtils;
import com.flx.netty.chat.user.api.vo.WebUserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/5 2:27
 * @Description: 自定义Token增强，添加额外信息
 */
@Slf4j
@Component
public class CustomTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

        String username = authentication.getUserAuthentication().getName();
        //得到用户名，去处理数据库可以拿到当前用户的信息和角色信息（需要传递到服务中用到的信息）
        final Map<String,Object> customInfoMap = new HashMap<>();
        //数据库拿到用户信息
        //此处模拟用户信息
        WebUserVO userVO = new WebUserVO();
        userVO.setId(1L);
        userVO.setUserName("fenglixiong");
        userVO.setEmail("fenglixiong123@163.com");
        userVO.setAddress("上海市徐汇区");
        customInfoMap.put("userInfo", JsonUtils.toJsonMsg(userVO));
        customInfoMap.put("slogin","I am a good man!");
        ((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(customInfoMap);

        return accessToken;
    }

}