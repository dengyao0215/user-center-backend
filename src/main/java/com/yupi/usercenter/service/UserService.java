package com.yupi.usercenter.service;

import com.yupi.usercenter.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.usercenter.model.domain.request.UserCreateRequest;
import com.yupi.usercenter.model.dto.UserDTO;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Skadhi
 * @description 针对表【user(用户表)】的数据库操作Service
 * @createDate 2023-02-19 18:41:21
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @return 新用户id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword, String planetCode);

    /**
     * 用户登录
     *
     * @param userAccount 用户账户
     * @param userPassword 用户密码
     * @return 脱敏后的用户参数
     */
    UserDTO userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户脱敏
     * @param originUser 原用户
     * @return 脱敏用户
     */
    UserDTO getSafetyUser(User originUser);

    /**
     * 用户注销
     * @param request 请求
     * @return 标识符
     */
    int userLogout(HttpServletRequest request);

    /**
     * 管理员进行用户创建
     * @param userDTO 用户信息
     * @return 标识符
     */
    long userCreateByAdmin(UserCreateRequest userDTO);

    /**
     * 管理员进行用户修改
     * @param userDTO 用户信息
     * @return 标识符
     */
    long userUpdateByAdmin(UserCreateRequest userDTO);

}
