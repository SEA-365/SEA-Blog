package com.sea.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.sea.dao.ArticleDao;
import com.sea.entity.Article;
import com.sea.model.vo.ArticlePasswordVO;
import com.sea.model.vo.ArticleVO;
import com.sea.model.vo.ConditionVO;
import com.sea.model.vo.DeleteVO;
import com.sea.service.ArticleService;
import com.sea.util.BeanCopyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: sea
 * @date: 2023/10/18 15:21
 */
@Service // 表示这是一个服务类
public class ArticleServiceImpl implements ArticleService {
    @Autowired // 自动注入ArticleDao对象
    ArticleDao articleDao;

    //日志打印
    public static final Logger log = LoggerFactory.getLogger(ArticleServiceImpl.class);

    public static final String TAG = "ArticleServiceImpl ====> ";

    @Override
    public List<Article> getArticleList(ConditionVO ConditionVO) {
        log.info(TAG + " " + ConditionVO);

        PageHelper.startPage(ConditionVO.getPageNum(), ConditionVO.getPageSize());//设置分页查询参数
        List<Article> articleList = articleDao.selectList(null); // 此时查询的记录为所有记录
        return articleList; // 返回文章列表
    }

    @Override
    public Article getArticleById(Long articleId) {
        //todo: 需要鉴权

        return articleDao.selectById(articleId); // 根据文章ID查询文章
    }

    @Override
    public boolean addArticle(ArticleVO articleVO) {
        Article article = BeanCopyUtil.copyObject(articleVO, Article.class);
        articleDao.insert(article); // 添加文章
        return true;
    }

    @Override
    public boolean updateArticle(ArticleVO articleVO) {
        Article article = BeanCopyUtil.copyObject(articleVO, Article.class);
        Long id = articleVO.getId();
        // 使用条件构造器。指定更新条件
        UpdateWrapper<Article> articleUpdateWrapper = new UpdateWrapper<>();
        // 此处第一个参数为列名，第二个参数为值，相当于子句：where id = Article.id
        articleUpdateWrapper.eq("id", id);
        articleDao.update(article, articleUpdateWrapper); // 更新文章
        return true;
    }

    @Override
    public List<Article> getArticleByCategoryId(Long categoryId) {
        return null;
    }

    @Override
    public void accessArticle(ArticlePasswordVO articlePasswordVO) {

    }

    @Override
    public List<Article> getArticleByTagId(Long tagId) {
        return null;
    }

    @Override
    public void updateArticleDeleteStatus(DeleteVO deleteVO) {

    }

    @Override
    public void deleteArticles(List<Long> articleIds) {

    }
}
