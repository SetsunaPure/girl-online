package com.girl.controller;


import com.alibaba.fastjson.JSONObject;
import com.girl.Common.enums.BgStatusEnum;
import com.girl.Common.model.ResponseApi;
import com.girl.Common.utils.StringUtils;
import io.swagger.annotations.ApiOperation;
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

    @PostMapping("/status")
    @ApiOperation("用户状态")
    public ResponseApi getUserManageStatus(@RequestBody JSONObject text) {
        try {
            String status = text.getString("status");
            String token = text.getString("token");
            if (!StringUtils.areNotEmpty(status, token)) {
                return new ResponseApi(BgStatusEnum.RESPONSE_EMPTY, "状态码和认证不能为空");
            }
            return null;
        } catch (Exception e) {
            return new ResponseApi(BgStatusEnum.RESPONSE_ERROR, e.getMessage());
        }

    }

    @PostMapping("/operate")
    @ApiOperation("用户审核操作")
    public ResponseApi userManageOperate(@RequestBody JSONObject text) {
        try {
            String status = text.getString("status");
            String token = text.getString("token");
            String id = text.getString("id");
            if(!StringUtils.areNotEmpty(id, status, token)){
                return new ResponseApi(BgStatusEnum.RESPONSE_EMPTY, "流水id、状态码和认证不能为空");
            }
            return null;
        } catch (Exception e) {
            return new ResponseApi(BgStatusEnum.RESPONSE_ERROR, e.getMessage());
        }
    }


}

