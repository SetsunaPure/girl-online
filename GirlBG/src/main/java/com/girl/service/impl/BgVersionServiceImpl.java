package com.girl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.girl.Common.enums.BgStatusEnum;
import com.girl.Common.model.ResponseApi;
import com.girl.Common.model.VersionInfo;
import com.girl.Common.utils.StringUtils;
import com.girl.Common.utils.UploadFileToQiNiu;
import com.girl.core.entity.BgVersion;
import com.girl.core.mapper.BgVersionMapper;
import com.girl.service.IBgVersionService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiniu.common.QiniuException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wangpei
 * @since 2018-12-28
 */
@Service
public class BgVersionServiceImpl extends ServiceImpl<BgVersionMapper, BgVersion> implements IBgVersionService {

    @Autowired
    private BgVersionMapper bgVersionMapper;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public JSONObject getLatestVersion(int client) {

        Set<String> set = new HashSet<>();
        set.add("id");

        BgVersion bv = bgVersionMapper.selectList(new EntityWrapper<BgVersion>().orderDesc(set)).get(0);

        VersionInfo versionInfo = new VersionInfo();
        versionInfo.setAndroid_url(bv.getUrl());
        versionInfo.setInfo(bv.getInfo());
        versionInfo.setUpdate_type(bv.getUpdateType());
        versionInfo.setVersion_code(Integer.valueOf(bv.getVersionCode()));
        versionInfo.setVersion_name(bv.getVersionName());

        JSONObject obj = new JSONObject();
        obj.put("data", versionInfo);
        obj.put("code", 200);
        obj.put("errorInfo", "获取最新版本成功");

        return obj;
    }

    @Override
    public ResponseApi uploadVersions(MultipartFile file, String token, String info,
                                     String versionCode, String versionName, Integer updateType) {

        try {
            String apkUrl = getFileUrl(file);
            BgVersion bgVersion = new BgVersion();
            bgVersion.setCreateTime(new Date());
            bgVersion.setInfo(info);
            bgVersion.setUrl("apkUrl");
            bgVersion.setUpdateType(updateType);
            bgVersion.setVersionCode(versionCode);
            bgVersion.setVersionName(versionName);

            bgVersionMapper.insert(bgVersion);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("版本信息存库失败" + e.getMessage());
            return new ResponseApi(BgStatusEnum.RESPONSE_ERROR, "版本信息存库失败" + e.getMessage());
        }
        return new ResponseApi(BgStatusEnum.RESPONSE_OK);
    }

    private String getFileUrl(MultipartFile file){
        return null;
    }

    public static void main(String[] args) {
        File file = new File("F:\\app-release.apk");

        try {
            UploadFileToQiNiu.uploadByFile(file,"images","20180229/app-release.apk");
        } catch (QiniuException e) {
            e.printStackTrace();
        }
    }

}
