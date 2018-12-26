package com.girl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.girl.Common.enums.BgStatusEnum;
import com.girl.Common.model.CertInfo;
import com.girl.Common.model.DynamicInfo;
import com.girl.Common.model.ResponseApi;
import com.girl.Common.utils.RedisUtils;
import com.girl.Common.utils.StringUtils;
import com.girl.core.entity.UserDynamic;
import com.girl.core.mapper.UserDynamicMapper;
import com.girl.service.IUserDynamicService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.girl.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

import static com.girl.Common.constants.Constant.DEFAULT_CURRENT;
import static com.girl.Common.constants.Constant.DEFAULT_SIZE;
import static com.girl.Common.enums.BgStatusEnum.RESPONSE_ERROR;
import static com.girl.Common.enums.BgStatusEnum.RESPONSE_OK;

/**
 * <p>
 * 用户钱包表 服务实现类
 * </p>
 *
 * @author wangpei
 * @since 2018-12-13
 */
@Service
public class UserDynamicServiceImpl extends ServiceImpl<UserDynamicMapper, UserDynamic> implements IUserDynamicService {

    @Autowired
    private UserDynamicMapper userDynamicMapper;

    @Autowired
    private RedisService redisService;

    @Override
    public ResponseApi getDynamicData(JSONObject text) {

        try {
            String status = text.getString("status");
            String token = text.getString("token");
            String current = text.getString("current");
            String size = text.getString("size");
//        String token = (String) RequestContextHolder.currentRequestAttributes().getAttribute("username", 0);

            if (!StringUtils.areNotEmpty(status, token)) {
                return new ResponseApi(BgStatusEnum.RESPONSE_EMPTY, "状态码和认证不能为空");
            }

            if (RedisUtils.isTokenNull(redisService, token)) {
                return new ResponseApi(BgStatusEnum.RESPONSE_NOT_LOGIN, null);
            }

            int lnCurrent = current == null ? DEFAULT_CURRENT : Integer.parseInt(current);
            int lnSize = size == null ? DEFAULT_SIZE : Integer.parseInt(size);
            Page page = new Page(lnCurrent, lnSize);

            List<DynamicInfo> lstDynamicInfo = userDynamicMapper.getDynamicInfo(page, Integer.parseInt(status));

            return new ResponseApi(RESPONSE_OK, lstDynamicInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseApi(RESPONSE_ERROR, e.getMessage());
        }

    }

    @Override
    public ResponseApi operateDynamic(JSONObject text) {
        try {
            String status = text.getString("status");
            String token = text.getString("token");
            String id = text.getString("id");
            if (!StringUtils.areNotEmpty(id, status, token)) {
                return new ResponseApi(BgStatusEnum.RESPONSE_EMPTY, "流水id、状态码和认证不能为空");
            }
            if (RedisUtils.isTokenNull(redisService, token)) {
                return new ResponseApi(BgStatusEnum.RESPONSE_NOT_LOGIN, null);
            }

            UserDynamic ud = new UserDynamic();
            ud.setStatus(Integer.parseInt(status));
            Integer res = userDynamicMapper.update(ud, new EntityWrapper<UserDynamic>().where("id={0}", Integer.parseInt(id)));
            return new ResponseApi(RESPONSE_OK, res);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseApi(RESPONSE_ERROR, e.getMessage());
        }
    }

}
