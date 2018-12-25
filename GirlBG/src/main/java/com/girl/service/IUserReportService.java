package com.girl.service;

import com.girl.Common.model.ResponseApi;
import com.girl.core.entity.UserReport;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wangpei
 * @since 2018-12-25
 */
public interface IUserReportService extends IService<UserReport> {

    ResponseApi getUserManageStatus(String token, String status);

    ResponseApi userManageOperate(String token, String id, String status);
}
