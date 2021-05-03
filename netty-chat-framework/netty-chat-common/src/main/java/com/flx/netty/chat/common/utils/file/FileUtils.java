package com.flx.netty.chat.common.utils.file;

import com.flx.netty.chat.common.utils.code.RandomCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: Fenglixiong
 * @Date: 2020/6/10 12:06
 * @Description:
 */
@Slf4j
public class FileUtils {

    public static void main(String[] args) {

        System.out.println(getRandomName("A",true));
        System.out.println(getRandomName("A.pdf",true));
        System.out.println(getRandomName("A",false));
        System.out.println(getRandomName("A.pdf",false));

    }

    /**
     * 获得随机的文件名字
     * @param source
     * @param date
     * @return
     */
    public static String getRandomName(String source,boolean date){
        String fileName;
        String r_code;
        if(date) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            r_code = sdf.format(new Date());
        }else {
            r_code = RandomCodeUtils.getRandomCode(14);
        }
        if(StringUtils.isBlank(source)){
            fileName = r_code;
        }else {
            if(source.contains(".")){
                fileName = source.substring(0,source.lastIndexOf(".")) + r_code + source.substring(source.lastIndexOf("."));
            }else {
                fileName = source + r_code;
            }
        }
        return fileName;
    }

    /**
     * 获取文件中的内容
     * @param pathName
     * @return
     */
    public static String readText(String pathName){
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(pathName)));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        }catch (Exception e){
            log.error("readText error : {}",e.getMessage());
            e.printStackTrace();
            return null;
        }finally {
            try {
                if(br!=null){
                    br.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 向文件中写入内容
     * @param pathName
     * @param content
     * @param append
     * @return
     */
    public static boolean writeText(String pathName,String content,boolean append){
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(pathName,append)));
            bw.write(content);
            return true;
        }catch (Exception e){
            log.info("writeText error {}",e.getMessage());
            e.printStackTrace();
            return false;
        }finally {
            try {
                if(bw!=null){
                    bw.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 文件复制
     * @param sourceFile 原文件路径
     * @param targetFile 目标文件路径
     * @return
     */
    public static boolean copy(String sourceFile,String targetFile){
        File source = new File(sourceFile);
        File target = new File(targetFile);
        boolean targetExist = true;
        if(!source.exists()){
            log.error("sourceFile not exist !");
            return false;
        }
        if(!target.getParentFile().exists()){
            targetExist = target.getParentFile().mkdirs();
        }
        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel in = null;
        FileChannel out = null;
        try{
            if(targetExist){
                fis = new FileInputStream(source);
                fos = new FileOutputStream(target);
                in = fis.getChannel();//
                out = fos.getChannel();//
                in.transferTo(0,in.size(),out);///连接两个通道，并且从in通道读取，然后写入out通道
            }
        }catch (Exception e){
            log.error("copy error !");
            e.printStackTrace();
            return false;
        }finally {
            try {
                if (out != null){
                    out.close();
                }
                if (in != null){
                    in.close();
                }
                if (fos != null){
                    fos.close();
                }
                if (fis != null){
                    fis.close();
                }
            } catch (IOException e) {
                log.info("close file error !");
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * 文件是否存在
     * @param filePath
     * @return
     */
    public static boolean isExist(String filePath){
        File file = new File(filePath);
        return file.exists();
    }

    /**
     * 删除文件或者目录以及下面所有文件
     * @param pathName
     * @return
     */
    public static boolean delete(String pathName){
        try{
            File sourceFile = new File(pathName);
            if(sourceFile.isDirectory()){
                File[] files = sourceFile.listFiles();
                if(files!=null) {
                    for (File listFile : files) {
                        delete(listFile.getAbsolutePath());
                    }
                }
            }
            return sourceFile.delete();
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断是否是文件夹
     * @param path
     * @return
     */
    public static boolean isDir(String path){
        File file = new File(path);
        if(file.exists()){
            return file.isDirectory();
        }
        return false;
    }

    /**
     * 创建目录
     * @param path
     * @return
     */
    public static boolean mkdirs(String path){
        try{
            File file = new File(path);
            if(!file.exists()){
                return file.mkdirs();
            }
            return true;
        }catch (Exception e){
            log.info("mkdirs error : {}",e.getMessage());
            return false;
        }
    }

    /**
     * 获取文件的后缀
     * @param name
     * @return
     */
    public static String getExtension(String name){
        if(StringUtils.isNotBlank(name)){
            return name.substring(name.lastIndexOf(".")+1);
        }
        return null;
    }

    /**
     * 通过后缀获取类型
     * @param suffix
     * @return
     */
    public static String getContentTypeBySuffix(String suffix){
        String result;
        if(".xls".equalsIgnoreCase(suffix) || ".xlsx".equalsIgnoreCase(suffix)){
            result = "application/vnd.ms-excel";
        }else if(".doc".equalsIgnoreCase(suffix) || ".docx".equalsIgnoreCase(suffix)){
            result = "application/vnd.msword";
        }else if(".exe".equalsIgnoreCase(suffix)){
            result = "application/octet-stream";
        }else if(".jpe".equalsIgnoreCase(suffix) || ".jpeg".equalsIgnoreCase(suffix) || ".jpg".equalsIgnoreCase(suffix)){
            result = "image/jpeg";
        }else if(".png".equalsIgnoreCase(suffix)){
            result = "image/png";
        }else if(".gif".equalsIgnoreCase(suffix)){
            result = "image/gif";
        }else if(".mp3".equalsIgnoreCase(suffix)){
            result = "audio/x-mpeg";
        }else if(".mp4".equalsIgnoreCase(suffix) || ".mpg4".equalsIgnoreCase(suffix)){
            result = "video/mp4";
        }else if(".wm".equalsIgnoreCase(suffix)){
            result = "video/x-ms-wm";
        }else if(".wmv".equalsIgnoreCase(suffix)){
            result = "audio/x-ms-wmv";
        }else if(".rm".equalsIgnoreCase(suffix) || ".rmvb".equalsIgnoreCase(suffix)){
            result = "audio/x-pn-realaudio";
        }else if(".xml".equalsIgnoreCase(suffix)){
            result = "text/xml";
        }else if(".gz".equalsIgnoreCase(suffix)){
            result = "application/x-gzip";
        }else if(".gtar".equalsIgnoreCase(suffix)){
            result = "application/x-gtar";
        }else if(".tar".equalsIgnoreCase(suffix) || ".taz".equalsIgnoreCase(suffix)){
            result = "application/x-tar";
        }else if(".rar".equalsIgnoreCase(suffix)){
            result = "application/x-rar-compressed";
        }else if(".zip".equalsIgnoreCase(suffix)){
            result = "application/zip";
        }else{
            result = "multipart/form-data";
        }
        return result;
    }

}
