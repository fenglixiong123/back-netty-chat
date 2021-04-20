package com.flx.netty.chat.common.utils.file;

import org.apache.commons.codec.Charsets;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * @Author: Fenglixiong
 * @Date: 2020/9/21 10:58
 * @Description: 主要采用apache包中现成的方法
 */
public class FileCommonUtils {

    /**
     * 返回文件或者文件夹的大小MB
     * @param file
     * @return
     */
    public static int size(File file){
        long size;
        if(file.isDirectory()){
            size = FileUtils.sizeOfDirectory(file);
        }else {
            size =  FileUtils.sizeOf(file);
        }
        return Long.valueOf(size/FileUtils.ONE_MB).intValue();
    }

    /**
     * 从文件中读取内容
     * @param file
     * @return
     * @throws IOException
     */
    public static List<String> readLines(File file) throws IOException {
        checkFile(file);
        return FileUtils.readLines(file,Charsets.UTF_8);
    }

    /**
     * 从文件中读取内容
     * @param file
     * @return
     * @throws IOException
     */
    public static String readFromFile(File file) throws IOException {
        checkFile(file);
        return FileUtils.readFileToString(file,Charsets.UTF_8);
    }

    /**
     * 向文件按行写内容
     * @param file
     * @param lines
     * @param append
     * @throws IOException
     */
    public static void writeLines(File file, List<String> lines, boolean append) throws IOException {
        checkFile(file);
        FileUtils.writeLines(file,"utf-8",lines, IOUtils.LINE_SEPARATOR_UNIX,append);
    }

    /**
     * 向文件写内容
     * @param file
     * @param content
     * @param append
     * @throws IOException
     */
    public static void writeToFile(File file, String content, boolean append) throws IOException {
        checkFile(file);
        FileUtils.writeStringToFile(file,content, Charsets.UTF_8,append);
    }

    /**
     * URL转换file
     * @param url
     * @return
     */
    public static File urlToFile(URL url){
        return FileUtils.toFile(url);
    }

    /**
     * 将文件从一个文件复制到另一个文件
     * @param source
     * @param target
     * @throws IOException
     */
    public static void copy(File source,File target) throws IOException {
        checkFile(source);
        checkFile(target);
        FileUtils.copyFile(source,target);
    }

    /**
     * 删除文件或者文件夹
     * @param file
     * @throws IOException
     */
    public static void delete(File file)throws IOException{
        checkFile(file);
        if(file.isDirectory()){
            FileUtils.deleteDirectory(file);
        }else {
            FileUtils.deleteQuietly(file);
        }
    }

    private static void checkFile(File file)throws IOException{
        if(!file.exists()){
            throw new IOException("file not exist ! ");
        }
    }

}
