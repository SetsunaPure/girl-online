package com.girl.core.mapper;

import com.girl.Common.model.CertInfo;
import com.girl.core.entity.UserCertInfo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wangpei
 * @since 2018-12-17
 */
public interface UserCertInfoMapper extends BaseMapper<UserCertInfo> {

    List<CertInfo> getCertInfo(Pagination page,
                               @Param(value = "status") Integer status,
                               @Param(value = "search") String search);

    long getCertCount(@Param(value = "status") Integer status,
                         @Param(value = "search") String search);

}
