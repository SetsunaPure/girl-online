package com.girl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.girl.Common.model.ResponseApi;
import com.girl.Common.model.VersionInfo;
import com.girl.core.entity.BgVersion;
import com.girl.core.mapper.BgVersionMapper;
import com.girl.service.IBgVersionService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wangpei
 * @since 2018-12-28
 */
@Service
public class BgVersionServiceImpl extends ServiceImpl<BgVersionMapper, BgVersion> implements IBgVersionService {

    @Autowired
    private BgVersionMapper bgVersionMapper;

    @Override
    public JSONObject getLatestVersion(int client) {

        Set<String> set = new HashSet<>();
        set.add("create_time");

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
}
