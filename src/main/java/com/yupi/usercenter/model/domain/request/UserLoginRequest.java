package com.yupi.usercenter.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/***
 * @title userLoginRequest
 * @description TODO 用户登录请求体
 * @author Skadhi
 * @version 1.0.0
 * @create 2023-02-20 22:22
 **/
@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 5653330378312090679L;

    private String userAccount;

    private String userPassword;
}
