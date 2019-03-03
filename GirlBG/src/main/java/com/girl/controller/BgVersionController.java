package com.girl.controller;


import com.alibaba.fastjson.JSONObject;
import com.girl.Common.model.ResponseApi;
import com.girl.Exception.GirlException;
import com.girl.service.IBgVersionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wangpei
 * @since 2018-12-28
 */
@RestController
@RequestMapping("/bg/version")
public class BgVersionController {

    @Autowired
    private IBgVersionService bgVersionService;

    @GetMapping("/latest")
    @ApiOperation("获取最新版本")
    public JSONObject getLatestVersion(@RequestParam(value = "client") int client) {
        return bgVersionService.getLatestVersion(client);
    }

    @PostMapping("/upload")
    @ApiOperation("上传版本")
    public ResponseApi uploadVersions(@RequestParam(value ="file", required = false) MultipartFile file,
                                      @RequestParam(value ="token", required = false) String token,
                                      @RequestParam(value = "info", required = false) String info,
                                      @RequestParam(value = "version_code", required = false) String versionCode,
                                      @RequestParam(value = "version_name", required = false) String versionName,
                                      @RequestParam(value = "update_type", required = false) Integer updateType,
                                      @RequestParam(value = "phone_type", required = false) Integer phoneType) throws GirlException

    {
        return bgVersionService.uploadVersions(file, token, info, versionCode, versionName, updateType, phoneType);
    }

    @PostMapping("/list")
    @ApiOperation("版本列表")
    public ResponseApi versionList(@RequestBody JSONObject text){
        return bgVersionService.versionList(text);
    }


}

