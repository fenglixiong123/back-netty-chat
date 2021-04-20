package com.flx.netty.chat.common.utils.file;

import com.flx.netty.chat.common.utils.date.DateUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;

/**
 * @Author: Fenglixiong
 * @Date: 2020/6/12 15:17
 * @Description: 文件上传工具类
 */
public class FileUploadUtils {

    /**
     * 上传文件
     * @param file
     * @param path
     * @param fileName
     * @return
     * @throws Exception
     */
    public static boolean upload(MultipartFile file,String path,String fileName)throws Exception{
        if(file.isEmpty()){
            throw new Exception("file is empty");
        }
        boolean pathExist = true;
        File dest = new File(path+"/"+fileName);
        if(!dest.getParentFile().exists()){
            pathExist = dest.getParentFile().mkdirs();
        }
        if(pathExist){
            file.transferTo(dest);
        }else {
            return false;
        }
        return true;
    }

    /**
     * 多文件上传
     * @param files
     * @param path
     * @return
     */
    public static boolean upload(MultipartFile[] files,String path){
        try{
            for (MultipartFile file : files) {
                if(file.getSize() > 0){
                    String originName = file.getOriginalFilename();
                    String filePath = path+ DateUtils.dateToSimpleString(new Date())+"."+FileUtils.getExtension(originName);
                    file.transferTo(new File(filePath));
                }
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }



}
