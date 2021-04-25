package com.flx.netty.chat.generator.app;

import com.flx.netty.chat.generator.enums.GeneratorType;
import com.flx.netty.chat.generator.service.CustomGeneratorService;
import com.flx.netty.chat.generator.service.SimpleGeneratorService;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/25 16:02
 * @Description:
 */
public class GeneratorApp {

    /**
     * 按照类型生成代码
     * @param type
     */
    public static void generator(GeneratorType type){
        if(type.equals(GeneratorType.SIMPLE)){
            SimpleGeneratorService.generator();
        }else if(type.equals(GeneratorType.CUSTOM)){
            CustomGeneratorService.generator();
        }
    }

}
