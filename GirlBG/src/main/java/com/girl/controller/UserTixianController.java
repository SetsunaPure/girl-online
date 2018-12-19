package com.girl.controller;


import com.girl.Common.enums.BgStatusEnum;
import com.girl.Common.model.ResponseApi;
import com.girl.Common.utils.StringUtils;
import com.girl.Exception.GirlException;
import com.girl.service.IUserTixianService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wangpei
 * @since 2018-12-13
 */
@Controller
@RequestMapping("/bg/drawings/")
public class UserTixianController {

    @Autowired
    IUserTixianService userTixianService;

    @PostMapping("/status")
    @ApiOperation("提现状态")
    public ResponseApi drawingStatus(@RequestBody String text) {
        try {
            String status = StringUtils.get("status", text);
            String token = StringUtils.get("token", text);

            return userTixianService.getDrawingStatus(token, status);
        } catch (GirlException e) {
            return new ResponseApi(BgStatusEnum.RESPONSE_ERROR, e.getMessage());
        }
    }

    @PostMapping("/operate")
    @ApiOperation("提现操作")
    public ResponseApi drawingOperate(@RequestBody String text) {
        try {
            String status = StringUtils.get("status", text);
            String token = StringUtils.get("token", text);
            String id = StringUtils.get("id", text);

            return userTixianService.operateDrawing(token, id, status);
        } catch (GirlException e) {
            return new ResponseApi(BgStatusEnum.RESPONSE_ERROR, e.getMessage());
        }
    }
}

