package com.sea.service;

import com.sea.entity.Article;
import com.sea.model.vo.ArticlePasswordVO;
import com.sea.model.vo.ArticleVO;
import com.sea.model.vo.ConditionVO;
import com.sea.model.vo.DeleteVO;
import org.springframework.web.multipart.MultipartFile;

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
    List<Article> getArticleList(ConditionVO conditionVO);

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
    Integer deleteArticles_logic(List<Long> articleIds);

    /**
     * 物理删除文章
     *
     * @param articleIds
     * @return
     */
    Integer deleteArticles_real(List<Long> articleIds);

    /**
     * 文件上传：
     *      （1）MultipartFile类：是 Spring Framework 中用于表示文件上传的接口，提供了一系列获取文件信息的方法，可以获取文件的InputStream，将文件保存到本地磁盘；
     *      （2）支持多文件上传，通过数组或集合方式接受；
     * @param file 上传的文件
     * @return 文件访问路径
     */
    String uploadFile(MultipartFile file);

}
