package com.girl.controller;


import com.alibaba.fastjson.JSONObject;
import com.girl.Common.enums.BgStatusEnum;
import com.girl.Common.model.BgApiToken;
import com.girl.Common.model.ResponseApi;
import com.girl.Common.utils.StringUtils;
import com.girl.Exception.GirlException;
import com.girl.service.IUserCertInfoService;
import com.girl.service.RedisService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wangpei
 * @since 2018-12-17
 */
@RestController
@RequestMapping("/bg/certificate")
public class UserCertInfoController {

    @Autowired
    private IUserCertInfoService userCertInfoService;

    @PostMapping("/status")
    @ApiOperation("状态")
    public ResponseApi certInfoStatus(@RequestBody JSONObject text) {
        try {

            return userCertInfoService.certInfoStatus(text);
        } catch (Exception e) {
            return new ResponseApi(BgStatusEnum.RESPONSE_ERROR, e.getMessage());
        }
    }

    @PostMapping("/operate")
    @ApiOperation("操作")
    public ResponseApi operateCertInfo(@RequestBody JSONObject text) {
        try {

            return userCertInfoService.operateCertInfo(text);
        } catch (Exception e) {
            return new ResponseApi(BgStatusEnum.RESPONSE_ERROR, e.getMessage());
        }
    }

}