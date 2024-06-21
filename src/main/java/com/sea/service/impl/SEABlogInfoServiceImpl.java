package com.sea.service.impl;

import com.alibaba.fastjson.JSON;
import com.sea.controller.NoticeController;
import com.sea.dao.WebsiteConfigDao;
import com.sea.model.dto.*;
import com.sea.entity.*;
import com.sea.dao.*;
import com.sea.model.vo.AboutVO;
import com.sea.service.SEABlogInfoService;
//import com.sea.service.RedisService;
//import com.sea.service.UniqueViewService;
//import com.sea.model.vo.AboutVO;
import com.sea.model.vo.WebsiteConfigVO;
//import eu.bitwalker.useragentutils.Browser;
//import eu.bitwalker.useragentutils.OperatingSystem;
//import eu.bitwalker.useragentutils.UserAgent;
import com.sea.util.BeanCopyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

import java.util.*;

import static com.sea.constant.CommonConstant.*;
import static com.sea.constant.RedisConstant.*;

@Service
public class SEABlogInfoServiceImpl implements SEABlogInfoService {

    //日志打印
    private static final Logger log = LoggerFactory.getLogger(SEABlogInfoServiceImpl.class);
    private static final String TAG = "SEABlogInfoServiceImpl ====> ";

    @Autowired
    private WebsiteConfigDao websiteConfigDao;

//    @Autowired
//    private ArticleMapper articleMapper;
//
//    @Autowired
//    private CategoryMapper categoryMapper;
//
//    @Autowired
//    private TagMapper tagMapper;
//
//    @Autowired
//    private CommentMapper commentMapper;
//
//    @Autowired
//    private TalkMapper talkMapper;
//
//    @Autowired
//    private UserInfoMapper userInfoMapper;
//
    @Autowired
    private AboutDao aboutDao;
//
//    @Autowired
//    private RedisService redisService;
//
//    @Autowired
//    private UniqueViewService uniqueViewService;

//    @Autowired
//    private HttpServletRequest request;

//    @Override
//    public void report() {
//        String ipAddress = IpUtil.getIpAddress(request);
//        UserAgent userAgent = IpUtil.getUserAgent(request);
//        Browser browser = userAgent.getBrowser();
//        OperatingSystem operatingSystem = userAgent.getOperatingSystem();
//        String uuid = ipAddress + browser.getName() + operatingSystem.getName();
//        String md5 = DigestUtils.md5DigestAsHex(uuid.getBytes());
//        if (!redisService.sIsMember(UNIQUE_VISITOR, md5)) {
//            String ipSource = IpUtil.getIpSource(ipAddress);
//            if (StringUtils.isNotBlank(ipSource)) {
//                String ipProvince = IpUtil.getIpProvince(ipSource);
//                redisService.hIncr(VISITOR_AREA, ipProvince, 1L);
//            } else {
//                redisService.hIncr(VISITOR_AREA, UNKNOWN, 1L);
//            }
//            redisService.incr(BLOG_VIEWS_COUNT, 1);
//            redisService.sAdd(UNIQUE_VISITOR, md5);
//        }
//    }
//
//    @SneakyThrows
//    @Override
//    public AuroraHomeInfoDTO getAuroraHomeInfo() {
//        CompletableFuture<Integer> asyncArticleCount = CompletableFuture.supplyAsync(() -> articleMapper.selectCount(new LambdaQueryWrapper<Article>().eq(Article::getIsDelete, FALSE)));
//        CompletableFuture<Integer> asyncCategoryCount = CompletableFuture.supplyAsync(() -> categoryMapper.selectCount(null));
//        CompletableFuture<Integer> asyncTagCount = CompletableFuture.supplyAsync(() -> tagMapper.selectCount(null));
//        CompletableFuture<Integer> asyncTalkCount = CompletableFuture.supplyAsync(() -> talkMapper.selectCount(null));
//        CompletableFuture<WebsiteConfigDTO> asyncWebsiteConfig = CompletableFuture.supplyAsync(this::getWebsiteConfig);
//        CompletableFuture<Integer> asyncViewCount = CompletableFuture.supplyAsync(() -> {
//            Object count = redisService.get(BLOG_VIEWS_COUNT);
//            return Integer.parseInt(Optional.ofNullable(count).orElse(0).toString());
//        });
//        return AuroraHomeInfoDTO.builder()
//                .articleCount(asyncArticleCount.get())
//                .categoryCount(asyncCategoryCount.get())
//                .tagCount(asyncTagCount.get())
//                .talkCount(asyncTalkCount.get())
//                .websiteConfigDTO(asyncWebsiteConfig.get())
//                .viewCount(asyncViewCount.get()).build();
//    }
//
//    @Override
//    public AuroraAdminInfoDTO getAuroraAdminInfo() {
//        Object count = redisService.get(BLOG_VIEWS_COUNT);
//        Integer viewsCount = Integer.parseInt(Optional.ofNullable(count).orElse(0).toString());
//        Integer messageCount = commentMapper.selectCount(new LambdaQueryWrapper<Comment>().eq(Comment::getType, 2));
//        Integer userCount = userInfoMapper.selectCount(null);
//        Integer articleCount = articleMapper.selectCount(new LambdaQueryWrapper<Article>()
//                .eq(Article::getIsDelete, FALSE));
//        List<UniqueViewDTO> uniqueViews = uniqueViewService.listUniqueViews();
//        List<ArticleStatisticsDTO> articleStatisticsDTOs = articleMapper.listArticleStatistics();
//        List<CategoryDTO> categoryDTOs = categoryMapper.listCategories();
//        List<TagDTO> tagDTOs = BeanCopyUtil.copyList(tagMapper.selectList(null), TagDTO.class);
//        Map<Object, Double> articleMap = redisService.zReverseRangeWithScore(ARTICLE_VIEWS_COUNT, 0, 4);
//        AuroraAdminInfoDTO auroraAdminInfoDTO = AuroraAdminInfoDTO.builder()
//                .articleStatisticsDTOs(articleStatisticsDTOs)
//                .tagDTOs(tagDTOs)
//                .viewsCount(viewsCount)
//                .messageCount(messageCount)
//                .userCount(userCount)
//                .articleCount(articleCount)
//                .categoryDTOs(categoryDTOs)
//                .uniqueViewDTOs(uniqueViews)
//                .build();
//        if (CollectionUtils.isNotEmpty(articleMap)) {
//            List<ArticleRankDTO> articleRankDTOList = listArticleRank(articleMap);
//            auroraAdminInfoDTO.setArticleRankDTOs(articleRankDTOList);
//        }
//        return auroraAdminInfoDTO;
//    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateWebsiteConfig(WebsiteConfigVO websiteConfigVO) {
        WebsiteConfig websiteConfig = WebsiteConfig.builder()
                .id(DEFAULT_CONFIG_ID)
                .config(JSON.toJSONString(websiteConfigVO))
                .build();
        websiteConfigDao.updateById(websiteConfig);
//        redisService.del(WEBSITE_CONFIG);
    }

    @Override
    public WebsiteConfigDTO getWebsiteConfig() {
        WebsiteConfigDTO websiteConfigDTO;
//        Object websiteConfig = redisService.get(WEBSITE_CONFIG);
//        if (Objects.nonNull(websiteConfig)) {
//            websiteConfigDTO = JSON.parseObject(websiteConfig.toString(), WebsiteConfigDTO.class);
//        } else {
            String config = websiteConfigDao.selectById(DEFAULT_CONFIG_ID).getConfig();
            websiteConfigDTO = JSON.parseObject(config, WebsiteConfigDTO.class);
//            redisService.set(WEBSITE_CONFIG, config);
//        }
        return websiteConfigDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAbout(AboutVO aboutVO) {
        About about = About.builder()
                .id(DEFAULT_ABOUT_ID)
                .content(JSON.toJSONString(aboutVO))
                .build();
        aboutDao.updateById(about);
//        redisService.del(ABOUT);
    }

    @Override
    public AboutDTO getAbout() {
        AboutDTO aboutDTO;
//        Object about = redisService.get(ABOUT);
//        if (Objects.nonNull(about)) {
//            aboutDTO = JSON.parseObject(about.toString(), AboutDTO.class);
//        } else {
            String content = aboutDao.selectById(DEFAULT_ABOUT_ID).getContent();
            log.info(TAG + " content: " + content);
            aboutDTO = new AboutDTO(content);
            log.info(TAG + " aboutDTO: " + aboutDTO);
//            redisService.set(ABOUT, content);
//        }
        return aboutDTO;
    }
//
//    private List<ArticleRankDTO> listArticleRank(Map<Object, Double> articleMap) {
//        List<Integer> articleIds = new ArrayList<>(articleMap.size());
//        articleMap.forEach((key, value) -> articleIds.add((Integer) key));
//        return articleMapper.selectList(new LambdaQueryWrapper<Article>()
//                        .select(Article::getId, Article::getArticleTitle)
//                        .in(Article::getId, articleIds))
//                .stream().map(article -> ArticleRankDTO.builder()
//                        .articleTitle(article.getArticleTitle())
//                        .viewsCount(articleMap.get(article.getId()).intValue())
//                        .build())
//                .sorted(Comparator.comparingInt(ArticleRankDTO::getViewsCount).reversed())
//                .collect(Collectors.toList());
//    }

}