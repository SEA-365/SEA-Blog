package com.sea.controller;

import com.github.pagehelper.PageInfo;
import com.sea.annotation.OperationLogSys;
import com.sea.common.PageRequestApi;
import com.sea.entity.Comment;
import com.sea.enums.OperationTypeEnum;
import com.sea.model.dto.PageResultDTO;
import com.sea.model.dto.ResponseDataDTO;
import com.sea.model.vo.CommentVO;
import com.sea.model.vo.ConditionVO;
import com.sea.service.CommentService;
import com.sea.util.PageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
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
@Api(tags = "评论信息管理模块")
@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired // 自动注入CommentService对象
    CommentService commentService;
    //日志打印
    private static final Logger log = LoggerFactory.getLogger(CommentController.class);
    private static final String TAG = "CommentController ====> ";

    /**
     * 请求指定页的评论信息
     * @return 指定页的评论信息
     */
    @ApiOperation(value = "请求指定页的评论信息") // Swagger注解，用于给接口添加描述信息
    @PostMapping("list") // 处理HTTP GET请求
    @OperationLogSys(description = "请求指定页的评论信息", operationType = OperationTypeEnum.SELECT)
    public ResponseDataDTO<PageResultDTO> getCommentPage(@RequestBody @Valid PageRequestApi<ConditionVO> conditionVO){
        log.info(TAG + "getCommentPage()");
        ResponseDataDTO<PageResultDTO> resultData = new ResponseDataDTO<>(); // 创建响应数据对象

        List<Comment> commentPage = commentService.getCommentList(conditionVO.getBody()); // 调用commentService的方法获取全部评论信息

        PageInfo<Comment> commentPageInfo = new PageInfo<>(commentPage);//这一步，会计算出相关的参数，例如总页数，总记录数等；
        //log.info(TAG + " " + commentPageInfo);
        PageResultDTO pageResultDTO = PageUtil.getPageResultDTO(conditionVO.getBody(), commentPageInfo);//封装数据

        if(commentPage != null){
            resultData.setStatusCode(SUCCESS.getCode()); // 设置响应状态码
            resultData.setData(pageResultDTO); // 设置响应数据
        }
        else {
            resultData.setStatusCode(FAIL.getCode());
            resultData.setData(pageResultDTO);
            resultData.setMsg("没有查询到指定页面的评论信息，请检查后重试！"); // 设置响应消息
        }
        return resultData; // 返回响应数据
    }

    /**
     * 根据id获取指定评论
     * @param commentId 评论id
     * @return 指定评论信息
     */
    @ApiOperation(value = "根据id获取指定评论") // Swagger注解，用于给接口添加描述信息
    @GetMapping("/{commentId}") // 处理HTTP GET请求，并将路径参数commentId映射到方法参数
    @OperationLogSys(description = "根据id获取指定分类", operationType = OperationTypeEnum.SELECT)
    public ResponseDataDTO<Comment> getCommentById(@PathVariable Long commentId){
        log.info(TAG + "getCommentById()");
        ResponseDataDTO<Comment> resultData = new ResponseDataDTO<>(); // 创建响应数据对象
        Comment comment = commentService.getCommentById(commentId); // 调用CommentService的方法根据id获取评论
        if(comment != null){
            resultData.setStatusCode(SUCCESS.getCode()); // 设置响应状态码
            resultData.setData(comment); // 设置响应数据
        }
        else {
            resultData.setStatusCode(FAIL.getCode());
            resultData.setData(comment);
            resultData.setMsg("查询评论失败，请检查重试！"); // 设置响应消息
        }
        return resultData; // 返回响应数据
    }

    /**
     * 添加评论
     * @param commentVO 待添加的评论信息
     */
    @ApiOperation(value = "添加评论") // Swagger注解，用于给接口添加描述信息
    @PostMapping // 处理HTTP POST请求
    @OperationLogSys(description = "添加评论", operationType = OperationTypeEnum.INSERT)
    public ResponseDataDTO<Boolean> addComment(@RequestBody @Valid CommentVO commentVO){
        log.info(TAG + "addComment()");
        boolean result = commentService.addComment(commentVO); // 调用CommentService的方法添加评论
        return new ResponseDataDTO<>(result ? SUCCESS.getCode() : FAIL.getCode(), result); // 返回响应数据
    }

    /**
     * 删除评论
     * @param commentId 待删除评论id
     */
    @ApiOperation(value = "删除评论-逻辑删除") // Swagger注解，用于给接口添加描述信息
    @DeleteMapping("/{commentId}") // 处理HTTP DELETE请求，并将路径参数commentId映射到方法参数
    @OperationLogSys(description = "删除评论-逻辑删除", operationType = OperationTypeEnum.DELETE)
    public ResponseDataDTO<Boolean> deleteComment(@PathVariable Long commentId){
        log.info(TAG + "deleteComment()");
        boolean result = commentService.deleteCommentById(commentId); // 调用CommentService的方法删除评论
        return new ResponseDataDTO<>(result ? SUCCESS.getCode() : FAIL.getCode(), result); // 返回响应数据
    }

    /**
     * 获取当前评论的所有回复评论
     * @param commentId 当前评论id
     */
    @ApiModelProperty(value = "获取当前评论的所有回复评论")
    @GetMapping("/getReply/{commentId}")
    @OperationLogSys(description = "获取当前评论的所有回复评论", operationType = OperationTypeEnum.SELECT)
    public ResponseDataDTO<List<Comment>> getReplyByCommentId(@PathVariable Long commentId){
        log.info(TAG + "getReplyByCommentId()");
        ResponseDataDTO<List<Comment>> resultData = new ResponseDataDTO<>(); // 创建响应数据对象
        List<Comment> replyList = commentService.getReplyByCommentId(commentId);// 调用commentService的方法获取当前评论的所有回复评论
        if(replyList != null){
            resultData.setStatusCode(SUCCESS.getCode()); // 设置响应状态码
            resultData.setData(replyList); // 设置响应数据
        }
        else {
            resultData.setStatusCode(FAIL.getCode());
            resultData.setData(replyList);
            resultData.setMsg("没有查询到评论列表，请检查后重试！"); // 设置响应消息
        }
        return resultData; // 返回响应数据
    }
}
