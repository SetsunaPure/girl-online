package com.girl.service;

import com.girl.Common.model.ResponseApi;
import com.girl.core.entity.UserTixian;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wangpei
 * @since 2018-12-17
 */
public interface IUserTixianService extends IService<UserTixian> {

    ResponseApi getDrawingStatus(String token, String status);

    ResponseApi operateDrawing(String token, String id, String status);
}
