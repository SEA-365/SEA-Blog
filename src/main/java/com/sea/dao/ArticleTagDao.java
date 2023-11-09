package com.sea.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sea.entity.ArticleTag;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文章标签关联表
 * @author: sea
 * @date: 2023/11/9 19:47
 */

@Mapper
public interface ArticleTagDao extends BaseMapper<ArticleTag> {

}
