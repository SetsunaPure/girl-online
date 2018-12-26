package com.girl.service;

import com.alibaba.fastjson.JSONObject;
import com.girl.Common.model.ResponseApi;
import com.girl.core.entity.UserMeet;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 用户钱包表 服务类
 * </p>
 *
 * @author wangpei
 * @since 2018-12-17
 */
public interface IUserMeetService extends IService<UserMeet> {

    ResponseApi getMeetInfo(JSONObject text);

    ResponseApi operateMeet(JSONObject text);
}
