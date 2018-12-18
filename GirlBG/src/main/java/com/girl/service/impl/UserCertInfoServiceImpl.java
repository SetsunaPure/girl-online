package com.girl.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.girl.Common.BgStatusEnum;
import com.girl.Common.model.CertInfo;
import com.girl.Common.model.ResponseApi;
import com.girl.core.entity.UserCertInfo;
import com.girl.core.mapper.UserCertInfoMapper;
import com.girl.service.IUserCertInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wangpei
 * @since 2018-12-17
 */
@Service
public class UserCertInfoServiceImpl extends ServiceImpl<UserCertInfoMapper, UserCertInfo> implements IUserCertInfoService {

    @Autowired
    UserCertInfoMapper userCertInfoMapper;

    @Override
    public ResponseApi certInfoStatus(String token, String status){

        try {
            List<CertInfo> lstCertInfo = userCertInfoMapper.getCertInfo(Integer.parseInt(status));
            return new ResponseApi(BgStatusEnum.RESPONSE_OK, lstCertInfo);
        }catch (Exception e)
        {
            e.getMessage();
            return new ResponseApi(BgStatusEnum.RESPONSE_ERROR, "读取认证信息失败");
        }

    }

    @Override
    @Transactional
    public ResponseApi operateCertInfo(String token, String id, String status){

        try{
            UserCertInfo uci = new UserCertInfo();
            uci.setStatus(Integer.parseInt(status));
            Integer res = userCertInfoMapper.update(uci, new EntityWrapper<UserCertInfo>().eq("id={0}", id));
            return new ResponseApi(BgStatusEnum.RESPONSE_OK, res);
        }catch (Exception e){
            e.getMessage();
            return new ResponseApi(BgStatusEnum.RESPONSE_ERROR, "更新认证信息失败");
        }
    }

}
