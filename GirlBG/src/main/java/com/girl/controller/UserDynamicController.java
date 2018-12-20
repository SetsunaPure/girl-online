package com.girl.controller;


import com.alibaba.fastjson.JSONObject;
import com.girl.Common.enums.BgStatusEnum;
import com.girl.Common.model.ResponseApi;
import com.girl.Common.utils.StringUtils;
import com.girl.Exception.GirlException;
import com.girl.service.IUserDynamicService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户钱包表 前端控制器
 * </p>
 *
 * @author wangpei
 * @since 2018-12-13
 */
@RestController
@RequestMapping("/bg/dynamiccheck")
public class UserDynamicController {

    @Autowired
    IUserDynamicService userDynamicService;

    @PostMapping("/status")
    @ApiOperation("状态")
    public ResponseApi dynamicStatus(@RequestBody JSONObject text) {
        try {
            String status = text.getString("status");
            String token = text.getString("token");
//        String token = (String) RequestContextHolder.currentRequestAttributes().getAttribute("username", 0);

            if(!StringUtils.areNotEmpty(status, token)){
                return new ResponseApi(BgStatusEnum.RESPONSE_EMPTY, "状态码和认证不能为空");
            }
            return userDynamicService.getDynamicData(token, status);
        } catch (Exception e) {
            return new ResponseApi(BgStatusEnum.RESPONSE_ERROR, e.getMessage());
        }
    }

    @PostMapping("/operate")
    @ApiOperation("操作")
    public ResponseApi dynamicOperation(@RequestBody JSONObject text) {
        try {
            String status = text.getString("operate_status");
            String token = text.getString("token");
            String id = text.getString("id");
            if(!StringUtils.areNotEmpty(id, status, token)){
                return new ResponseApi(BgStatusEnum.RESPONSE_EMPTY, "流水id、状态码和认证不能为空");
            }
            return userDynamicService.operateDynamic(token, id, status);
        } catch (Exception e) {
            return new ResponseApi(BgStatusEnum.RESPONSE_ERROR, e.getMessage());
        }
    }


}

