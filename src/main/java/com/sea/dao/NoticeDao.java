package com.sea.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sea.entity.Notice;
import org.apache.ibatis.annotations.Mapper;

/**
 * 通知公告表数据访问层接口
 * @author: sea
 * @date: 2023/12/27 18:31
 */
@Mapper
public interface NoticeDao extends BaseMapper<Notice> {

}
