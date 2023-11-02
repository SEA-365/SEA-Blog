package com.sea.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sea.entity.Article;
import com.sea.model.vo.ConditionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文章表数据访问层接口
 * @author: sea
 * @date: 2023/10/20 18:32
 */
@Mapper
public interface ArticleDao extends BaseMapper<Article> {
    /**
     * 根据指定条件查询文章列表
     * @param conditionVO 查询条件
     * @return 指定文章列表
     */
    List<Article> getArticlePage(@Param("conditionVO") ConditionVO conditionVO);

}
