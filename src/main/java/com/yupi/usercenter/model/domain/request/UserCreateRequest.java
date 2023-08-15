package com.yupi.usercenter.model.domain.request;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/***
 * @title UserNewbuildRequest
 * @description TODO 管理员新建用户请求体
 * @author Skadhi
 * @version 1.0.0
 * @create 2023-02-28 20:32
 **/
@Data
public class UserCreateRequest implements Serializable {

    private static final long serialVersionUID = -467659548458497588L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 用户昵称
     */
    private String username;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 星球编号
     */
    private String planetCode;

}
