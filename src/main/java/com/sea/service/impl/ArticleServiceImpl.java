package com.sea.service.impl;

import cn.hutool.extra.mail.MailUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.github.pagehelper.PageHelper;
import com.sea.config.mail.MailInfo;
import com.sea.config.mail.SendMailConfig;
import com.sea.dao.ArticleDao;
import com.sea.dao.ArticleTagDao;
import com.sea.dao.CategoryDao;
import com.sea.entity.*;
import com.sea.exception.BizException;
import com.sea.model.vo.ArticlePasswordVO;
import com.sea.model.vo.ArticleVO;
import com.sea.model.vo.ConditionVO;
import com.sea.model.vo.DeleteVO;
import com.sea.service.ArticleService;
import com.sea.service.ArticleTagService;
import com.sea.service.TagService;
import com.sea.service.UserService;
import com.sea.util.BeanCopyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.sea.enums.ArticleStatusEnum.DRAFT;

/**
 * @author: sea
 * @date: 2023/10/18 15:21
 */
@Service // 表示这是一个服务类
public class ArticleServiceImpl implements ArticleService {
    @Autowired // 自动注入ArticleDao对象
    ArticleDao articleDao;

    @Autowired // 自动注入ArticleTagDao对象
    ArticleTagDao articleTagDao;

    @Autowired // 自动注入CategoryDao对象
    CategoryDao categoryDao;

    @Autowired
    TagService tagService;

    @Autowired
    ArticleTagService articleTagService;

    @Autowired
    UserService userService;

    //日志打印
    public static final Logger log = LoggerFactory.getLogger(ArticleServiceImpl.class);

    public static final String TAG = "ArticleServiceImpl ====> ";

    //缓存文章数据
    Map<Long, Article> articleMap = new LinkedHashMap<>();

    @Override
    @PostConstruct //此注解用于初始化数据，在构造函数之后执行，init()方法之前执行。
    public void initData(){

        List<Article> articles = articleDao.getAllArticle();

        log.info(TAG + " articles: " + articles.size());
        try{
            for (Article article : articles) {
                articleMap.put(article.getId(), article);
            }

            log.info(TAG + "缓存文章信息完成！size:" + articleMap.size());
        }catch (Exception e){
            log.info(TAG + "缓存文章信息失败！" + e.getMessage());
        }

    }

    @Override
    public List<Article> getArticleList(ConditionVO conditionVO) {
        log.info(TAG + " " + conditionVO);

        if(conditionVO == null){
            return null;
        }
        PageHelper.startPage(conditionVO.getPageNum(), conditionVO.getPageSize());//设置分页查询参数

        List<Article> articleList = articleDao.getArticlePage(conditionVO); // 此时查询的记录为所有记录
        log.info(TAG + "获取文章列表 ===> " + articleList);
        return articleList; // 返回文章列表
    }

    @Override
    public Article getArticleById(Long articleId) {
        //todo: 需要鉴权
        Article article = articleMap.get(articleId);//先查缓存
        log.info(TAG + " articleMap: " + articleMap.size());
        log.info(TAG + " article: " + article);
        if(article == null)//缓存没有再查数据库
            return articleDao.selectById(articleId); // 根据文章ID查询文章
        log.info(TAG + "根据id获取文章 ===> " + article);
        return article;
    }

    @Override
    public boolean addArticle(ArticleVO articleVO) {
        if(articleVO.getTagNames() == null){
            ArrayList<String> tagNames = new ArrayList<>();
            tagNames.add(tagService.getTagById(0L).getTagName());
            articleVO.setTagNames(tagNames);
        }

        Category category = saveArticleCategory(articleVO);

        Article article = BeanCopyUtil.copyObject(articleVO, Article.class);
        if (Objects.nonNull(category)) {
            article.setCategoryId(category.getId());
        }
        articleDao.insert(article);
        saveArticleTag(articleVO, article.getId());

        log.info(TAG + "新增文章 ===> " + article);



        //新增文章后，邮件提示功能
        String mailContent = "【{0}】您好：\n" +
                "您已经成功发布了标题为：【{1}】 的文章\n" +
                "请注意查收！\n";
        //新增文章的作者
        User curUser = userService.getUserById(articleDao.selectById(article.getId()).getUserId());
        log.info(TAG + " curUser: " + curUser);
        if(curUser != null){
            MailInfo mailInfo = MailInfo.builder().receiveMail(curUser.getEmail())
                    .title("test文章发布")
                    .content(MessageFormat.format(mailContent, curUser.getUsername(), article.getTitle()))
                    .build();
            SendMailConfig.sendMail(mailInfo);
        }


        articleMap.put(article.getId(), article);//缓存更新
        return true;
    }

    @Override
    public boolean updateArticle(ArticleVO articleVO) {
        log.info(TAG + "修改文章 ===> " + articleVO);
        if(articleVO.getTagNames() == null){
            ArrayList<String> tagNames = new ArrayList<>();
            tagNames.add(tagService.getTagById(0L).getTagName());
            articleVO.setTagNames(tagNames);
        }

        Category category = saveArticleCategory(articleVO);

        Article article = BeanCopyUtil.copyObject(articleVO, Article.class);
        if (Objects.nonNull(category)) {
            article.setCategoryId(category.getId());
        }
        QueryWrapper<Article> articleQueryWrapper = new QueryWrapper<>();
        articleQueryWrapper.eq("id", articleVO.getId());
        articleDao.update(article, articleQueryWrapper);
        saveArticleTag(articleVO, article.getId());
        articleMap.put(article.getId(), article);//缓存更新
        return true;
    }

    @Override
    public void accessArticle(ArticlePasswordVO articlePasswordVO) {
        log.info(TAG + "访问加密文章 ===> " + articlePasswordVO);
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

        log.info(TAG + "根据标签获取文章 ===> " + articleByTagId);
        return articleByTagId;
    }

    @Override
    public Integer deleteArticles_logic(List<Long> articleIds) {
        log.info(TAG + "逻辑删除文章 ===> " + articleIds);
        //构造查询条件，查询集合articleIds包含的article_id，并删除；
        QueryWrapper<ArticleTag> articleTagQueryWrapper = new QueryWrapper<>();
        articleTagQueryWrapper.in("article_id", articleIds);

        articleTagDao.delete(articleTagQueryWrapper);//删除关联表的记录


        for (Long articleId : articleIds) {
            Article article = articleDao.selectById(articleId);
            article.setIsDelete(1);
            //缓存更新
            articleMap.put(articleId, article);

            //数据库更新
            QueryWrapper<Article> articleQueryWrapper = new QueryWrapper<>();
            articleQueryWrapper.eq("id", articleId);
            articleDao.update(article, articleQueryWrapper);
        }

        return 0;//删除文章
    }


    @Override
    public Integer deleteArticles_real(List<Long> articleIds) {
        log.info(TAG + "物理删除文章 ===> " + articleIds);
        //构造查询条件，查询集合articleIds包含的article_id，并删除；
        QueryWrapper<ArticleTag> articleTagQueryWrapper = new QueryWrapper<>();
        articleTagQueryWrapper.in("article_id", articleIds);

        articleTagDao.delete(articleTagQueryWrapper);//删除关联表的记录


        for (Long articleId : articleIds) {
            //缓存更新
            articleMap.remove(articleId);
        }

        return articleDao.deleteBatchIds(articleIds);//删除文章
    }
    private Category saveArticleCategory(ArticleVO articleVO) {
        log.info(TAG + "文章分类保存 ===> " + articleVO);
        //1.未出现过得分类=>新建分类；2.出现过的直接返回
        Category category = categoryDao.selectOne(new QueryWrapper<Category>().eq("category_name", articleVO.getCategoryName()));
        log.info(TAG + category + " === " + articleVO + " === ");
        if (Objects.isNull(category) ) {//&& !articleVO.getStatus().equals(DRAFT.getCode())
            category = new Category();
            if(articleVO.getCategoryName() != null) {
                category.setCategoryName(articleVO.getCategoryName());
                log.info(TAG + category + " === new");
                categoryDao.insert(category);
            }
            else { //未指定分类名称，则使用默认分类名称
                return categoryDao.selectById(0);
            }
        }
        return category;
    }


    public void saveArticleTag(ArticleVO articleVO, Long articleId) {
        log.info(TAG + "文章标签关联关系保存 ===> " + articleVO + "====" + articleId);
        if (Objects.nonNull(articleVO.getId())) {//1.文章标签关联表已存在该文章的一些记录，则先删除
            articleTagDao.delete(new QueryWrapper<ArticleTag>()
                    .eq("article_id", articleVO.getId()));
        }
        List<String> tagNames = articleVO.getTagNames();
        log.info(TAG + "tagNames: " + tagNames);
        if (CollectionUtils.isNotEmpty(tagNames)) {
            //获取已存在的标签
            List<Tag> existTags = tagService.list(new QueryWrapper<Tag>().in("tag_name", tagNames));
            log.info(TAG + "existTags: " + existTags);
            //获取已存在的标签名称
            //Java8新增用法
            List<String> existTagNames = existTags.stream()
                    .map(Tag::getTagName)
                    .collect(Collectors.toList());
            log.info(TAG + "existTagNames: " + existTagNames);
            //获取已存在的标签id
            List<Long> existTagIds = existTags.stream()
                    .map(Tag::getId)
                    .collect(Collectors.toList());
            log.info(TAG + "existTagIds: " + existTagIds);

            //移除请求数据中已存在的标签名称
            tagNames.removeAll(existTagNames);
            log.info(TAG + "tagNames: " + tagNames);
            //第一次出现的标签，新建
            if (CollectionUtils.isNotEmpty(tagNames)) {
                ArrayList<Tag> tags = new ArrayList<>();
                for (int i = 0; i < tagNames.size(); i++) {
                    Tag tag = new Tag();
                    tag.setTagName(tagNames.get(i));
                    tags.add(tag);
                }
                //新建标签
                tagService.saveBatch(tags);
                //新建标签后得到标签的id，用于新建文章标签关联表
                List<Long> tagIds = tags.stream()
                        .map(Tag::getId)
                        .collect(Collectors.toList());
                existTagIds.addAll(tagIds);
            }
            log.info(TAG + "existTagIds: " + existTagIds);
            //新建文章标签关联表
            List<ArticleTag> articleTags = new ArrayList<>();
            for (int i = 0; i < existTagIds.size(); i++) {
                ArticleTag articleTag = new ArticleTag();
                articleTag.setArticleId(articleId);
                articleTag.setTagId(existTagIds.get(i));
                articleTags.add(articleTag);
            }
            log.info(TAG + "articleTags: " + articleTags);

            boolean isSave = articleTagService.saveBatch(articleTags);
            log.info(TAG + "isSave: " + isSave);
        }
        else {

        }
    }

}
