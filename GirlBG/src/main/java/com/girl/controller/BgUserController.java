package com.girl.controller;

import com.alibaba.fastjson.JSONObject;
import com.girl.Common.enums.BgStatusEnum;
import com.girl.Common.model.ResponseApi;
import com.girl.Common.model.ResponseLogin;
import com.girl.Exception.GirlException;
import com.girl.service.impl.BgUserServiceImpl;
import com.girl.Common.utils.StringUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wangpei
 * @since 2018-12-13
 */
@RestController
@RequestMapping("/bg")
public class BgUserController {

    @Autowired
    BgUserServiceImpl bgUserService;

    @PostMapping("/userLogin")
    @ResponseBody
    @ApiOperation("登录")
    public ResponseApi userLogin(@RequestBody JSONObject text) {
        try {
            return bgUserService.login(text);
        } catch (Exception e) {
            return new ResponseApi(400, e.getMessage(), "");
        }

    }

    @PostMapping("/customerservice/add")
    @ApiOperation("新增用户")
    public ResponseApi addUser(@RequestBody JSONObject text) {
        try {
            return bgUserService.addUser(text);
        } catch (Exception e) {
            return new ResponseApi(BgStatusEnum.RESPONSE_ERROR, e.getMessage());
        }
    }

    @PostMapping("/customerservice/delete")
    @ApiOperation("删除用户")
    public ResponseApi delUser(@RequestBody JSONObject text) {
        try {
            return bgUserService.delUser(text);
        } catch (Exception e) {
            return new ResponseApi(BgStatusEnum.RESPONSE_ERROR, e.getMessage());
        }
    }

    @PostMapping("/customerservice/list")
    @ApiOperation("客服列表")
    public ResponseApi getUsers(@RequestBody JSONObject text) {
        try {
            return bgUserService.getUsers(text);
        } catch (Exception e) {
            return new ResponseApi(BgStatusEnum.RESPONSE_ERROR, e.getMessage());
        }
    }

    @PostMapping("/customerservice/modifypassword")
    @ApiOperation("客服列表")
    public ResponseApi modifyPassword(@RequestBody JSONObject text) {
        try {
            return bgUserService.modifyPassword(text);
        } catch (Exception e) {
            return new ResponseApi(BgStatusEnum.RESPONSE_ERROR, e.getMessage());
        }
    }

}