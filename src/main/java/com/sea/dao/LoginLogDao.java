package com.sea.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sea.entity.LoginLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 登录日志记录表数据访问层接口
 * @author: sea
 * @date: 2023/12/20 10:49
 */
@Mapper
public interface LoginLogDao extends BaseMapper<LoginLog> {

}
