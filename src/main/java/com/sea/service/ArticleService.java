package com.sea.service;

import com.sea.entity.Article;
import com.sea.model.vo.ArticlePasswordVO;
import com.sea.model.vo.ArticleVO;
import com.sea.model.vo.ConditionVO;
import com.sea.model.vo.DeleteVO;

import java.util.List;

/**
 * 文章表业务层接口
 * @author: sea
 * @date: 2023/10/20 18:38
 */
public interface ArticleService {

    /**
     * 初始化数据，在项目启动时，将数据放到缓存中，减少对数据库的访问次数
     */

    void initData();
    /**
     * 获取文章信息列表
     * @param conditionVO 查询条件
     * @return 指定页的文章信息
     */
    public List<Article> getArticleList(ConditionVO conditionVO);

    /**
     * 根据id获取指定文章信息
     * @param articleId 文章id
     * @return 指定文章信息
     */
    Article getArticleById(Long articleId);

    /**
     * 添加文章
     * @param articleVO 待添加的文章信息
     */
    boolean addArticle(ArticleVO articleVO);

    /**
     * 修改文章
     * @param articleVO 新的文章信息
     */
    boolean updateArticle(ArticleVO articleVO);

    /**
     * 访问加密文章
     * @param articlePasswordVO 访问加密文章需要的信息
     */
    void accessArticle(ArticlePasswordVO articlePasswordVO);

    /**
     * 根据标签id获取该标签的文章列表
     * @param tagId 标签id
     * @return 有该标签的文章列表
     */
    List<Article> getArticleByTagId(Long tagId);

    /**
     * todo: 归档操作，目前还不知道怎么做，先往后写，后面回来补
     */

    /**
     * 逻辑删除文章
     *
     * @param articleIds
     * @return
     */
    Integer deleteArticles(List<Long> articleIds);

}
