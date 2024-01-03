package com.sea.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sea.entity.Category;
import com.sea.model.vo.CategoryVO;
import com.sea.model.vo.ConditionVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 文章分类表数据访问层接口
 * @author: sea
 * @date: 2023/10/18 15:15
 */
@Mapper
public interface CategoryDao extends BaseMapper<Category> {

}
