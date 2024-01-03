package com.sea.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.sea.model.vo.CommentVO;
import com.sea.model.vo.ConditionVO;
import com.sea.dao.CommentDao;
import com.sea.entity.Comment;
import com.sea.service.CommentService;
import com.sea.util.BeanCopyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: sea
 * @date: 2023/10/19 18:11
 */
@Service // 表示这是一个服务类
public class CommentServiceImpl implements CommentService {
    @Autowired // 自动注入CommentDao对象
    CommentDao commentDao;

    //日志打印
    public static final Logger log = LoggerFactory.getLogger(CommentServiceImpl.class);

    public static final String TAG = "CommentServiceImpl ====> ";

    @Override
    public List<Comment> getCommentList(ConditionVO conditionVO) {
        log.info(TAG + " " + conditionVO);

        PageHelper.startPage(conditionVO.getPageNum(), conditionVO.getPageSize());//设置分页查询参数
        List<Comment> commentList = commentDao.selectList(null); // 此时查询的记录为所有记录
        return commentList; // 返回评论列表
    }

    @Override
    public Comment getCommentById(Long commentId) {
        return commentDao.selectById(commentId); // 根据评论ID查询评论
    }

    @Override
    public boolean addComment(CommentVO commentVO) {
        Comment comment = BeanCopyUtil.copyObject(commentVO, Comment.class);
        commentDao.insert(comment); // 添加评论
        return true;
    }

    @Override
    public boolean deleteCommentById_logic(Long commentId) {
        Comment comment = commentDao.selectById(commentId);
        comment.setIsDelete(1);//逻辑删除

        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq("id", commentId);

        commentDao.update(comment, commentQueryWrapper);
        return true;
    }

    @Override
    public boolean deleteCommentById_real(Long commentId) {

        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq("id", commentId);

        commentDao.delete(commentQueryWrapper);
        return true;
    }


    @Override
    public List<Comment> getReplyByCommentId(Long commentId) {
        log.info(TAG + " getReplyByCommentId() ");

        QueryWrapper<Comment> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("id", commentId);
        Comment cur_comment = commentDao.selectOne(objectQueryWrapper);
        log.info(TAG + "  getReplyByCommentId()  " + cur_comment);
        Long commentCreateId = cur_comment.getCommentCreateId();
        Long articleId = cur_comment.getArticleId();

        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq("comment_reply_id", commentCreateId);
        commentQueryWrapper.eq("article_id", articleId);
        commentQueryWrapper.orderByAsc("comment_create_time");//按照创建时间升序排序，最新的评论在最后
        List<Comment> comments = commentDao.selectList(commentQueryWrapper);

        log.info(TAG + "  getReplyByCommentId()  " + comments);

        return comments;
    }
}
