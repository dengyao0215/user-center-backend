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
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.yupi.usercenter.utils.RedisConstants.LOGIN_USER_KEY;
import static com.yupi.usercenter.utils.RedisConstants.LOGIN_USER_TTL;

/***
 * @title RefreshTokenInterceptor
 * @description TODO 刷新token功能
 * @author Skadhi
 * @version 1.0.0
 * @create 2023-03-02 0:43
 **/
@Slf4j
public class RefreshTokenInterceptor implements HandlerInterceptor {
    private StringRedisTemplate stringRedisTemplate;

    public RefreshTokenInterceptor(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ////1. 获取session
        //HttpSession session = request.getSession();
        //1. 获取请求头中的token
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            return true;
        }
        //2. 获取session中的用户
        //Object user = session.getAttribute(USER_LOGIN_STATE);
        //2. 获取redis中的用户
        String tokenKey = LOGIN_USER_KEY + token;
        Map<Object, Object> userMap = stringRedisTemplate.opsForHash().entries(tokenKey);
        //3. 判断用户是否存在
        if (userMap.isEmpty()) {
            //4. 不存在，拦截
            return true;
        }
        //5. 将MAP转为User
        User user = BeanUtil.fillBeanWithMap(userMap, new User(), false);

        //6. 存在，保存用户信息到TheadLocal
        UserHolder.saveUser((User) user);

        //7. 刷新token有效时间
        stringRedisTemplate.expire(tokenKey, LOGIN_USER_TTL, TimeUnit.MINUTES);

        //6. 放行
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //移除用户
        UserHolder.removeUser();
    }
}
