package com.flx.netty.chat.login.console.security.token.jwt;

import com.flx.netty.chat.common.utils.json.JsonUtils;
import com.flx.netty.chat.user.api.vo.WebUserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/3 21:16
 * @Description: token信息
 * 在使用JWT存储token的时候，由于授权服务把token给了资源服务之后，资源服务就不会再访问授权服务索求token相关的信息，以及当前登录人的信息。
 * 因为这些token的校验，以及授权服务想告诉资源服务的信息，都可以存放在这个token里，资源服务只需要去解析就可以了
 */
@Slf4j
@Component
public class CustomJwtAccessTokenConverter extends JwtAccessTokenConverter {

    /**
     * 重写增强token方法，用于自定义一些token总需要封装的信息
     * @param accessToken
     * @param authentication
     * @return
     */
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
        return super.enhance(accessToken, authentication);
    }

}
