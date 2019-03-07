package com.girl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.girl.Common.constants.Constant;
import com.girl.Common.enums.BgStatusEnum;
import com.girl.Common.model.*;
import com.girl.Common.utils.*;
import com.girl.core.entity.UserCertInfo;
import com.girl.core.entity.UserInfo;
import com.girl.core.entity.UserMsg;
import com.girl.core.mapper.UserCertInfoMapper;
import com.girl.core.mapper.UserInfoMapper;
import com.girl.service.IUserCertInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.girl.service.RedisService;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    UserInfoMapper userInfoMapper;

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserNotice userNotice;

    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public ResponseApi certInfoStatus(JSONObject text) {

        try {

            String status = text.getString("status");
            String token = text.getString("token");
            String current = text.getString("current");
            String size = text.getString("size");
            String search = text.getString("search");

            if (!StringUtils.areNotEmpty(status, token)) {
                return new ResponseApi(BgStatusEnum.RESPONSE_EMPTY, "状态码和认证不能为空");
            }
            if (RedisUtils.isTokenNull(redisService, token)) {
                return new ResponseApi(BgStatusEnum.RESPONSE_NOT_LOGIN, null);
            }

            int lnCurrent = current == null ? DEFAULT_CURRENT : Integer.parseInt(current);
            int lnSize = size == null ? DEFAULT_SIZE : Integer.parseInt(size);
            int lnStatus = Integer.parseInt(status);
            Page page = new Page(lnCurrent, lnSize);

            //默认选择第一页，50条记录
            List<CertInfo> lstCertInfo = userCertInfoMapper.getCertInfo(page, lnStatus, search);

//            long count = userCertInfoMapper.selectCount(new EntityWrapper<UserCertInfo>().eq("status", lnStatus));
            long count = userCertInfoMapper.getCertCount(lnStatus, search);
            ResponseData info = new ResponseData(count, lstCertInfo);

            return new ResponseApi(BgStatusEnum.RESPONSE_OK, info);
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

            Integer lnid = Integer.parseInt(id);
            Integer lnStatus = Integer.parseInt(status);

            UserCertInfo uci = new UserCertInfo();
            uci.setStatus(Integer.parseInt(status));
            uci.setId(Long.valueOf(id));

            Integer res = userCertInfoMapper.update(uci, new EntityWrapper<UserCertInfo>().eq("id", lnid));
            userCertInfoMapper.updateInfoCert(lnid, lnStatus== 2 ? 0 : 1);
            try {
                //推送认证消息
                pushCertMessages(id, lnStatus, uci);
            }catch (Exception e){
                logger.error("推送认证消息给客户端失败");
            }
            return new ResponseApi(BgStatusEnum.RESPONSE_OK, res);
        } catch (Exception e) {
            e.getMessage();
            return new ResponseApi(BgStatusEnum.RESPONSE_ERROR, "更新认证信息失败");
        }
    }

    private void pushCertMessages(String id, Integer lnStatus, UserCertInfo uci) {
        UserCertInfo userCertInfo = userCertInfoMapper.selectOne(uci);

        UserMsg userMsg = new UserMsg();
        userMsg.setSubType(30);
        userMsg.setBindId(Long.valueOf(id));
        userMsg.setUid(userCertInfo.getUid());
        userMsg.setCreateTime(new Date());

        //推送消息给用户
        if(lnStatus == 1){
            userMsg.setMsg("您提交的认证审核通过");
        } else if(lnStatus == 2){
            userMsg.setMsg("您提交的认证审核未通过");
        }

        logger.info(userMsg.getMsg());

        UserInfo userInformation = new UserInfo();
        userInformation.setId(userCertInfo.getUid());

        UserInfo userInfo = userInfoMapper.selectOne(userInformation);

        UserIcon userIcon = new UserIcon();
        userIcon.setNickName(userInfo.getNickName());
        userIcon.setUid(userInfo.getId());
        userIcon.setAvatar(userInfo.getAvatar());

        Map<String, String> extend = new HashMap();
        extend.put("certId", userCertInfo.getId().toString());

        //激光推送认证消息
        pushCertMessage( userMsg, userIcon, extend);
    }

    private void pushCertMessage( UserMsg userMsg, UserIcon userIcon, Map<String, String> extend) {
//        UserNotice userNotice = new UserNotice();
        userNotice.sendMessage(userMsg, userIcon, extend, "认证通知");
    }

    public static void main(String[] args) throws QiniuException {
        File temFile = new File("F:\\1316.apk");
        Auth auth = Auth.create(Constant.QINIU_ACCESSKEY, Constant.QINIU_SECRETKEY);
        Configuration cfg = new Configuration(Zone.zone2());
        UploadManager uploadManager = new UploadManager(cfg);
        String token = auth.uploadToken("images");
        uploadManager.put(temFile, "20190113/test.apk", token);
//        UploadFileToQiNiu.uploadByFile( temFile, "images2", "20190113/test.jpg");

    }
}
