package com.girl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.girl.Common.enums.BgStatusEnum;
import com.girl.Common.model.BgApiToken;
import com.girl.Common.model.CertInfo;
import com.girl.Common.model.ResponseApi;
import com.girl.Common.utils.RedisUtils;
import com.girl.Common.utils.StringUtils;
import com.girl.core.entity.UserCertInfo;
import com.girl.core.mapper.UserCertInfoMapper;
import com.girl.service.IUserCertInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.girl.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import java.util.List;

import static com.girl.Common.constants.Constant.DEFAULT_CURRENT;
import static com.girl.Common.constants.Constant.DEFAULT_SIZE;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wangpei
 * @since 2018-12-17
 */
@Service
public class UserCertInfoServiceImpl extends ServiceImpl<UserCertInfoMapper, UserCertInfo> implements IUserCertInfoService {

    @Autowired
    UserCertInfoMapper userCertInfoMapper;

    @Autowired
    private RedisService redisService;

    @Override
    public ResponseApi certInfoStatus(JSONObject text) {

        try {

            String status = text.getString("status");
            String token = text.getString("token");
            String current = text.getString("current");
            String size = text.getString("size");

            if (!StringUtils.areNotEmpty(status, token)) {
                return new ResponseApi(BgStatusEnum.RESPONSE_EMPTY, "状态码和认证不能为空");
            }
            if (RedisUtils.isTokenNull(redisService, token)) {
                return new ResponseApi(BgStatusEnum.RESPONSE_NOT_LOGIN, null);
            }

            int lnCurrent = current == null ? DEFAULT_CURRENT : Integer.parseInt(current);
            int lnSize = size == null ? DEFAULT_SIZE : Integer.parseInt(size);
            Page page = new Page(lnCurrent, lnSize);
            //默认选择第一页，50条记录
            List<CertInfo> lstCertInfo = userCertInfoMapper.getCertInfo(new Page(1, 50), Integer.parseInt(status));

            return new ResponseApi(BgStatusEnum.RESPONSE_OK, lstCertInfo);
        } catch (Exception e) {
            e.getMessage();
            return new ResponseApi(BgStatusEnum.RESPONSE_ERROR, "读取认证信息失败");
        }

    }

    @Override
    @Transactional
    public ResponseApi operateCertInfo(JSONObject text) {

        try {

            String id = text.getString("id");
            String token = text.getString("token");
            String status = text.getString("status");
            if (!StringUtils.areNotEmpty(id, status, token)) {
                return new ResponseApi(BgStatusEnum.RESPONSE_EMPTY, "流水id、状态码和认证不能为空");
            }

            if (RedisUtils.isTokenNull(redisService, token)) {
                return new ResponseApi(BgStatusEnum.RESPONSE_NOT_LOGIN, null);
            }

            UserCertInfo uci = new UserCertInfo();
            uci.setStatus(Integer.parseInt(status));
            Integer res = userCertInfoMapper.update(uci, new EntityWrapper<UserCertInfo>().eq("id={0}", id));
            return new ResponseApi(BgStatusEnum.RESPONSE_OK, res);
        } catch (Exception e) {
            e.getMessage();
            return new ResponseApi(BgStatusEnum.RESPONSE_ERROR, "更新认证信息失败");
        }
    }

}
