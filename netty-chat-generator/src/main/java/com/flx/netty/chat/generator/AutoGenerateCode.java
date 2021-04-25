package com.flx.netty.chat.generator;

import com.flx.netty.chat.generator.service.CustomGeneratorService;
import com.flx.netty.chat.generator.service.SimpleGeneratorService;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/23 11:28
 * @Description:
 */
public class AutoGenerateCode {

    public static void main(String[] args) {

//        SimpleGeneratorService.generator();
        CustomGeneratorService.generator();
    }

}
