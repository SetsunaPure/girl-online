package com.girl.core.mapper;

import com.girl.core.entity.PubApiToken;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wangpei
 * @since 2018-12-28
 */
public interface PubApiTokenMapper extends BaseMapper<PubApiToken> {

    String getTokenByReportId(@Param(value = "report_id")Integer reportId);
}
