package com.girl.service;

import com.alibaba.fastjson.JSONObject;
import com.girl.Common.model.ResponseApi;
import com.girl.Exception.GirlException;
import com.girl.core.entity.BgVersion;
import com.baomidou.mybatisplus.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wangpei
 * @since 2018-12-28
 */
public interface IBgVersionService extends IService<BgVersion> {

    JSONObject getLatestVersion(int client);

    ResponseApi uploadVersions(MultipartFile file, String token, String info,
                              String versionCode, String versionName, Integer updateType, Integer phoneType) throws GirlException;

    ResponseApi versionList(JSONObject text);

}
