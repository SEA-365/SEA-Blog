package com.sea.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sea.entity.Article;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文章表数据访问层接口
 * @author: sea
 * @date: 2023/10/20 18:32
 */
@Mapper
public interface ArticleDao extends BaseMapper<Article> {
}
