package com.flx.netty.chat.auth.console.security.handler;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * @Author Fenglixiong
 * @Create 2021/5/13 0:25
 * @Description 添加CustomOauthException的序列化实现
 **/
public class CustomOauthExceptionSerializer extends StdSerializer<CustomOauthException> {

    public CustomOauthExceptionSerializer() {
        super(CustomOauthException.class);
    }

    @Override
    public void serialize(CustomOauthException e, JsonGenerator generator, SerializerProvider provider) throws IOException {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        generator.writeStartObject();
        generator.writeStringField("success", "false");
        generator.writeStringField("code", String.valueOf(e.getHttpErrorCode()));
        generator.writeStringField("message", e.getMessage());
        generator.writeStringField("data", e.getData());
        generator.writeStringField("path", request.getServletPath());
        if (e.getAdditionalInformation()!=null) {
            for (Map.Entry<String, String> entry : e.getAdditionalInformation().entrySet()) {
                String key = entry.getKey();
                String add = entry.getValue();
                generator.writeStringField(key, add);
            }
        }
        generator.writeEndObject();
    }
}
