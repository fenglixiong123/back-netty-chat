package com.flx.netty.chat.plugin.plugins.swagger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/27 10:48
 * @Description:
 */
@Controller
public class SwaggerController {

    @GetMapping(value = "/swagger")
    public ModelAndView home() {
        return new ModelAndView("redirect:/swagger-ui.html");
    }

    @GetMapping(value = "/swagger2")
    public DeferredResult<ModelAndView> ui2() {
        DeferredResult<ModelAndView> result = new DeferredResult<>();
        result.setResult(new ModelAndView("redirect:/doc.html"));
        return result;
    }

}
