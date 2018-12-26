package com.girl.core.mapper;

import com.girl.core.entity.BgUser;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wangpei
 * @since 2018-12-13
 */
public interface BgUserMapper extends BaseMapper<BgUser> {

    String getTokenByName(@Param(value = "name") String name);

}