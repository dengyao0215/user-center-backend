package com.yupi.usercenter;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

@SpringBootTest
class UserCenterApplicationTests {

    @Resource
    private RedisTemplate redisTemplate;

    @Test
    void contextLoads() {
    }

    @Test
    void testRedis() {
        //设置值到redis
        redisTemplate.opsForValue().set("ssban","lucy");
        //从redis获取值
        String name = (String)redisTemplate.opsForValue().get("name");
    }

}
