package com.girl.service;

import com.alibaba.fastjson.JSONObject;
import com.girl.Common.model.ResponseApi;
import com.girl.core.entity.UserCertInfo;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wangpei
 * @since 2018-12-17
 */
public interface IUserCertInfoService extends IService<UserCertInfo> {

    ResponseApi certInfoStatus(JSONObject text);

    ResponseApi operateCertInfo(JSONObject text);
}
