package com.flx.netty.chat.common.utils.context;

import com.flx.netty.chat.common.constants.WebConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Fenglixiong
 * @Date: 2020/8/6 17:06
 * @Description:
 */
@Slf4j
public class CookieUtils {

    public static String getValueFromCookie(HttpServletRequest request,String key){
        String language = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(key)) {
                    language = cookie.getValue();
                }
            }
        }
        if (language == null) {
            String header = request.getHeader(key);
            if (StringUtils.isNotBlank(header)) {
                language = header;
            } else {
                language = WebConstant.en_us;
            }
        }
        return language;
    }

}
