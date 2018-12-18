package com.girl.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.girl.Common.model.PubApiToken;
import com.girl.core.entity.BgUser;
import com.girl.core.mapper.BgUserMapper;
import com.girl.service.IBgUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.girl.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.girl.Common.utils.UUIDUtils;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wangpei
 * @since 2018-12-13
 */
@Service
public class BgUserServiceImpl extends ServiceImpl<BgUserMapper, BgUser> implements IBgUserService {
    @Autowired
    BgUserMapper bg;

    @Autowired
    RedisService redisService;

    @Override
    public JSONObject login(String username, String password){
        BgUser bgUser = new BgUser();
        bgUser.setName(username);
        BgUser rtBguser = bg.selectOne(bgUser);

        boolean isEq = password.equals(rtBguser.getPwd());

        /** todo:判断之前是否有登录（redis中是否有token信息） */

        String token = UUIDUtils.getToken();

        PubApiToken apitoken = new PubApiToken();
        apitoken.setCreateTime(new Date());
        apitoken.setToken(token);
        apitoken.setAuthsId(rtBguser.getUserId());

        redisService.add(token, apitoken);

        System.out.println(redisService.get(token));
        int code;
        String msg;
        if (isEq) {
            code = 200;
            msg = "正常返回";
        } else {
            code = 400;
            msg = "返回異常";
        }

        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("msg", msg);
        json.put("token", apitoken);
        json.put("data", "");

        return json;
    }

    public JSONObject addUser(String token){
        return null;
    }

}


