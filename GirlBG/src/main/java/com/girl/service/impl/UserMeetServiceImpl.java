package com.girl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.girl.Common.enums.BgStatusEnum;
import com.girl.Common.model.MeetInfo;
import com.girl.Common.model.ResponseApi;
import com.girl.Common.utils.RedisUtils;
import com.girl.Common.utils.StringUtils;
import com.girl.core.entity.UserMeet;
import com.girl.core.mapper.UserMeetMapper;
import com.girl.service.IUserMeetService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.girl.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.girl.Common.constants.Constant.DEFAULT_CURRENT;
import static com.girl.Common.constants.Constant.DEFAULT_SIZE;

/**
 * <p>
 * 用户钱包表 服务实现类
 * </p>
 *
 * @author wangpei
 * @since 2018-12-17
 */
@Service
public class UserMeetServiceImpl extends ServiceImpl<UserMeetMapper, UserMeet> implements IUserMeetService {

    @Autowired
    private UserMeetMapper userMeetMapper;

    @Autowired
    private RedisService redisService;

    @Override
    public ResponseApi getMeetInfo(JSONObject text){

        try {
            String status = text.getString("status");
            String token = text.getString("token");
            String current = text.getString("current");
            String size = text.getString("size");

            if(!StringUtils.areNotEmpty(status, token)){
                return new ResponseApi(BgStatusEnum.RESPONSE_EMPTY, "状态码和认证不能为空");
            }
            if (RedisUtils.isTokenNull(redisService,token)){
                return new ResponseApi(BgStatusEnum.RESPONSE_NOT_LOGIN, null);
            }


            int lnCurrent = current == null ? DEFAULT_CURRENT : Integer.parseInt(current);
            int lnSize = size == null ? DEFAULT_SIZE : Integer.parseInt(size);
            Page page = new Page(lnCurrent, lnSize);

            List<MeetInfo> lstMeetInfo = userMeetMapper.getMeetInfo(page, Integer.parseInt(status));
            return new ResponseApi(BgStatusEnum.RESPONSE_OK, lstMeetInfo);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseApi(BgStatusEnum.RESPONSE_ERROR, "读取约会信息失败");
        }

    }

    @Override
    @Transactional
    public ResponseApi operateMeet(JSONObject text){
        try {
            String status = text.getString("status");
            String token = text.getString("token");
            String id = text.getString("id");
            if(!StringUtils.areNotEmpty(id, status, token)){
                return new ResponseApi(BgStatusEnum.RESPONSE_EMPTY, "流水id、状态码和认证不能为空");
            }
            if (RedisUtils.isTokenNull(redisService,token)){
                return new ResponseApi(BgStatusEnum.RESPONSE_NOT_LOGIN, null);
            }

            UserMeet userMeet = new UserMeet();
            userMeet.setStatus(Integer.parseInt(status));
            Integer res = userMeetMapper.update(userMeet, new EntityWrapper<UserMeet>().eq("id={0}", id));
            return new ResponseApi(BgStatusEnum.RESPONSE_OK, res);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseApi(BgStatusEnum.RESPONSE_ERROR, "更新约会审核状态失败");
        }
    }
}
