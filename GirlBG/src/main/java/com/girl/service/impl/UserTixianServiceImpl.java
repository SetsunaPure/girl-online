package com.girl.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.girl.Common.model.DepositInfo;
import com.girl.Common.model.ResponseApi;
import com.girl.core.entity.UserTixian;
import com.girl.core.mapper.UserTixianMapper;
import com.girl.service.IUserTixianService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Override
    public ResponseApi getDrawingStatus(String token, String status){
        try {
            List<DepositInfo> lstDepositInfo = userTixianMapper.getDrawingStatus(Integer.parseInt(status));
            return new ResponseApi(RESPONSE_OK, lstDepositInfo);
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseApi(RESPONSE_ERROR, e.getMessage());
        }
    }

    @Override
    @Transactional
    public ResponseApi operateDrawing(String token, String id, String status){
        try {
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