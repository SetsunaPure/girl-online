package com.girl.core.mapper;

import com.girl.Common.model.DynamicInfo;
import com.girl.core.entity.UserDynamic;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * <p>
 * 用户钱包表 Mapper 接口
 * </p>
 *
 * @author wangpei
 * @since 2018-12-13
 */
public interface UserDynamicMapper extends BaseMapper<UserDynamic> {

    List<DynamicInfo> getDynamicInfo(@Param(value = "status") Integer status);

}
