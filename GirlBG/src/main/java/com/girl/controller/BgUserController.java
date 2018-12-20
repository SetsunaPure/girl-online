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
    public ResponseLogin userLogin(@RequestBody JSONObject text) {
        try {
            String username = text.getString("username");
            String pwd = text.getString("password");
            if(!StringUtils.areNotEmpty(username, pwd)){
                return new ResponseLogin(401, "用户名密码不能为空",null,null);
            }
            return bgUserService.login(username, pwd);
        } catch (Exception e) {
            return new ResponseLogin(400, e.getMessage(), "", "");
        }

    }

    @PostMapping("/customerservice/add")
    @ApiOperation("新增用户")
    public ResponseApi addUser(@RequestBody JSONObject text) {
        try {
            String token = text.getString("token");
            String name = text.getString("name");
            String pwd = text.getString("pwd");
            if(!StringUtils.areNotEmpty(name, pwd,token)){
                return new ResponseApi(BgStatusEnum.RESPONSE_EMPTY, "用户名、参数、认证不能为空");
            }
            return bgUserService.addUser(token, name, pwd);
        } catch (Exception e) {
            return new ResponseApi(BgStatusEnum.RESPONSE_ERROR, e.getMessage());
        }
    }

    @PostMapping("/customerservice/delete")
    @ApiOperation("删除用户")
    public ResponseApi delUser(@RequestBody JSONObject text) {
        try {
            String token = text.getString("token");
            String id = text.getString("id");
            if(!StringUtils.areNotEmpty(token, id)){
                return new ResponseApi(BgStatusEnum.RESPONSE_EMPTY, "流水id和认证不能为空");
            }
            return bgUserService.delUser(token, id);
        } catch (Exception e) {
            return new ResponseApi(BgStatusEnum.RESPONSE_ERROR, e.getMessage());
        }
    }

}