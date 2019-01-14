package com.girl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.girl.Common.enums.BgStatusEnum;
import com.girl.Common.model.DepositInfo;
import com.girl.Common.model.ResponseApi;
import com.girl.Common.model.ResponseData;
import com.girl.Common.model.UserIcon;
import com.girl.Common.utils.RedisUtils;
import com.girl.Common.utils.StringUtils;
import com.girl.core.entity.UserInfo;
import com.girl.core.entity.UserMsg;
import com.girl.core.entity.UserTixian;
import com.girl.core.mapper.UserInfoMapper;
import com.girl.core.mapper.UserTixianMapper;
import com.girl.service.IUserTixianService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.girl.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.girl.Common.constants.Constant.DEFAULT_CURRENT;
import static com.girl.Common.constants.Constant.DEFAULT_SIZE;
import static com.girl.Common.enums.BgStatusEnum.RESPONSE_ERROR;
import static com.girl.Common.enums.BgStatusEnum.RESPONSE_OK;

/**
 * <p>
 * 服务实现类
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

    @Autowired
    private UserInfoMapper userInfoMapper;

    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public ResponseApi getDrawingStatus(JSONObject text) {
        try {
            String status = text.getString("status");
            String token = text.getString("token");
            String current = text.getString("current");
            String size = text.getString("size");
            String search = text.getString("search");

            if (!StringUtils.areNotEmpty(status)) {
                return new ResponseApi(BgStatusEnum.RESPONSE_EMPTY, "状态码不能为空");
            }
            if (RedisUtils.isTokenNull(redisService, token)) {
                return new ResponseApi(BgStatusEnum.RESPONSE_NOT_LOGIN, null);
            }

            int lnCurrent = current == null ? DEFAULT_CURRENT : Integer.parseInt(current);
            int lnSize = size == null ? DEFAULT_SIZE : Integer.parseInt(size);
            int lnStatus = Integer.parseInt(status);
            Page page = new Page(lnCurrent, lnSize);

            List<DepositInfo> lstDepositInfo = userTixianMapper.getDrawingStatus(page, lnStatus, search);

            long count = userTixianMapper.getTixianCount(lnStatus, search);

            ResponseData info = new ResponseData(count, lstDepositInfo);
            return new ResponseApi(RESPONSE_OK, info);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseApi(RESPONSE_ERROR, e.getMessage());
        }
    }

    @Override
    @Transactional
    public ResponseApi operateDrawing(JSONObject text) {
        try {
            String status = text.getString("status");
            String token = text.getString("token");
            String id = text.getString("id");
            if (!StringUtils.areNotEmpty(id, status, token)) {
                return new ResponseApi(BgStatusEnum.RESPONSE_EMPTY, "流水id、状态码和认证不能为空");
            }

            if (RedisUtils.isTokenNull(redisService, token)) {
                return new ResponseApi(BgStatusEnum.RESPONSE_NOT_LOGIN, null);
            }

            //更改提现状态
            Integer tixianStatus = 1;
            if (status.equals("1")) {
                //更改个人币数
                tixianStatus = userTixianMapper.updateDrawingStatus(Integer.parseInt(id));
            }

            UserTixian userTixian = new UserTixian();
            userTixian.setStatus(Integer.parseInt(status));
            tixianStatus = userTixianMapper.update(userTixian,
                    new EntityWrapper<UserTixian>().where("id={0}", Integer.parseInt(id)));

            //激光推送提现消息
            pushTixianMessages(id, tixianStatus, userTixian);

            return new ResponseApi(RESPONSE_OK, tixianStatus);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseApi(RESPONSE_ERROR, e.getMessage());
        }
    }

    private void pushTixianMessages(String id, Integer lnStatus, UserTixian uci) {
        UserTixian userTixian = userTixianMapper.selectOne(uci);

        UserMsg userMsg = new UserMsg();
        userMsg.setSubType(30);
        userMsg.setBindId(Long.valueOf(id));
        userMsg.setUid(userTixian.getUid());
        userMsg.setCreateTime(new Date());

        //推送消息给用户
        if (lnStatus == 1) {
            userMsg.setMsg("恭喜！提现成功");
        } else if (lnStatus == 2) {
            userMsg.setMsg("对不起！提现失败");
        }

        logger.info(userMsg.getMsg());

        UserInfo userInformation = new UserInfo();
        userInformation.setId(userTixian.getUid());

        UserInfo userInfo = userInfoMapper.selectOne(userInformation);

        UserIcon userIcon = new UserIcon();
        userIcon.setNickName(userInfo.getNickName());
        userIcon.setUid(userInfo.getId());
        userIcon.setAvatar(userInfo.getAvatar());

        Map<String, String> extend = new HashMap();
        extend.put("tixianId", userInfo.getId().toString());
    }

}