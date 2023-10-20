package com.sea.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sea.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文章评论表数据访问层接口
 * @author: sea
 * @date: 2023/10/19 14:43
 */
@Mapper
public interface CommentDao extends BaseMapper<Comment> {

}
