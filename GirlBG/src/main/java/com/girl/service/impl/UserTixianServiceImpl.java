package com.girl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.girl.Common.enums.BgStatusEnum;
import com.girl.Common.model.DepositInfo;
import com.girl.Common.model.ResponseApi;
import com.girl.Common.utils.RedisUtils;
import com.girl.Common.utils.StringUtils;
import com.girl.core.entity.UserTixian;
import com.girl.core.mapper.UserTixianMapper;
import com.girl.service.IUserTixianService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.girl.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.girl.Common.constants.Constant.DEFAULT_CURRENT;
import static com.girl.Common.constants.Constant.DEFAULT_SIZE;
import static com.girl.Common.enums.BgStatusEnum.RESPONSE_ERROR;
import static com.girl.Common.enums.BgStatusEnum.RESPONSE_OK;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wangpei
 * @since 2018-12-17
 */
@Service
public class UserTixianServiceImpl extends ServiceImpl<UserTixianMapper, UserTixian> implements IUserTixianService {

    @Autowired
    private UserTixianMapper userTixianMapper;

    @Autowired
    private RedisService redisService;

    @Override
    public ResponseApi getDrawingStatus(JSONObject text){
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

            List<DepositInfo> lstDepositInfo = userTixianMapper.getDrawingStatus(page, Integer.parseInt(status));
            return new ResponseApi(RESPONSE_OK, lstDepositInfo);
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseApi(RESPONSE_ERROR, e.getMessage());
        }
    }

    @Override
    @Transactional
    public ResponseApi operateDrawing(JSONObject text){
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

            //更改提现状态
            Integer tixianStatus = 1;
            if (status.equals("3") ) {
                //提现条件不足
                UserTixian userTixian = new UserTixian();
                userTixian.setStatus(Integer.parseInt(status));
                tixianStatus = userTixianMapper.update(userTixian,
                        new EntityWrapper<UserTixian>().where("id={0}", Integer.parseInt(id)));
            }else if (status.equals("2")){
                //更改个人币数
                tixianStatus = userTixianMapper.updateDrawingStatus(Integer.parseInt(id));
            }

            //todo:激光推送
            return new ResponseApi(RESPONSE_OK, tixianStatus);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseApi(RESPONSE_ERROR, e.getMessage());
        }
    }

}