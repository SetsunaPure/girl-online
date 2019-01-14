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
    public ResponseApi uploadVersions(@RequestParam("file") MultipartFile file,
                                      @RequestParam("token") String token,
                                      @RequestParam("info") String info,
                                      @RequestParam("version_code") String versionCode,
                                      @RequestParam("version_name") String versionName,
                                      @RequestParam("update_type") Integer updateType) throws GirlException

    {
        return bgVersionService.uploadVersions(file, token, info, versionCode, versionName, updateType);
    }

    @PostMapping("/list")
    @ApiOperation("版本列表")
    public ResponseApi versionList(@RequestBody JSONObject text){
        return bgVersionService.versionList(text);
    }


}

