package com.flx.netty.chat.user.api.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @Author Fenglixiong
 * @Create 2021/4/22 1:58
 * @Description
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidatePassVO {

    @NotNull
    @Size(max = 64)
    private String username;

    @NotNull
    @Size(max = 64)
    private String password;

}
