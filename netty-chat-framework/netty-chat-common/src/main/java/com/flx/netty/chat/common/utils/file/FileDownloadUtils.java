package com.flx.netty.chat.common.utils.file;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;

/**
 * @Author: Fenglixiong
 * @Date: 2020/6/11 20:11
 * @Description:
 *
 * 2.Content-Disposition中指定的类型是文件的扩展名，并且弹出的下载对话框中的文件类型图片是按照文件的扩展名显示的，
 * 点保存后，文件以filename的值命名，保存类型以Content中设置的为准。
 * 注意：在设置Content-Disposition头字段之前，一定要设置Content-Type头字段。
 */
@Slf4j
public class FileDownloadUtils {


    /**
     * 全路径下载文件
     * @param response
     * @param pathName
     */
    public static void download(HttpServletResponse response,String pathName){
        int index = pathName.lastIndexOf("/")+1;
        String path = pathName.substring(0,index);
        String fileName = pathName.substring(index);
        download(response,path,fileName);
    }

    /**
     * 下载文件
     * @param response
     * @param path
     * @param fileName
     */
    public static void download(HttpServletResponse response,String path,String fileName){
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            String pathName;
            if(path.endsWith("/")){
                pathName = path+fileName;
            }else {
                pathName = path+"/"+fileName;
            }
            File file = new File(pathName);
            if(!file.exists()) {
                throw new Exception("file not exist !");
            }
            String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
            response.setContentType(FileUtils.getContentTypeBySuffix(suffix));
            response.addHeader("Content-Disposition", String.format("attachment;fileName=%s", fileName));
            byte[] buffer = new byte[1024];
            bis = new BufferedInputStream(new FileInputStream(file));
            bos = new BufferedOutputStream(response.getOutputStream());
            int count;
            while ((count=bis.read(buffer))!=-1){
                bos.write(buffer,0,count);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (bos != null) {
                    bos.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 上传文件并且追加到文件后面
     * @param file
     * @param pathName
     * @return
     */
    public static boolean uploadAndAppend(MultipartFile file,String pathName){
        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            File writeFile = new File(pathName);
            if (!writeFile.exists()) {
                boolean result = writeFile.createNewFile();
                if(!result){
                    return false;
                }
            }
            StringBuilder sb = new StringBuilder();
            //read file
            br=new BufferedReader(new InputStreamReader(file.getInputStream(), Charset.forName("UTF-8")));
            //write file
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(writeFile.getAbsoluteFile(),true)));
            String temp;
            while ((temp = br.readLine())!=null){
                sb.append(temp).append("\n");
            }
            bw.write(sb.toString());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(br!=null) {
                    br.close();
                }
                if(bw!=null) {
                    bw.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return true;
    }

}
