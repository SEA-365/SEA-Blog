package com.sea.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sea.entity.Tag;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文章标签表数据访问层接口
 * @author: sea
 * @date: 2023/10/19 11:08
 */
@Mapper
public interface TagDao extends BaseMapper<Tag> {

}
