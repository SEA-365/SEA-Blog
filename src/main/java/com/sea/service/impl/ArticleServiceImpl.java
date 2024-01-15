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
import com.sea.enums.FilePathEnum;
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
import com.sea.util.FileUtil;
import com.sea.util.TotalWordsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.io.*;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

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

    String imgPath = FilePathEnum.ARTICLE.getPath();

    @Value("${upload.local.url}")
    String localUrl;

    @Value("${upload.local.path}")
    String localPath;

    private
    SendMailConfig mailConfig = new SendMailConfig();


    //日志打印
    public static final Logger log = LoggerFactory.getLogger(ArticleServiceImpl.class);

    public static final String TAG = "ArticleServiceImpl ====> ";

    //缓存文章数据
    Map<Long, Article> articleMap = new LinkedHashMap<>();

    @Override
    @PostConstruct //此注解用于初始化数据，在构造函数之后执行，init()方法之前执行。
    public void initData(){

        ConditionVO conditionVO = new ConditionVO();
        conditionVO.setPageNum(1);
        conditionVO.setPageSize(10);
        List<Article> articles = articleDao.getArticlePage(conditionVO);

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
        //todo: 此处应该先根据其他条件进行查询，然后基于该结果分页，其他涉及多条件查询的实体类List同理
        PageHelper.startPage(conditionVO.getPageNum(), conditionVO.getPageSize());//设置分页查询参数
        List<Article> articleList = articleDao.getArticlePage(conditionVO); // 此时查询的记录为所有记录
        log.info(TAG + "获取文章列表 ===> ");
        for (Article article : articleList) {
            log.info(TAG + article);
        }
        return articleList; // 返回文章列表
    }

    @Override
    public Article getArticleById(Long articleId) {
        //todo: 需要鉴权
        Article article = articleMap.get(articleId);//先查缓存
        log.info(TAG + " articleMap: " + articleMap.size());
        log.info(TAG + " article: " + article);
        if(article == null)//缓存没有再查数据库
            return articleDao.getArticleById(articleId); // 根据文章ID查询文章
        log.info(TAG + "根据id获取文章 ===> " + article);
        return article;
    }

    @Override
    public boolean addArticle(ArticleVO articleVO) {
        if(articleVO.getTagList() == null){
            ArrayList<Tag> tagList = new ArrayList<>();
            tagList.add(tagService.getTagById(0L));
            articleVO.setTagList(tagList);
        }

        Category category = saveArticleCategory(articleVO);

        Article article = BeanCopyUtil.copyObject(articleVO, Article.class);
        if (Objects.nonNull(category)) {
            article.setCategoryId(category.getId());
        }

        article.setTotalWords(TotalWordsUtil.wordCount(articleVO.getContent()));
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
            mailConfig.sendMail(mailInfo);
        }


        articleMap.put(article.getId(), article);//缓存更新
        return true;
    }

    @Override
    public boolean updateArticle(ArticleVO articleVO) {
        log.info(TAG + "修改文章 ===> " + articleVO);
        if(articleVO.getTagList() == null){
            ArrayList<Tag> tagList = new ArrayList<>();
            tagList.add(tagService.getTagById(0L));
            articleVO.setTagList(tagList);
        }

        Category category = saveArticleCategory(articleVO);

        Article article = BeanCopyUtil.copyObject(articleVO, Article.class);
        if (Objects.nonNull(category)) {
            article.setCategoryId(category.getId());
        }

        article.setTotalWords(TotalWordsUtil.wordCount(articleVO.getContent()));

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
        List<String> tagNameList = new ArrayList<>();
        for (Tag tag : articleVO.getTagList()) {
            tagNameList.add(tag.getTagName());
        }

        log.info(TAG + "tagNameList: " + tagNameList);
        if (CollectionUtils.isNotEmpty(tagNameList)) {
            //获取已存在的标签
            List<Tag> existTags = tagService.list(new QueryWrapper<Tag>().in("tag_name", tagNameList));
            log.info(TAG + "existTags: " + existTags);
            //获取已存在的标签名称
            //Java8新增用法
            List<String> existTagList = existTags.stream()
                    .map(Tag::getTagName)
                    .collect(Collectors.toList());
            log.info(TAG + "existTagList: " + existTagList);
            //获取已存在的标签id
            List<Long> existTagIds = existTags.stream()
                    .map(Tag::getId)
                    .collect(Collectors.toList());
            log.info(TAG + "existTagIds: " + existTagIds);

            //移除请求数据中已存在的标签名称
            tagNameList.removeAll(existTagList);
            log.info(TAG + "tagNameList: " + tagNameList);
            //第一次出现的标签，新建
            if (CollectionUtils.isNotEmpty(tagNameList)) {
                ArrayList<Tag> tags = new ArrayList<>();
                for (int i = 0; i < tagNameList.size(); i++) {
                    Tag tag = new Tag();
                    tag.setTagName(tagNameList.get(i));
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


    @Override
    public String uploadFile(MultipartFile file) {
        log.info(TAG + " uploadFile() ");
        try {
            //1.获取文件的MD5，目的：根据文件内容生成唯一的哈希值，保证文件数据的完整性；
            String md5 = FileUtil.getMd5(file.getInputStream());
            //2.获取文件拓展名：如 .txt、.jpg等
            String extName = FileUtil.getExtName(file.getOriginalFilename());
            //3.得到新的文件名
            String newFileName = md5 + extName;
            //4.上传文件：即写入到服务器的指定位置
            log.info(TAG + " localUrl: " + localUrl);
            log.info(TAG + " localPath: " + localPath);
            log.info(TAG + " imgPath: " + imgPath);
            log.info(TAG + " newFileName: " + newFileName);
            if (!exists(localPath + imgPath + newFileName)) {
                upload(localPath + imgPath, newFileName, file.getInputStream());
            }
            return getFileAccessUrl(newFileName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("文件上传失败");
        }

    }

    /**
     * 判断文件是否存在
     *
     * @param filePath 文件路径
     * @return
     */
    public Boolean exists(String filePath){
        return new File(localPath + filePath).exists();
    }

    /**
     * 获取文件访问url
     *
     * @param fileName 文件名
     * @return 完整的服务器文件访问地址url
     */
    public String getFileAccessUrl(String fileName) {
        log.info(TAG + " accessUrl: " + localUrl + imgPath + fileName);
        return localUrl + imgPath + fileName;
    }

    private void upload(String path, String fileName, InputStream inputStream)throws IOException{
        log.info(TAG + " upload() ");
        log.info(TAG + " path: " + path);
        log.info(TAG + " fileName: " + fileName);
        //1.判断文章相关的目录是否已创建，若没有则创建
        File directory = new File(path);
        if(!directory.exists()){
            log.info(TAG + "目录不存在，需要创建目录！");
            if(!directory.mkdirs()){
                log.error(TAG + "创建目录失败！");
            }
            else {
                log.info(TAG + "目录创建成功!");
            }
        }

        //2.写入文件
        // 在服务器创建新的文件接收用户上传的文件数据
        File file = new File(path + fileName);
        if(file.createNewFile()){
            // 文件输入流：用户上传的文件读到内存
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

            // 文件输出流：用户上传的文件写入到服务器指定目录
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
            byte[] bytes = new byte[1024];
            int len;
            while((len = bufferedInputStream.read(bytes)) != -1){
                bufferedOutputStream.write(bytes, 0, len);
            }
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
            bufferedInputStream.close();
            inputStream.close();
        }
    }

}
