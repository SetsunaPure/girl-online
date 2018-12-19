package com.girl.controller;


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
    public ResponseApi dynamicStatus(@RequestBody String text) {
        try {
            String status = StringUtils.get("status", text);
            String token = StringUtils.get("token", text);
//        String token = (String) RequestContextHolder.currentRequestAttributes().getAttribute("username", 0);
            return userDynamicService.getDynamicData(token, status);
        } catch (GirlException e) {
            return new ResponseApi(BgStatusEnum.RESPONSE_ERROR, e.getMessage());
        }
    }

    @PostMapping("/operate")
    @ApiOperation("操作")
    public ResponseApi dynamicOperation(@RequestBody String text) {
        try {
            String status = StringUtils.get("operate_status", text);
            String token = StringUtils.get("token", text);
            String id = StringUtils.get("id", text);
            return userDynamicService.operateDynamic(token, id, status);
        } catch (GirlException e) {
            return new ResponseApi(BgStatusEnum.RESPONSE_ERROR, e.getMessage());
        }
    }


}

