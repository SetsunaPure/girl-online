package com.girl.Common.utils;


import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;
import com.girl.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class Jiguang {

    @Autowired
    RedisService redisService;

    static Logger logger=LoggerFactory.getLogger(Jiguang.class);

    static JPushClient jPushClient =null;

    String masterSecret = "e73480010742499a128ce710";
    String appKey = "23bbd835554f22f6c724a420";

    public static JPushClient getInstance(){
        if(jPushClient==null){
            reload();
        }
        return jPushClient;
    }

    public static void reload()
    {
        jPushClient = new JPushClient("e73480010742499a128ce710", "23bbd835554f22f6c724a420", null, ClientConfig.getInstance());
    }

    public  void initParam(){
        masterSecret = redisService.get("J_PUSH_APP_SECRET").toString();
        appKey = redisService.get("J_PUSH_APP_KEY").toString();
    }


    public static void alert(String message,String title,Map<String,String> map,String ... uid) throws APIConnectionException, APIRequestException {
        JPushClient instance = getInstance();
        PushPayload build = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias(uid))
                .setNotification(Notification.android(message, title, map))
                .build();
            PushResult result = instance.sendPush(build);
        logger.debug(result.toString());
    }


 }
