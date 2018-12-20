package com.girl.controller;


import com.alibaba.fastjson.JSONObject;
import com.girl.Common.enums.BgStatusEnum;
import com.girl.Common.model.ResponseApi;
import com.girl.Common.utils.StringUtils;
import com.girl.service.IUserTixianService;
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
 * @since 2018-12-13
 */
@RestController
@RequestMapping("/bg/drawings")
public class UserTixianController {

    @Autowired
    IUserTixianService userTixianService;

    @PostMapping("/status")
    @ApiOperation("提现状态")
    public ResponseApi drawingStatus(@RequestBody JSONObject text) {
        try {
            String status = text.getString("status");
            String token = text.getString("token");
            if(!StringUtils.areNotEmpty(status, token)){
                return new ResponseApi(BgStatusEnum.RESPONSE_EMPTY, "状态码和认证不能为空");
            }
            return userTixianService.getDrawingStatus(token, status);
        } catch (Exception e) {
            return new ResponseApi(BgStatusEnum.RESPONSE_ERROR, e.getMessage());
        }
    }

    @PostMapping("/operate")
    @ApiOperation("提现操作")
    public ResponseApi drawingOperate(@RequestBody JSONObject text) {
        try {
            String status = text.getString("status");
            String token = text.getString("token");
            String id = text.getString("id");
            if(!StringUtils.areNotEmpty(id, status, token)){
                return new ResponseApi(BgStatusEnum.RESPONSE_EMPTY, "流水id、状态码和认证不能为空");
            }
            return userTixianService.operateDrawing(token, id, status);
        } catch (Exception e) {
            return new ResponseApi(BgStatusEnum.RESPONSE_ERROR, e.getMessage());
        }
    }
}

