package com.sea.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sea.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户表数据访问接口
 * @author: sea
 * @date: 2023/10/11 15:44
 */
@Mapper
public interface UserDao extends BaseMapper<User> {

}
