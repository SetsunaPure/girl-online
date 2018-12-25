package com.girl.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.girl.Common.enums.BgStatusEnum;
import com.girl.Common.model.MeetInfo;
import com.girl.Common.model.ResponseApi;
import com.girl.Common.utils.RedisUtils;
import com.girl.core.entity.UserMeet;
import com.girl.core.mapper.UserMeetMapper;
import com.girl.service.IUserMeetService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.girl.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public ResponseApi getMeetInfo(String token, String status){

        try {

            if (RedisUtils.isTokenNull(redisService,token)){
                return new ResponseApi(BgStatusEnum.RESPONSE_NOT_LOGIN, null);
            }

            List<MeetInfo> lstMeetInfo = userMeetMapper.getMeetInfo(Integer.parseInt(status));
            return new ResponseApi(BgStatusEnum.RESPONSE_OK, lstMeetInfo);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseApi(BgStatusEnum.RESPONSE_ERROR, "读取约会信息失败");
        }

    }

    @Override
    @Transactional
    public ResponseApi operateMeet(String token, String id, String status){
        try {

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
