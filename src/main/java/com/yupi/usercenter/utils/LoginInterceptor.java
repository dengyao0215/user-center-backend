package com.yupi.usercenter.utils;

import cn.hutool.core.bean.BeanUtil;
import com.yupi.usercenter.common.ErrorCode;
import com.yupi.usercenter.exception.BusinessException;
import com.yupi.usercenter.model.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.yupi.usercenter.constant.UserConstant.USER_LOGIN_STATE;
import static com.yupi.usercenter.utils.RedisConstants.LOGIN_USER_KEY;
import static com.yupi.usercenter.utils.RedisConstants.LOGIN_USER_TTL;

/***
 * @title LoginInterceptor
 * @description TODO 全局请求拦截器
 * @author Skadhi
 * @version 1.0.0
 * @create 2023-03-01 20:00
 **/
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 1.判断是否需要拦截（ThreadLocal中是否有用户）
        if (UserHolder.getUser() == null) {
            // 没有，需要拦截
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        // 有用户，则放行
        return true;
    }

}
