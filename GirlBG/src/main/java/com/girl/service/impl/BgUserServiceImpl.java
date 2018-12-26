package com.girl.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.girl.Common.enums.BgStatusEnum;
import com.girl.Common.model.BgApiToken;
import com.girl.Common.model.ResponseApi;
import com.girl.Common.model.ResponseLogin;
import com.girl.Common.utils.RedisUtils;
import com.girl.Common.utils.StringUtils;
import com.girl.core.entity.BgToken;
import com.girl.core.entity.BgUser;
import com.girl.core.mapper.BgTokenMapper;
import com.girl.core.mapper.BgUserMapper;
import com.girl.service.IBgUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.girl.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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

    @Autowired
    BgTokenMapper tokenMapper;

    @Override
    @Transactional
    public ResponseLogin login(JSONObject text){

        String username = text.getString("username");
        String pwd = text.getString("password");

        if(!StringUtils.areNotEmpty(username, pwd)){
            return new ResponseLogin(401, "用户名密码不能为空",null,null);
        }

        BgUser bgUser = new BgUser();
        bgUser.setName(username);
        BgUser rtBguser = bg.selectOne(bgUser);

        if(rtBguser == null){
            return new ResponseLogin(BgStatusEnum.RESPONSE_USER_NOT_EXIST);
        }

        boolean isEq = pwd.equals(rtBguser.getPwd());

        if(!isEq){
            return new ResponseLogin(BgStatusEnum.RESPONSE_PASSWORD_ERROR);
        }

        String dbToken = bg.getTokenByName(username);
        if(null != dbToken){
            return new ResponseLogin(BgStatusEnum.RESPONSE_IS_LOGIN, dbToken);
        }

        String token = UUIDUtils.getToken();

        BgToken bgToken = new BgToken();
        bgToken.setCreateTime(new Date());
        bgToken.setToken(token);
        bgToken.setLoginId(rtBguser.getUserId());

        tokenMapper.insert(bgToken);

        redisService.set(token, bgToken);

//        List<BgUser> d = selectPage(new Page<BgUser>(1,50)).getRecords();

        return new ResponseLogin(BgStatusEnum.RESPONSE_OK, token);
    }

    @Override
    public ResponseApi addUser(JSONObject text){
        try {
            String token = text.getString("token");
            String name = text.getString("name");
            String pwd = text.getString("pwd");
            if(!StringUtils.areNotEmpty(name, pwd,token)){
                return new ResponseApi(BgStatusEnum.RESPONSE_EMPTY, "用户名、参数、认证不能为空");
            }

            if (RedisUtils.isTokenNull(redisService,token)){
                return new ResponseApi(BgStatusEnum.RESPONSE_NOT_LOGIN, null);
            }

            BgUser bu = new BgUser();
            bu.setName(name);
            bu.setPwd(pwd);
            bu.setIsAdmin(0);
            Integer res = bg.insert(bu);
            return new ResponseApi(BgStatusEnum.RESPONSE_OK, res);
        }catch (DuplicateKeyException e){
            e.printStackTrace();
            return new ResponseApi(BgStatusEnum.RESPONSE_USER_EXIST);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseApi(BgStatusEnum.RESPONSE_ERROR, "新增客服失败");
        }
    }

    @Override
    public ResponseApi delUser(JSONObject text){
        try {
            String token = text.getString("token");
            String id = text.getString("id");
            if(!StringUtils.areNotEmpty(token, id)){
                return new ResponseApi(BgStatusEnum.RESPONSE_EMPTY, "流水id和认证不能为空");
            }

            if (RedisUtils.isTokenNull(redisService,token)){
                return new ResponseApi(BgStatusEnum.RESPONSE_NOT_LOGIN, null);
            }

            Integer res = bg.delete(new EntityWrapper<BgUser>().eq("user_id={0}", id));
            return new ResponseApi(BgStatusEnum.RESPONSE_OK, res);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseApi(BgStatusEnum.RESPONSE_ERROR, "删除客服失败");
        }
    }

}


