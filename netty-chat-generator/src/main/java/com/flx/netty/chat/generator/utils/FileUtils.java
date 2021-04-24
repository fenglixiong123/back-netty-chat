package com.flx.netty.chat.generator.utils;

import java.io.File;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/25 0:02
 * @Description:
 */
public class FileUtils {

    /**
     * 判断文件是否存在
     * @param path 路径
     * @return
     */
    private static boolean isExists(String path) {
        File file = new File(path);
        return file.exists();
    }

}
