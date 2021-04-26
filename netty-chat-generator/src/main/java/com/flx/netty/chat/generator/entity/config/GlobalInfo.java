package com.flx.netty.chat.generator.entity.config;


import com.flx.netty.chat.common.constants.FileConstant;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/26 18:47
 * @Description: 其他设置
 */
public class GlobalInfo {

    /**
     * 是否覆盖
     */
    private boolean override;

    private String basePath;

    private String author;

    public boolean isOverride() {
        return override;
    }

    public GlobalInfo setOverride(boolean override) {
        this.override = override;
        return this;
    }

    public String getBasePath() {
        if(StringUtils.isBlank(basePath)) {
            basePath = System.getProperty("user.dir")+ FileConstant.pathSeparator;
        }
        return basePath;
    }

    public GlobalInfo setBasePath(String basePath) {
        this.basePath = basePath;
        return this;
    }

    public String getAuthor() {
        if(StringUtils.isBlank(author)){
            return "Fenglixiong";
        }
        return author;
    }

    public GlobalInfo setAuthor(String author) {
        this.author = author;
        return this;
    }
}
