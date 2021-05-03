package com.flx.netty.chat.common.utils.result;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/22 12:53
 * @Description:
 */
public class ResponseUtils {

    /**
     * 获取真实结果，否则抛出异常
     * @param response
     * @param <T>
     * @return
     * @throws Exception
     */
    public static  <T> T getResult(ResultResponse<T> response)throws Exception{
        if(response.isSuccess()){
            return response.getData();
        }else {
            throw new Exception(response.getMessage());
        }
    }

}
