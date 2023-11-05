package com.sea.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.sea.dao.ArticleDao;
import com.sea.entity.Article;
import com.sea.entity.ArticleTag;
import com.sea.exception.BizException;
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

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    //缓存文章数据
    Map<Long, Article> articleMap = new LinkedHashMap<>();

    @Override
    @PostConstruct //此注解用于初始化数据，在构造函数之后执行，init()方法之前执行。
    public void initData(){
        List<Article> articles = articleDao.selectList(null);
        try{
            log.info(TAG + "缓存文章信息完成！");
        }catch (Exception e){
            log.info(TAG + "缓存文章信息失败！" + e.getMessage());
        }
        for (Article article : articles) {
            articleMap.put(article.getId(), article);
        }

    }

    @Override
    public List<Article> getArticleList(ConditionVO conditionVO) {
        log.info(TAG + " " + conditionVO);

        PageHelper.startPage(conditionVO.getPageNum(), conditionVO.getPageSize());//设置分页查询参数

        List<Article> articleList = articleDao.getArticlePage(conditionVO); // 此时查询的记录为所有记录
        return articleList; // 返回文章列表
    }

    @Override
    public Article getArticleById(Long articleId) {
        //todo: 需要鉴权
        Article article = articleMap.get(articleId);//先查缓存
        if(article == null)//缓存没有再查数据库
            return articleDao.selectById(articleId); // 根据文章ID查询文章
        return article;
    }

    @Override
    public boolean addArticle(ArticleVO articleVO) {
        Article article = BeanCopyUtil.copyObject(articleVO, Article.class);
        articleDao.insert(article);
        log.info(TAG + "新增文章 ===> " + article);
        articleMap.put(article.getId(), article);//缓存更新
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
        articleMap.put(id, article);//缓存更新
        return true;
    }

    @Override
    public void accessArticle(ArticlePasswordVO articlePasswordVO) {
        QueryWrapper<Article> articleQueryWrapper = new QueryWrapper<>();
        articleQueryWrapper.eq("id", articlePasswordVO.getArticleId());
        Article article = articleDao.selectOne(articleQueryWrapper);
        if(Objects.isNull(article)) {//文章不存在
            throw new BizException("文章不存在！");
        }
        if(article.getPassword().equals(articlePasswordVO.getArticlePassword()))
            return;
        else
            throw new BizException("密码错误！请重新输入！");
    }

    @Override
    public List<Article> getArticleByTagId(Long tagId) {
        ConditionVO conditionVO = new ConditionVO();
        conditionVO.setTagId(tagId);
        conditionVO.setPageNum(1);
        conditionVO.setPageNum(5);

        List<Article> articleByTagId = articleDao.getArticleByTagId(conditionVO);

        return articleByTagId;
    }

    @Override
    public void updateArticleDeleteStatus(DeleteVO deleteVO) {

    }

    @Override
    public void deleteArticles(List<Long> articleIds) {

    }
}
