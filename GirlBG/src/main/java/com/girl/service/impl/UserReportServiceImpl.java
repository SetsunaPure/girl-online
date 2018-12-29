package com.girl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.girl.Common.enums.BgStatusEnum;
import com.girl.Common.model.*;
import com.girl.Common.utils.RedisUtils;
import com.girl.Common.utils.StringUtils;
import com.girl.Exception.GirlException;
import com.girl.core.entity.UserInfo;
import com.girl.core.entity.UserReport;
import com.girl.core.mapper.UserInfoMapper;
import com.girl.core.mapper.UserReportMapper;
import com.girl.service.IUserReportService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.girl.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.girl.Common.constants.Constant.DEFAULT_CURRENT;
import static com.girl.Common.constants.Constant.DEFAULT_SIZE;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wangpei
 * @since 2018-12-25
 */
@Service
public class UserReportServiceImpl extends ServiceImpl<UserReportMapper, UserReport> implements IUserReportService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserReportMapper userReportMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public ResponseApi getUserManageStatus(JSONObject text) {

        String token = text.getString("token");
        String status = text.getString("status");
        String current = text.getString("current");
        String size = text.getString("size");
        String search = text.getString("search");

        if (RedisUtils.isTokenNull(redisService, token)) {
            return new ResponseApi(BgStatusEnum.RESPONSE_NOT_LOGIN, null);
        }

        if (!StringUtils.areNotEmpty(status, token)) {
            return new ResponseApi(BgStatusEnum.RESPONSE_EMPTY, "状态码和认证不能为空");
        }

        int lnCurrent = current == null ? DEFAULT_CURRENT : Integer.parseInt(current);
        int lnSize = size == null ? DEFAULT_SIZE : Integer.parseInt(size);
        int lnStatus = Integer.parseInt(status);
        Page page = new Page(lnCurrent, lnSize);

        List<OrignReport> orignReports = userReportMapper.getReportInfo(lnStatus, search);

        Map<Integer, ReportInfo> mapUid = new HashMap();

        for (OrignReport or : orignReports) {
            Integer uuid = or.getUid();
            if (mapUid.containsKey(uuid)) {
                ReportInfo report = mapUid.get(uuid);
                mapUid.get(uuid).setReportTimes(report.getReportTimes() + 1);
            } else {
                ReportInfo reportInfo = new ReportInfo();
                reportInfo.setId(or.getUid());
                reportInfo.setName(or.getNickName());
                reportInfo.setType(or.getReportType());
                reportInfo.setCreateTime(or.getCreateTime());
                reportInfo.setReportTimes(1);
                mapUid.put(uuid, reportInfo);
            }

        }

        List<ReportInfo> lstReportInfo = mapUid.entrySet().stream().map(t -> t.getValue()).collect(Collectors.toList());

        ResponseData info = new ResponseData(Long.valueOf(lstReportInfo.size()), lstReportInfo);

        return new ResponseApi(BgStatusEnum.RESPONSE_OK, info);
    }

    @Override
    public ResponseApi getReportDetail(JSONObject text) {

        String token = text.getString("token");
        String uid = text.getString("id");

        if (!StringUtils.areNotEmpty(uid, token)) {
            return new ResponseApi(BgStatusEnum.RESPONSE_EMPTY, "用户ID和认证不能为空");
        }

        if (RedisUtils.isTokenNull(redisService, token)) {
            return new ResponseApi(BgStatusEnum.RESPONSE_NOT_LOGIN, null);
        }

        List<ReportDetail> reportDetails = userReportMapper.getReportDetail(Integer.valueOf(uid));

        ResponseData info = new ResponseData(Long.valueOf(reportDetails.size()), reportDetails);

        return new ResponseApi(BgStatusEnum.RESPONSE_OK, info);
    }

    @Override
    public ResponseApi userManageOperate(JSONObject text) {

        String token = text.getString("token");
        String status = text.getString("status");
        String id = text.getString("id");

        if (!StringUtils.areNotEmpty(status, token)) {
            return new ResponseApi(BgStatusEnum.RESPONSE_EMPTY, "用户ID、状态码和认证不能为空");
        }

        if (RedisUtils.isTokenNull(redisService, token)) {
            return new ResponseApi(BgStatusEnum.RESPONSE_NOT_LOGIN, null);
        }

        Integer lnStatus = Integer.parseInt(status);
        Integer uid = Integer.parseInt(id);

        UserReport userReport = new UserReport();
        userReport.setStatus(lnStatus);

        UserInfo userInfo = new UserInfo();
        userInfo.setStatus(lnStatus);

        try {
            updateUserRecord(uid, userReport, userInfo, lnStatus);
        } catch (GirlException e) {
            return new ResponseApi(BgStatusEnum.RESPONSE_ERROR, e.getMessage());
        }

        return new ResponseApi(BgStatusEnum.RESPONSE_OK);
    }

    @Transactional
    private void updateUserRecord(Integer uid, UserReport userReport, UserInfo userInfo, Integer status) throws GirlException {
        try {
            if (status == 0){
                //解冻
                userReportMapper.delete(new EntityWrapper<UserReport>().eq("uid", uid));
            }else {
                //冻结
                userReportMapper.update(userReport, new EntityWrapper<UserReport>().eq("uid", uid));
            }
            userInfoMapper.update(userInfo, new EntityWrapper<UserInfo>().eq("id", uid));
        }catch (Exception e){
            e.printStackTrace();
            throw new GirlException(400,"更新用户管理状态失败");
        }
    }
}
