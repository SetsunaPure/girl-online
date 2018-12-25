package com.girl.service.impl;

import com.girl.Common.enums.BgStatusEnum;
import com.girl.Common.model.ResponseApi;
import com.girl.Common.utils.RedisUtils;
import com.girl.core.entity.UserReport;
import com.girl.core.mapper.UserReportMapper;
import com.girl.service.IUserReportService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.girl.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wangpei
 * @since 2018-12-25
 */
@Service
public class UserReportServiceImpl extends ServiceImpl<UserReportMapper, UserReport> implements IUserReportService {

    @Autowired
    private RedisService redisService;

    @Override
    public ResponseApi getUserManageStatus(String token, String status) {

        if (RedisUtils.isTokenNull(redisService,token)){
            return new ResponseApi(BgStatusEnum.RESPONSE_NOT_LOGIN, null);
        }

        return null;
    }

    @Override
    public ResponseApi userManageOperate(String token, String id, String status) {

        if (RedisUtils.isTokenNull(redisService,token)){
            return new ResponseApi(BgStatusEnum.RESPONSE_NOT_LOGIN, null);
        }
        return null;
    }
}
