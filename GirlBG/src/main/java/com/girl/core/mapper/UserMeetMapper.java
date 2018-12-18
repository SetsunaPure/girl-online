package com.girl.core.mapper;

import com.girl.Common.model.MeetInfo;
import com.girl.core.entity.UserMeet;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 用户钱包表 Mapper 接口
 * </p>
 *
 * @author wangpei
 * @since 2018-12-13
 */
public interface UserMeetMapper extends BaseMapper<UserMeet> {

    List<MeetInfo> getMeetInfo(Integer status);

}
