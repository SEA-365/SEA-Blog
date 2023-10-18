package com.sea.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sea.entity.Category;
import org.apache.ibatis.annotations.Mapper;

/**
 * 分类表数据访问层
 * @author: sea
 * @date: 2023/10/18 15:15
 */
@Mapper
public interface CategoryDao extends BaseMapper<Category> {

}
