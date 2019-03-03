package com.girl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.girl.Common.constants.Constant;
import com.girl.Common.enums.BgStatusEnum;
import com.girl.Common.model.ResponseApi;
import com.girl.Common.model.ResponseData;
import com.girl.Common.model.VersionInfo;
import com.girl.Common.utils.RedisUtils;
import com.girl.Common.utils.StringUtils;
import com.girl.Common.utils.UploadFileToQiNiu;
import com.girl.Exception.GirlException;
import com.girl.core.entity.BgVersion;
import com.girl.core.mapper.BgVersionMapper;
import com.girl.service.IBgVersionService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.girl.service.RedisService;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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

    public static final int PHONE_IOS = 1;
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
    public ResponseApi uploadVersions(MultipartFile file, String token, String info, String versionCode,
                                      String versionName, Integer updateType, Integer phoneType) throws GirlException {

        try {
            if (RedisUtils.isTokenNull(redisService, token)) {
                return new ResponseApi(BgStatusEnum.RESPONSE_NOT_LOGIN, null);
            }

            //若无版本则上传
            BgVersion bgVersion0 = new BgVersion();
            bgVersion0.setPhoneType(phoneType);
            if (bgVersionMapper.selectOne(bgVersion0) == null) {
                BgVersion bgVersion = new BgVersion();
                bgVersion.setCreateTime(new Date());
                bgVersion.setInfo(info);
                bgVersion.setUrl(file == null ? null : getFileUrl(file));
                bgVersion.setUpdateType(updateType);
                bgVersion.setVersionCode(versionCode);
                bgVersion.setVersionName(versionName);
                bgVersion.setPhoneType(phoneType);

                bgVersionMapper.insert(bgVersion);
            } else {
                BgVersion bgVersion1 = new BgVersion();
                bgVersion1.setUrl(file == null ? null : getFileUrl(file));
                bgVersion1.setCreateTime(new Date());
                bgVersion1.setVersionCode(versionCode);
                bgVersion1.setVersionName(versionName);
                bgVersion1.setUpdateType(updateType);
                bgVersion1.setInfo(info);

                bgVersionMapper.update(bgVersion1, getPhoneTypeWrapper(phoneType));
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("版本信息存库失败" + e.getMessage());
            return new ResponseApi(BgStatusEnum.RESPONSE_ERROR, "版本信息存库失败" + e.getMessage());
        }
        return new ResponseApi(BgStatusEnum.RESPONSE_OK);
    }

    private Wrapper getPhoneTypeWrapper(Integer phoneType) {
        Wrapper phoneTypeWapper = new EntityWrapper<BgVersion>().eq("phone_type", phoneType);
        return phoneTypeWapper;
    }

    public ResponseApi versionList(JSONObject text) {

        String token = text.getString("token");
        String current = text.getString("current");
        String size = text.getString("size");


        if (RedisUtils.isTokenNull(redisService, token)) {
            return new ResponseApi(BgStatusEnum.RESPONSE_NOT_LOGIN, null);
        }

        int lnCurrent = current == null ? DEFAULT_CURRENT : Integer.parseInt(current);
        int lnSize = size == null ? DEFAULT_SIZE : Integer.parseInt(size);

        long count = bgVersionMapper.selectCount(new EntityWrapper<BgVersion>());

        List<BgVersion> lstBg = bgVersionMapper.selectPage(new RowBounds(lnCurrent, lnSize), new EntityWrapper<BgVersion>());

        ResponseData info = new ResponseData(count, lstBg);

        return new ResponseApi(BgStatusEnum.RESPONSE_OK, info);
    }

    private String getFileUrl(MultipartFile file) throws GirlException, IOException {
        //上传七牛云
        Configuration cfg = new Configuration(Zone.zone2());
        Auth auth = Auth.create(redisService.get("QINIU_ACCESSKEY").toString(),
                redisService.get("QINIU_SECRETKEY").toString());

        UploadManager uploadManager = new UploadManager(cfg);
        String uri = "yanzhigongshe" + ".apk";
        InputStream inputStream = file.getInputStream();
        try {
            UploadFileToQiNiu.uploadByInputStream(
                    redisService.get("QINIU_ACCESSKEY").toString(),
                    redisService.get("QINIU_SECRETKEY").toString(),
                    inputStream,
                    "images",
                    uri);
//            uploadManager.put(file.getInputStream(), uri, auth.uploadToken("images"), null, null);
        } catch (QiniuException e) {
            logger.error("上传apk失败");
            throw new GirlException(500, "上传apk失败");
        }

        return redisService.get("QINIU_DOMAINNAMEimages").toString() + uri;
    }

    public static void main(String[] args) {
//        File file = new File("F:\\app-release.apk");
        File file = new File("F:\\1.jpg");
        try {
            Configuration cfg = new Configuration(Zone.zone0());
            Auth auth = Auth.create(Constant.QINIU_ACCESSKEY, Constant.QINIU_SECRETKEY);

            UploadManager uploadManager = new UploadManager(cfg);
//            String token = getUpToken(bucketname, auth);
            uploadManager.put(file, "20190111/test.jpg", auth.uploadToken("images"));
//            UploadFileToQiNiu.uploadByFile(file,"images","20190111/app-release.apk");
//            UploadFileToQiNiu.uploadByFile(file,"images","20190111/test.jpg");

        } catch (QiniuException e) {
            e.printStackTrace();
        }
    }

}
