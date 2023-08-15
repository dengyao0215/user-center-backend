package com.yupi.usercenter.utils;

import com.yupi.usercenter.model.domain.User;

/***
 * @title UserHolder
 * @description TODO ThreadLocal工具类
 * @author Skadhi
 * @version 1.0.0
 * @create 2023-03-01 20:24
 **/
public class UserHolder {
    private static final ThreadLocal<User> tl = new ThreadLocal<>();

    public static void saveUser(User user){
        tl.set(user);
    }

    public static User getUser(){
        return tl.get();
    }

    public static void removeUser(){
        tl.remove();
    }
}
