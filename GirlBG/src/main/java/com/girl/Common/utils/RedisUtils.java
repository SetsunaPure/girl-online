package com.girl.Common.utils;

import com.girl.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;

public class RedisUtils {

    public static boolean isTokenNull(RedisService redisService, String token) {
        if (token == null){
            return true;
        }
        if (redisService.get(token) == null){
            return true;
        }
        return false;
    }
}
