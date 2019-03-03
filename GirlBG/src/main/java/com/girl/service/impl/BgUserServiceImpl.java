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
import com.girl.Exception.GirlException;
import com.girl.core.entity.BgToken;
import com.girl.core.entity.BgUser;
import com.girl.core.mapper.BgTokenMapper;
import com.girl.core.mapper.BgUserMapper;
import com.girl.service.IBgUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.girl.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import com.girl.Common.utils.UUIDUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

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

    @Autowired
    BgUserMapper userMapper;

    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    @Transactional
    public ResponseApi login(JSONObject text){

        String username = text.getString("username");
        String pwd = text.getString("password");

        if(!StringUtils.areNotEmpty(username, pwd)){
            return new ResponseApi(401, "用户名密码不能为空",null);
        }

        BgUser bgUser = new BgUser();
        bgUser.setName(username);
        BgUser rtBguser = bg.selectOne(bgUser);

        if(rtBguser == null){
            return new ResponseApi(BgStatusEnum.RESPONSE_USER_NOT_EXIST);
        }

        boolean isEq = pwd.equals(rtBguser.getPwd());

        if(!isEq){
            return new ResponseApi(BgStatusEnum.RESPONSE_PASSWORD_ERROR);
        }

//        String dbToken = bg.getTokenByName(username);
//        if(null != dbToken){
//            return new ResponseApi(BgStatusEnum.RESPONSE_IS_LOGIN, dbToken);
//        }

        String token = UUIDUtils.getToken();

        BgToken bgToken = new BgToken();
        bgToken.setCreateTime(new Date());
        bgToken.setToken(token);
        bgToken.setLoginId(rtBguser.getUserId());

        tokenMapper.insert(bgToken);

        redisService.set(token, bgToken);

//        List<BgUser> d = selectPage(new Page<BgUser>(1,50)).getRecords();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", token);
        jsonObject.put("name", rtBguser.getName());
        jsonObject.put("is_admin", rtBguser.getIsAdmin());

        return new ResponseApi(BgStatusEnum.RESPONSE_OK, jsonObject);
    }

    @Override
    public ResponseApi addUser(JSONObject text){
        try {
            String token = text.getString("token");
            String name = text.getString("name");
            String pwd = text.getString("pwd");
            String account = text.getString("account");
            String isAdmin = text.getString("isAdmin");

            if(!StringUtils.areNotEmpty(name, pwd,token)){
                return new ResponseApi(BgStatusEnum.RESPONSE_EMPTY, "用户名、参数、认证不能为空");
            }

            if (RedisUtils.isTokenNull(redisService,token)){
                return new ResponseApi(BgStatusEnum.RESPONSE_NOT_LOGIN, null);
            }

            //校验参数重复
            if(bg.selectCount(new EntityWrapper<BgUser>().eq("name", name)) != 0){
                return new ResponseApi(BgStatusEnum.RESPONSE_USER_EXIST);
            }
            if(bg.selectCount(new EntityWrapper<BgUser>().eq("account", account)) != 0){
                return new ResponseApi(BgStatusEnum.RESPONSE_COUNNT_ESIST);
            }

            BgUser bu = new BgUser();
            bu.setName(name);

            if(bg.selectOne(bu) != null){
                return new ResponseApi(BgStatusEnum.RESPONSE_USER_EXIST);
            }
            bu.setPwd(pwd);
            bu.setIsAdmin(Integer.valueOf(isAdmin));
            bu.setAccount(account);
            bu.setName(name);
            bg.insert(bu);
            logger.info("新增客服成功");

            JSONObject res = new JSONObject();
            res.put("costomer_id",bg.selectOne(bu).getUserId());

            return new ResponseApi(BgStatusEnum.RESPONSE_OK, res);
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

            Integer res = bg.delete(new EntityWrapper<BgUser>().eq("user_id", id));
            return new ResponseApi(BgStatusEnum.RESPONSE_OK, res);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseApi(BgStatusEnum.RESPONSE_ERROR, "删除客服失败");
        }
    }

    public ResponseApi getUsers(JSONObject text){
        try {
            String token = text.getString("token");
            if (StringUtils.isEmpty(token)) {
                return new ResponseApi(BgStatusEnum.RESPONSE_EMPTY, "认证不能为空");
            }

            if (RedisUtils.isTokenNull(redisService, token)) {
                return new ResponseApi(BgStatusEnum.RESPONSE_NOT_LOGIN, null);
            }

            List<BgUser> users = userMapper.selectList(new EntityWrapper<BgUser>().where("name!='admin'"));
            return new ResponseApi(BgStatusEnum.RESPONSE_OK, users);
        }catch (Exception e){
            return new ResponseApi(BgStatusEnum.RESPONSE_ERROR, "读取客服列表失败");
        }

    }

    public ResponseApi modifyPassword(JSONObject text){
        String token = text.getString("token");

        if (StringUtils.isEmpty(token)) {
            return new ResponseApi(BgStatusEnum.RESPONSE_EMPTY, "认证不能为空");
        }

        if (RedisUtils.isTokenNull(redisService, token)) {
            return new ResponseApi(BgStatusEnum.RESPONSE_NOT_LOGIN, null);
        }

        BgToken bgToken = (BgToken)redisService.get(token);

        try {
            deletUserRecord(token, bgToken);
        } catch (GirlException e) {
            return new ResponseApi(e.getCode(),null, e.getMessage());
        }

        return new ResponseApi(BgStatusEnum.RESPONSE_OK);
    }

    @Transactional
    private void deletUserRecord(String token, BgToken bgToken) throws GirlException {
        try {
            userMapper.delete(new EntityWrapper<BgUser>().eq("user_id", bgToken.getLoginId()));
            tokenMapper.delete(new EntityWrapper<BgToken>().eq("token", token));
        }catch (Exception e){
            throw new GirlException(400, "删除客服失败");
        }
    }

}


