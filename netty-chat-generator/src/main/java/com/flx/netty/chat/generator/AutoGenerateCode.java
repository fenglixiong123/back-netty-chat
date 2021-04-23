package com.flx.netty.chat.generator;

import com.flx.netty.chat.common.utils.system.PropertyUtils;
import com.flx.netty.chat.generator.service.GeneratorService;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/23 11:28
 * @Description:
 */
public class AutoGenerateCode {

    public static void main(String[] args) {

        System.out.println("user.dir = "+System.getProperty("user.dir"));
        GeneratorService.generator();

//        System.out.println(System.getProperty("user.dir")+PropertyUtils.get("femg")+"/src/main");

    }

}
