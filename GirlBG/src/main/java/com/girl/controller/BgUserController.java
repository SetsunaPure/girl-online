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
    public ResponseLogin userLogin(@RequestBody String text) {
        try {
            String username = StringUtils.get("username", text);
            String pwd = StringUtils.get("password", text);
            return bgUserService.login(username, pwd);
        } catch (GirlException e) {
            return new ResponseLogin(400, e.getMessage(), "", "");
        }

    }

    @PostMapping("/customerservice/add")
    @ApiOperation("新增用户")
    public ResponseApi addUser(@RequestBody String text) {
        try {
            String token = StringUtils.get("token", text);
            String name = StringUtils.get("name", text);
            String pwd = StringUtils.get("pwd", text);
            return bgUserService.addUser(token, name, pwd);
        } catch (GirlException e) {
            return new ResponseApi(BgStatusEnum.RESPONSE_ERROR, e.getMessage());
        }
    }

    @PostMapping("/customerservice/delete")
    @ApiOperation("删除用户")
    public ResponseApi delUser(@RequestBody String text) {
        try {
            String token = StringUtils.get("token", text);
            String id = StringUtils.get("id", text);
            return bgUserService.delUser(token, id);
        } catch (GirlException e) {
            return new ResponseApi(BgStatusEnum.RESPONSE_ERROR, e.getMessage());
        }
    }

}