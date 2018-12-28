package com.girl.controller;


import com.alibaba.fastjson.JSONObject;
import com.girl.Common.enums.BgStatusEnum;
import com.girl.Common.model.ResponseApi;
import com.girl.Common.utils.StringUtils;
import com.girl.service.IUserReportService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * 用户管理
 *
 * @author wangpei
 * @since 2018-12-25
 */
@RestController
@RequestMapping("/bg/usermanage")
public class UserReportController {

    @Autowired
    IUserReportService userReportService;

    @PostMapping("/status")
    @ApiOperation("用户状态")
    public ResponseApi getUserManageStatus(@RequestBody JSONObject text) {
        try {
            return userReportService.getUserManageStatus(text);
        } catch (Exception e) {
            return new ResponseApi(BgStatusEnum.RESPONSE_ERROR, e.getMessage());
        }

    }

    @PostMapping("/detail")
    @ApiOperation("用户详情")
    public ResponseApi getUserManageDetail(@RequestBody JSONObject text) {
        try {
            return userReportService.getReportDetail(text);
        } catch (Exception e) {
            return new ResponseApi(BgStatusEnum.RESPONSE_ERROR, e.getMessage());
        }

    }


    @PostMapping("/operate")
    @ApiOperation("用户审核操作")
    public ResponseApi userManageOperate(@RequestBody JSONObject text) {
        try {
            return userReportService.userManageOperate(text);
        } catch (Exception e) {
            return new ResponseApi(BgStatusEnum.RESPONSE_ERROR, e.getMessage());
        }
    }


}

