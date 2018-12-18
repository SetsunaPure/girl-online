package com.girl.service;

import com.girl.Common.model.ResponseApi;
import com.girl.core.entity.UserDynamic;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 用户钱包表 服务类
 * </p>
 *
 * @author wangpei
 * @since 2018-12-13
 */
public interface IUserDynamicService extends IService<UserDynamic> {
    ResponseApi getDynamicData(String token, String status);

    ResponseApi operateDynamic(String token, String id, String status);
}
