package com.girl.controller;

import com.alibaba.fastjson.JSONObject;
import com.girl.service.impl.BgUserServiceImpl;
import com.girl.Common.utils.StringUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;


/**
 * <p>
 *  前端控制器
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
    public JSONObject userLogin(@RequestBody String text) {
        String username = StringUtils.get("username", text);
        String pwd = StringUtils.get("password", text);
        return bgUserService.login(username, pwd);

    }

    public JSONObject addUser(@RequestBody String text) {
        String token = StringUtils.get("token", text);
        return bgUserService.addUser(token);
    }
}