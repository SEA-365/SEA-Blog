package com.sea.service;

import com.model.dto.PageRequestDTO;
import com.model.vo.CommentVO;
import com.sea.entity.Comment;

import java.util.List;

/**
 * 评论表业务层接口
 * @author: sea
 * @date: 2023/10/19 17:45
 */
public interface CommentService {
    /**
     * 获取评论信息列表
     * @return 指定页的评论信息
     */
    public List<Comment> getCommentList(PageRequestDTO pageRequestDTO);

    /**
     * 根据id获取指定评论信息
     * @param commentId 评论id
     * @return 指定评论信息
     */
    Comment getCommentById(Long commentId);

    /**
     * 添加评论
     * @param commentVO 待添加的评论信息
     */
    boolean addComment(CommentVO commentVO);

//    /**
//     * 修改评论 【删除接口！【评论一般没有修改操作】
//     * @param commentVO 新的评论信息
//     */
//    boolean updateComment(CommentVO commentVO);

    /**
     * 删除评论-逻辑删除
     * @param commentId 待删除评论id
     */
    boolean deleteCommentById(Long commentId);

//    /**
//     * 统计当前文章的评论数【似乎不应该在这里实现。。】
//     * @param articleId 文章id
//     * @return 当前文章的评论数
//     */
//    public Integer countComment(Long articleId);//统计当前文章的评论数

    /**
     * 获取当前评论的所有回复评论
     * @param commentId 当前评论id
     * @return 当前评论的所有回复评论
     */
    public List<Comment> getReplyByCommentId(Long commentId);//获取当前评论的所有回复评论
}
