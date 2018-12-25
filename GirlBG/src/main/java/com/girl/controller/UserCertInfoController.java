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
            String status = text.getString("status");
            String token = text.getString("token");

            if(!StringUtils.areNotEmpty(status, token)){
                return new ResponseApi(BgStatusEnum.RESPONSE_EMPTY, "状态码和认证不能为空");
            }

            return userCertInfoService.certInfoStatus(token, status);
        } catch (Exception e) {
            return new ResponseApi(BgStatusEnum.RESPONSE_ERROR, e.getMessage());
        }
    }

    @PostMapping("/operate")
    @ApiOperation("操作")
    public ResponseApi operateCertInfo(@RequestBody JSONObject text) {
        try {
            String id = text.getString("id");
            String token = text.getString("token");
            String status = text.getString("status");
            if(!StringUtils.areNotEmpty(id, status, token)){
                return new ResponseApi(BgStatusEnum.RESPONSE_EMPTY, "流水id、状态码和认证不能为空");
            }
            return userCertInfoService.operateCertInfo(token, id, status);
        } catch (Exception e) {
            return new ResponseApi(BgStatusEnum.RESPONSE_ERROR, e.getMessage());
        }
    }

}