package com.girl.Common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.girl.Common.model.UserIcon;
import com.girl.core.entity.UserMsg;
import com.girl.core.mapper.UserMsgMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UserNotice {

    @Autowired
    UserMsgMapper userMsgMapper;

    Logger logger = LoggerFactory.getLogger(getClass());

    public void sendMessage(UserMsg userMsg, UserIcon userIcon, Map<String, String> extend, String title) {
        userMsg.setType(30);

        try {
            if (userMsg.getCreateTime() == null) {
                userMsg.setCreateTime(new Date());
            }
            Map<String, String> param = new HashMap<>();

            userMsgMapper.insert(userMsg);

//            UserMsg userMsg1 = new UserMsg();
//            userMsg1.setCreateTime(userMsg.getCreateTime());
//            userMsg1.setUid(userMsg.getUid());
            logger.info("查询推送消息前：", JSON.toJSON(userMsg));
            UserMsg userMsg2 = userMsgMapper.selectOne(userMsg);
            logger.info("查询推送消息后：", JSON.toJSON(userMsg2));
            param.put("id", userMsg2.getId().toString());
            param.put("userIcon", JSONObject.toJSONString(userIcon));
            param.put("extend", JSONObject.toJSONString(extend));
            param.put("time", String.valueOf(userMsg2.getCreateTime().getTime()));
            param.put("type", userMsg2.getType().toString());
            param.put("subType", userMsg2.getSubType().toString());

            Jiguang.alert(userMsg.getMsg(), title, param, userMsg2.getUid().toString());

            userMsg2.setParam(JSON.toJSONString(param));
        } catch (Exception e) {
            logger.error("推送失败", e);
        }
    }
}
