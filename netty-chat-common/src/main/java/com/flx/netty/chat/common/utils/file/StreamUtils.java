package com.flx.netty.chat.common.utils.file;

import org.apache.commons.codec.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * @Author: Fenglixiong
 * @Date: 2020/9/18 17:46
 * @Description: 输入输出流的操作：Based by IOUtils
 */
public class StreamUtils {


    /**
     * 从输入流读取内容
     * @param is
     * @return
     * @throws IOException
     */
    public static String readBody(InputStream is) throws IOException {
        return IOUtils.toString(is, Charsets.UTF_8);
    }

    /**
     * 读取流
     * @param is
     * @param buffer
     * @return
     * @throws IOException
     */
    public static int read(InputStream is,byte[] buffer) throws IOException {
        return IOUtils.read(is,buffer);
    }

    /**
     * 读取流的所有行
     * @param is
     * @return
     * @throws IOException
     */
    public static List<String> readLines(InputStream is) throws IOException {
        return IOUtils.readLines(is,Charsets.UTF_8);
    }

    /**
     * 写入流
     * @param os
     * @param buffer
     * @throws IOException
     */
    public static void write(OutputStream os,byte[] buffer) throws IOException {
        IOUtils.write(buffer,os);
    }

    /**
     * 写入流
     * @param data
     * @param os
     * @throws IOException
     */
    public static void writeLines(List<String> data,OutputStream os) throws IOException {
        IOUtils.writeLines(data,IOUtils.LINE_SEPARATOR_UNIX,os,Charsets.UTF_8);
    }

    /**
     * 从输入流复制到输出流
     * @param is
     * @param os
     * @return
     * @throws IOException
     */
    public static int copy(InputStream is, OutputStream os) throws IOException {
        return IOUtils.copy(is,os);
    }

    /**
     * 复制大数据量>2g
     * @param is
     * @param os
     * @return
     * @throws IOException
     */
    public static long copyLarge(InputStream is, OutputStream os) throws IOException {
        return IOUtils.copyLarge(is,os);
    }


    /**
     * 获取流迭代器
     * @param is
     * @return
     * @throws IOException
     */
    public static LineIterator lineIterator(InputStream is) throws IOException {
        return IOUtils.lineIterator(is,Charsets.UTF_8);
    }

    /**
     * 关闭流
     * @param source
     * @throws IOException
     */
    public static void close(InputStream source) throws IOException {
        IOUtils.close(source);
    }

}
