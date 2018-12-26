package com.girl.core.mapper;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.girl.Common.model.DepositInfo;
import com.girl.core.entity.UserTixian;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wangpei
 * @since 2018-12-13
 */
public interface UserTixianMapper extends BaseMapper<UserTixian> {

    List<DepositInfo> getDrawingStatus(Pagination page, Integer status);

    Integer updateDrawingStatus(Integer id);

}
