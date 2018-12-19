package com.girl.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.girl.Common.enums.BgStatusEnum;
import com.girl.Common.model.BgApiToken;
import com.girl.Common.model.ResponseApi;
import com.girl.Common.model.ResponseLogin;
import com.girl.core.entity.BgUser;
import com.girl.core.mapper.BgUserMapper;
import com.girl.service.IBgUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.girl.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.girl.Common.utils.UUIDUtils;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public ResponseLogin login(String username, String password){

        BgUser bgUser = new BgUser();
        bgUser.setName(username);
        BgUser rtBguser = bg.selectOne(bgUser);

        if(rtBguser == null){
            return new ResponseLogin(400, "用户名不存在", "", "");
        }

        boolean isEq = password.equals(rtBguser.getPwd());

        /** todo:判断之前是否有登录（redis中是否有token信息） */

        String token = UUIDUtils.getToken();

        BgApiToken apitoken = new BgApiToken();
        apitoken.setCreateTime(new Date());
        apitoken.setToken(token);
        apitoken.setOperteId(rtBguser.getUserId());

        redisService.set(token, apitoken.toString());

        int code = isEq ? 200 : 400;
        String msg = isEq ? "返回正常" : "密码错误";

        return new ResponseLogin(code, msg, token, "");
    }

    @Override
    public ResponseApi addUser(String token, String name, String pwd){
        try {
            BgUser bu = new BgUser();
            bu.setName(name);
            bu.setPwd(pwd);
            bu.setIsAdmin(0);
            Integer res = bg.insert(bu);
            return new ResponseApi(BgStatusEnum.RESPONSE_OK, res);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseApi(BgStatusEnum.RESPONSE_ERROR, "删除客服失败");
        }
    }

    @Override
    public ResponseApi delUser(String token, String id){
        try {
            Integer res = bg.delete(new EntityWrapper<BgUser>().eq("id={0}", id));
            return new ResponseApi(BgStatusEnum.RESPONSE_OK, res);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseApi(BgStatusEnum.RESPONSE_ERROR, "删除客服失败");
        }
    }

}


