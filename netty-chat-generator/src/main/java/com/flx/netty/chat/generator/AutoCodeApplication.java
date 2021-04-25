package com.flx.netty.chat.generator;

import com.flx.netty.chat.generator.app.GeneratorApp;
import com.flx.netty.chat.generator.enums.GeneratorType;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/23 11:28
 * @Description:
 */
public class AutoCodeApplication {

    public static void main(String[] args) {

//        GeneratorApp.generator(GeneratorType.SIMPLE);
        GeneratorApp.generator(GeneratorType.CUSTOM);

    }

}
