package com.girl.controller;


import com.alibaba.fastjson.JSONObject;
import com.girl.Common.model.ResponseApi;
import com.girl.service.IBgVersionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
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
    public JSONObject getLatestVersion(@RequestParam(value = "client") int client){
        return bgVersionService.getLatestVersion(client);
    }

}

