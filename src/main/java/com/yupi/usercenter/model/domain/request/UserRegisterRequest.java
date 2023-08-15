package com.yupi.usercenter.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/***
 * @title UserRegisterRequest
 * @description TODO 用户注册请求体
 * @author Skadhi
 * @version 1.0.0
 * @create 2023-02-20 22:14
 **/
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private String userAccount;

    private String userPassword;

    private String checkPassword;

    private String planetCode;

}
