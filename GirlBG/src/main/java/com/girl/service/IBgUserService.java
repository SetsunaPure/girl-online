package com.girl.service;

import com.alibaba.fastjson.JSONObject;
import com.girl.Common.model.ResponseApi;
import com.girl.Common.model.ResponseLogin;
import com.girl.core.entity.BgUser;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wangpei
 * @since 2018-12-13
 */
public interface IBgUserService extends IService<BgUser> {

    ResponseApi login(JSONObject text);

    ResponseApi addUser(JSONObject text);

    ResponseApi delUser(JSONObject text);
}
