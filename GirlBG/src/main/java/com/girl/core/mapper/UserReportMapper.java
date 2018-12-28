package com.girl.core.mapper;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.girl.Common.model.OrignReport;
import com.girl.Common.model.ReportDetail;
import com.girl.core.entity.UserReport;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wangpei
 * @since 2018-12-25
 */
public interface UserReportMapper extends BaseMapper<UserReport> {

    List<OrignReport> getReportInfo(@Param(value = "status") Integer status,
                                    @Param(value = "search") String search);

    List<ReportDetail> getReportDetail(@Param(value = "uid") Integer uid);

}
