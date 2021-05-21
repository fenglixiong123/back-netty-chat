package com.flx.netty.chat.admin.common.wrapper;

import org.apache.commons.io.IOUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/21 17:36
 * @Description: 由于http的response读取一次就不能继续发送了所以需要包装
 */
public class ResponseWrapper extends HttpServletResponseWrapper {

    private ByteArrayOutputStream bos = new ByteArrayOutputStream();

    private PrintWriter printWriter = new PrintWriter(bos);

    private HttpServletResponse response;

    public ResponseWrapper(HttpServletResponse response) throws IOException {
        super(response);
        this.response = response;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return new ServletOutputStream() {

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setWriteListener(WriteListener listener) {
            }

            @Override
            public void write(int content) throws IOException {
                bos.write(content);//将数据写到stream中
            }

        };
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return printWriter;

    }

    public String getBody() throws IOException {
        return IOUtils.toString(bos.toByteArray(), StandardCharsets.UTF_8.name());
    }

    /**
     * 重新发送response请求
     * @param body
     * @throws IOException
     */
    public void reSendResponse(String body) throws IOException {
        //将数据再写入response中
        OutputStream os = response.getOutputStream();
        os.write(body.getBytes(StandardCharsets.UTF_8));
        os.flush();
        os.close();
    }

}
