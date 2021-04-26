package com.flx.netty.chat.generator.app;

import com.flx.netty.chat.generator.enums.GeneratorType;
import com.flx.netty.chat.generator.service.CustomGeneratorService;
import com.flx.netty.chat.generator.service.SimpleGeneratorService;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/25 16:02
 * @Description:
 * 参考：
 * https://blog.csdn.net/weixin_43671737/article/details/106551387
 */
public class GeneratorApp {

    public static void main(String[] args) {
//        generator(GeneratorType.SIMPLE);
        generator(GeneratorType.CUSTOM);
    }

    /**
     * 按照类型生成代码
     * @param type
     */
    public static void generator(GeneratorType type){
        if(type.equals(GeneratorType.SIMPLE)){
            new SimpleGeneratorService().generator();
        }else if(type.equals(GeneratorType.CUSTOM)){
            new CustomGeneratorService().generator();
        }
    }

}
