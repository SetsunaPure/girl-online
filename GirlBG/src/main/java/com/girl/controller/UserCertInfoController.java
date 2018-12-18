package com.girl.controller;


import com.girl.Common.model.ResponseApi;
import com.girl.Common.utils.StringUtils;
import com.girl.service.IUserCertInfoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
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
    public ResponseApi certInfoStatus(@RequestBody String text){

        String status = StringUtils.get("status", text);
        String token = StringUtils.get("token", text);

        return userCertInfoService.certInfoStatus(token, status);
    }

    @PostMapping("/operate")
    @ApiOperation("操作")
    public ResponseApi operateCertInfo(@RequestBody String text){

        String id = StringUtils.get("id", text);
        String token = StringUtils.get("token", text);
        String status = StringUtils.get("status", text);

        return userCertInfoService.operateCertInfo(token, id, status);
    }

}