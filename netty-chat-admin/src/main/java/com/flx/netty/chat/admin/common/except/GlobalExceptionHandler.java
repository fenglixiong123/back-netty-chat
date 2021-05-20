package com.flx.netty.chat.admin.common.except;

import com.flx.netty.chat.common.enums.ErrorMsgEnum;
import com.flx.netty.chat.common.utils.result.ResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author Fenglixiong
 * @Create 2018.11.10 14:11
 * @Description 全局异常处理类，注意要被引用的项目扫描到才行
 **/
@Slf4j
@ResponseBody
@ControllerAdvice
public class GlobalExceptionHandler implements InitializingBean {

    /**
     * Http请求方式常类
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResultResponse<String> httpRequestMethodNotSupportedHandler(HttpServletRequest request, Exception e){
        log.error("【异常地址】：{}",request.getRequestURL().toString());
        log.error("【异常类型】请求方法异常HttpRequestMethodNotSupportedException：{}",e.getMessage());
        return ResultResponse.error("HttpMethod not support !",e.getMessage());
    }

    /**
     * Hibernate校验异常类
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultResponse<String> methodNotValidExceptionHandler(HttpServletRequest request,MethodArgumentNotValidException e){
        log.error("【异常地址】：{}",request.getRequestURL().toString());
        log.error("【异常类型】字段校验异常MethodArgumentNotValidException：{}",e.getMessage());
        String errorMsg = "字段校验异常";
        e.getBindingResult();
        if(e.getBindingResult().getFieldError() != null){
            errorMsg = e.getBindingResult().getFieldError().getDefaultMessage();
        }
        return ResultResponse.error(ErrorMsgEnum.PARAM_CHECK,errorMsg);
    }

    /**
     * 传参异常类
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResultResponse<String> missingRequestParamExceptionHandler(HttpServletRequest request,Exception e){
        log.error("【异常地址】：{}",request.getRequestURL().toString());
        log.error("【异常类型】缺少参数异常missingRequestParamException：{}",e.getMessage());
        return ResultResponse.error(ErrorMsgEnum.PARAM_MISSING,e.getMessage());
    }

    /**
     * Json转换异常类
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResultResponse<String> jsonConvertExceptionHandler(HttpServletRequest request,Exception e){
        log.error("【异常地址】：{}",request.getRequestURL().toString());
        log.error("【异常类型】业务异常JsonConvertException：{}",e.getMessage());
        return ResultResponse.error(ErrorMsgEnum.JSON_CONVERT_ERROR,e.getMessage());
    }

    /**
     * 全局异常类
     */
    @ExceptionHandler(Exception.class)
    public ResultResponse<String> defaultExceptionHandler(HttpServletRequest request,Exception e){
        log.error("【异常地址】：{}",request.getRequestURL().toString());
        String message;
        if(e instanceof NullPointerException){
            message = "空指针异常";
            log.error("【异常类型】空指针异常NullPointerException：{}",e.getMessage());
        } else if(e instanceof IllegalArgumentException){
            message = "参数异常";
            log.error("【异常类型】参数异常IllegalArgumentException：{}",e.getMessage());
        }else if(e instanceof ArithmeticException){
            message = "算数异常";
            log.error("【异常类型】算数异常ArithmeticException：{}",e.getMessage());
        }else {
            message = "未知异常";
            log.error("【异常类型】未知异常UnknownException：{}",e.getMessage());
        }
        e.printStackTrace();
        return ResultResponse.error(message);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info(">>>>>>>>>>>>>Exception Successful<<<<<<<<<<<<");
    }
}
