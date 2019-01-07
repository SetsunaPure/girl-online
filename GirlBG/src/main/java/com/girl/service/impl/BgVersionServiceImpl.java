package com.girl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.girl.Common.enums.BgStatusEnum;
import com.girl.Common.model.ResponseApi;
import com.girl.Common.model.ResponseData;
import com.girl.Common.model.VersionInfo;
import com.girl.Common.utils.RedisUtils;
import com.girl.Common.utils.StringUtils;
import com.girl.Common.utils.UploadFileToQiNiu;
import com.girl.core.entity.BgVersion;
import com.girl.core.mapper.BgVersionMapper;
import com.girl.service.IBgVersionService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.girl.service.RedisService;
import com.qiniu.common.QiniuException;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.girl.Common.constants.Constant.DEFAULT_CURRENT;
import static com.girl.Common.constants.Constant.DEFAULT_SIZE;

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

    @Autowired
    private RedisService redisService;

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
            if (RedisUtils.isTokenNull(redisService, token)) {
                return new ResponseApi(BgStatusEnum.RESPONSE_NOT_LOGIN, null);
            }
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

    public ResponseApi versionList(JSONObject text){
        String token = text.getString("token");
        String current = text.getString("current");
        String size = text.getString("size");


        if (RedisUtils.isTokenNull(redisService, token)) {
            return new ResponseApi(BgStatusEnum.RESPONSE_NOT_LOGIN, null);
        }

        int lnCurrent = current == null ? DEFAULT_CURRENT : Integer.parseInt(current);
        int lnSize = size == null ? DEFAULT_SIZE : Integer.parseInt(size);

        long count = bgVersionMapper.selectCount(new EntityWrapper<BgVersion>());

        List<BgVersion> lstBg = bgVersionMapper.selectPage(new RowBounds(lnCurrent,lnSize),new EntityWrapper<BgVersion>());

        ResponseData info = new ResponseData(count, lstBg);

        return new ResponseApi(BgStatusEnum.RESPONSE_OK, info);
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
