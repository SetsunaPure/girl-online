package com.girl.core.mapper;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.girl.Common.model.DepositInfo;
import com.girl.core.entity.UserTixian;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

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

    List<DepositInfo> getDrawingStatus(Pagination page,
                                       @Param(value = "status") Integer status,
                                       @Param(value = "search") String search);

    Integer updateDrawingStatus(@Param(value = "id")Integer id);

    Long getTixianCount(@Param(value = "status") Integer status,
                         @Param(value = "search") String search);

}
