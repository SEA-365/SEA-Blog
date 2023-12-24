package com.sea.controller;

import com.github.pagehelper.PageInfo;
import com.sea.annotation.OperationLogSys;
import com.sea.common.PageRequestApi;
import com.sea.entity.Article;
import com.sea.enums.OperationTypeEnum;
import com.sea.model.dto.PageResultDTO;
import com.sea.model.dto.ResponseDataDTO;
import com.sea.model.vo.ArticleVO;
import com.sea.model.vo.ConditionVO;
import com.sea.service.ArticleService;
import com.sea.util.PageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.sea.enums.StatusCodeEnum.*;

/**
 * @author: sea
 * @date: 2023/10/18 15:29
 */
@Api(tags = "文章信息管理模块")
@RestController
@RequestMapping("/articles")
public class ArticleController {
    @Autowired // 自动注入ArticleService对象
    ArticleService articleService;
    //日志打印
    private static final Logger log = LoggerFactory.getLogger(ArticleController.class);
    private static final String TAG = "ArticleController ====> ";

    /**
     * 请求指定页的文章信息
     * @return 指定页的文章信息
     */
    @ApiOperation(value = "请求指定页的文章信息") // Swagger注解，用于给接口添加描述信息
    @PostMapping("list") // 需要换成Post请求
    @OperationLogSys(description = "请求指定页的文章信息", operationType = OperationTypeEnum.SELECT)
    public ResponseDataDTO<PageResultDTO> getArticlePage(@RequestBody @Valid PageRequestApi<ConditionVO> conditionVO){
        log.info(TAG + "getArticlePage()");
        ResponseDataDTO<PageResultDTO> resultData = new ResponseDataDTO<>(); // 创建响应数据对象

        List<Article> articlePage = articleService.getArticleList(conditionVO.getBody()); // 调用articleService的方法获取全部文章信息

        PageInfo<Article> articlePageInfo = new PageInfo<>(articlePage);//这一步，会计算出相关的参数，例如总页数，总记录数等；
        //log.info(TAG + " " + articlePageInfo);
        PageResultDTO pageResultDTO = PageUtil.getPageResultDTO(conditionVO.getBody(), articlePageInfo);//封装数据

        if(articlePage != null){
            resultData.setStatusCode(SUCCESS.getCode()); // 设置响应状态码
            resultData.setData(pageResultDTO); // 设置响应数据
        }
        else {
            resultData.setStatusCode(FAIL.getCode());
            resultData.setData(pageResultDTO);
            resultData.setMsg("没有查询到指定页面的文章信息，请检查后重试！"); // 设置响应消息
        }
        return resultData; // 返回响应数据
    }

    /**
     * 根据id获取指定文章
     * @param articleId 文章id
     * @return 指定文章信息
     */
    @ApiOperation(value = "根据id获取指定文章") // Swagger注解，用于给接口添加描述信息
    @GetMapping("/{articleId}") // 处理HTTP GET请求，并将路径参数articleId映射到方法参数
    @OperationLogSys(description = "根据id获取指定文章", operationType = OperationTypeEnum.SELECT)
    public ResponseDataDTO<Article> getArticleById(@PathVariable Long articleId){
        log.info(TAG + "getArticleById()");
        ResponseDataDTO<Article> resultData = new ResponseDataDTO<>(); // 创建响应数据对象
        Article article = articleService.getArticleById(articleId); // 调用ArticleService的方法根据id获取文章
        if(article != null){
            resultData.setStatusCode(SUCCESS.getCode()); // 设置响应状态码
            resultData.setData(article); // 设置响应数据
        }
        else {
            resultData.setStatusCode(FAIL.getCode());
            resultData.setData(article);
            resultData.setMsg("查询文章失败，请检查重试！"); // 设置响应消息
        }
        return resultData; // 返回响应数据
    }

    /**
     * 添加文章
     * @param articleVO 待添加的文章信息
     */
    @ApiOperation(value = "添加文章") // Swagger注解，用于给接口添加描述信息
    @PostMapping // 处理HTTP POST请求
    @OperationLogSys(description = "添加文章", operationType = OperationTypeEnum.INSERT)
    public ResponseDataDTO<Boolean> addArticle(@RequestBody @Valid ArticleVO articleVO){
        log.info(TAG + "addArticle()");
        boolean result = articleService.addArticle(articleVO); // 调用ArticleService的方法添加文章
        return new ResponseDataDTO<>(result ? SUCCESS.getCode() : FAIL.getCode(), result); // 返回响应数据
    }

    /**
     * 修改文章
     * @param articleVO 新的文章信息
     */
    @ApiOperation(value = "修改文章") // Swagger注解，用于给接口添加描述信息
    @PutMapping // 处理HTTP PUT请求
    @OperationLogSys(description = "修改文章", operationType = OperationTypeEnum.UPDATE)
    public ResponseDataDTO<Boolean> updateArticle(@RequestBody @Valid ArticleVO articleVO){
        log.info(TAG + "updateArticle()");

        boolean result = articleService.updateArticle(articleVO); // 调用ArticleService的方法修改文章
        return new ResponseDataDTO<>(result ? SUCCESS.getCode() : FAIL.getCode(), result); // 返回响应数据
    }

    /**
     * 物理删除文章
     * @param articleIds 待删除文章id列表
     */
    @ApiOperation(value = "物理删除文章") // Swagger注解，用于给接口添加描述信息
    @DeleteMapping
    @OperationLogSys(description = "物理删除文章", operationType = OperationTypeEnum.DELETE)
    public ResponseDataDTO<Integer> deleteArticles(@RequestBody List<Long> articleIds){
        log.info(TAG + "deleteArticles()");
        Integer result = articleService.deleteArticles(articleIds); // 调用ArticleService的方法删除文章
        return new ResponseDataDTO<>(result >= 0 ? SUCCESS.getCode() : FAIL.getCode(), result); // 返回响应数据
    }
}
