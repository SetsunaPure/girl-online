package com.girl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
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

import static com.girl.Common.constants.Constant.DEFAULT_CURRENT;
import static com.girl.Common.constants.Constant.DEFAULT_SIZE;

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
    public ResponseApi getUserManageStatus(JSONObject text) {

        String token = text.getString("token");
        String status = text.getString("status");
        String current = text.getString("current");
        String size = text.getString("size");

        if (RedisUtils.isTokenNull(redisService,token)){
            return new ResponseApi(BgStatusEnum.RESPONSE_NOT_LOGIN, null);
        }

        int lnCurrent = current == null ? DEFAULT_CURRENT : Integer.parseInt(current);
        int lnSize = size == null ? DEFAULT_SIZE : Integer.parseInt(size);
        int lnStatus = Integer.parseInt(status);
        Page page = new Page(lnCurrent, lnSize);



        return null;
    }

    @Override
    public ResponseApi userManageOperate(JSONObject text) {

        String token = text.getString("token");

        if (RedisUtils.isTokenNull(redisService,token)){
            return new ResponseApi(BgStatusEnum.RESPONSE_NOT_LOGIN, null);
        }
        return null;
    }
}
