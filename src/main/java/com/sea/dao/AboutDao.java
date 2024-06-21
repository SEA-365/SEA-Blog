package com.sea.dao;

import com.sea.entity.About;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface AboutDao extends BaseMapper<About> {

}