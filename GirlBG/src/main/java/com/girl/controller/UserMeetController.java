package com.girl.controller;


import com.alibaba.fastjson.JSONObject;
import com.girl.Common.enums.BgStatusEnum;
import com.girl.Common.model.ResponseApi;
import com.girl.Common.utils.StringUtils;
import com.girl.Exception.GirlException;
import com.girl.service.IUserMeetService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户钱包表 前端控制器
 * </p>
 *
 * @author wangpei
 * @since 2018-12-13
 */
@RestController
@RequestMapping("bg/appointment")
public class UserMeetController {

    @Autowired
    IUserMeetService userMeetService;

    @PostMapping("/status")
    @ApiOperation("约会审核状态")
    public ResponseApi getMeetStatus(@RequestBody JSONObject text) {

        try {
            String status = text.getString("status");
            String token = text.getString("token");
            if(!StringUtils.areNotEmpty(status, token)){
                return new ResponseApi(BgStatusEnum.RESPONSE_EMPTY, "状态码和认证不能为空");
            }
            return userMeetService.getMeetInfo(token, status);
        } catch (Exception e) {
            return new ResponseApi(BgStatusEnum.RESPONSE_ERROR, e.getMessage());
        }
    }

    @PostMapping("/operate")
    @ApiOperation("约会状态操作")
    public ResponseApi operateMeet(@RequestBody JSONObject text) {
        try {
            String status = text.getString("status");
            String token = text.getString("token");
            String id = text.getString("id");
            if(!StringUtils.areNotEmpty(id, status, token)){
                return new ResponseApi(BgStatusEnum.RESPONSE_EMPTY, "流水id、状态码和认证不能为空");
            }
            return userMeetService.operateMeet(token, id, status);
        } catch (Exception e) {
            return new ResponseApi(BgStatusEnum.RESPONSE_ERROR, e.getMessage());
        }
    }
}

