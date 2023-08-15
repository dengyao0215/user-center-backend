package com.yupi.usercenter.config;

import com.yupi.usercenter.utils.LoginInterceptor;
import com.yupi.usercenter.utils.RefreshTokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/***
 * @title McvConfig
 * @description TODO 拦截器配置类
 * @author Skadhi
 * @version 1.0.0
 * @create 2023-03-01 20:28
 **/
@Configuration
public class McvConfig implements WebMvcConfigurer {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .excludePathPatterns(
                        "/user/register",
                        "/user/login",
                        "/user/logout"
                ).order(1);

        // token刷新的拦截器
        registry.addInterceptor(new RefreshTokenInterceptor(stringRedisTemplate)).addPathPatterns("/**").order(0);
    }
}
