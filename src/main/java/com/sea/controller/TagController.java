package com.sea.controller;

import com.github.pagehelper.PageInfo;
import com.sea.annotation.OperationLogSys;
import com.sea.common.PageRequestApi;
import com.sea.enums.OperationTypeEnum;
import com.sea.model.dto.PageResultDTO;
import com.sea.model.dto.ResponseDataDTO;
import com.sea.model.vo.ConditionVO;
import static com.sea.enums.StatusCodeEnum.*;
import com.sea.model.vo.TagVO;
import com.sea.entity.Tag;
import com.sea.service.TagService;
import com.sea.util.PageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author: sea
 * @date: 2023/10/18 15:29
 */
@Api(tags = "标签信息管理模块")
@RestController
@RequestMapping("/tags")
public class TagController {
    @Autowired // 自动注入TagService对象
    TagService tagService;
    //日志打印
    private static final Logger log = LoggerFactory.getLogger(TagController.class);
    private static final String TAG = "TagController ====> ";

    /**
     * 请求指定页的标签信息
     * @return 指定页的标签信息
     */
    @ApiOperation(value = "请求指定页的标签信息") // Swagger注解，用于给接口添加描述信息
    @PostMapping("list") // 需要换成Post请求
    @OperationLogSys(description = "请求指定页的标签信息", operationType = OperationTypeEnum.SELECT)
    public ResponseDataDTO<PageResultDTO> getTagPage(@RequestBody @Valid PageRequestApi<ConditionVO> conditionVO){
        log.info(TAG + "getTagPage()");
        ResponseDataDTO<PageResultDTO> resultData = new ResponseDataDTO<>(); // 创建响应数据对象

        List<Tag> tagPage = tagService.getTagList(conditionVO.getBody()); // 调用tagService的方法获取全部标签信息

        PageInfo<Tag> tagPageInfo = new PageInfo<>(tagPage);//这一步，会计算出相关的参数，例如总页数，总记录数等；
        //log.info(TAG + " " + tagPageInfo);
        PageResultDTO pageResultDTO = PageUtil.getPageResultDTO(conditionVO.getBody(), tagPageInfo);//封装数据

        if(tagPage != null){
            resultData.setStatusCode(SUCCESS.getCode()); // 设置响应状态码
            resultData.setData(pageResultDTO); // 设置响应数据
        }
        else {
            resultData.setStatusCode(FAIL.getCode());
            resultData.setData(pageResultDTO);
            resultData.setMsg("没有查询到指定页面的标签信息，请检查后重试！"); // 设置响应消息
        }
        return resultData; // 返回响应数据
    }

    /**
     * 根据id获取指定标签
     * @param tagId 标签id
     * @return 指定标签信息
     */
    @ApiOperation(value = "根据id获取指定标签") // Swagger注解，用于给接口添加描述信息
    @GetMapping("/{tagId}") // 处理HTTP GET请求，并将路径参数tagId映射到方法参数
    @OperationLogSys(description = "根据id获取指定标签", operationType = OperationTypeEnum.SELECT)
    public ResponseDataDTO<Tag> getTagById(@PathVariable Long tagId){
        log.info(TAG + "getTagById()");
        ResponseDataDTO<Tag> resultData = new ResponseDataDTO<>(); // 创建响应数据对象
        Tag tag = tagService.getTagById(tagId); // 调用TagService的方法根据id获取标签
        if(tag != null){
            resultData.setStatusCode(SUCCESS.getCode()); // 设置响应状态码
            resultData.setData(tag); // 设置响应数据
        }
        else {
            resultData.setStatusCode(FAIL.getCode());
            resultData.setData(null);
            resultData.setMsg("查询标签失败，请检查重试！"); // 设置响应消息
        }
        return resultData; // 返回响应数据
    }

    /**
     * 根据标签名称获取标签
     * @param  conditionVO 查询条件
     * @return 标签信息
     */
    @ApiOperation(value = "根据标签名称获取标签") // Swagger注解，用于给接口添加描述信息
    @PostMapping("/getTagByName") //
    @OperationLogSys(description = "根据id获取指定标签", operationType = OperationTypeEnum.SELECT)
    public ResponseDataDTO<List<Tag>> getTagByName(@RequestBody @Valid PageRequestApi<ConditionVO> conditionVO){
        log.info(TAG + "getTagById()");
        ResponseDataDTO<List<Tag>> resultData = new ResponseDataDTO<>(); // 创建响应数据对象
        List<Tag> tags = tagService.getTagByName(conditionVO.getBody()); // 调用TagService的方法根据id获取标签
        if(tags != null){
            resultData.setStatusCode(SUCCESS.getCode()); // 设置响应状态码
            resultData.setData(tags); // 设置响应数据
        }
        else {
            resultData.setStatusCode(FAIL.getCode());
            resultData.setData(null);
            resultData.setMsg("查询标签失败，请检查重试！"); // 设置响应消息
        }
        return resultData; // 返回响应数据
    }

    /**
     * 添加标签
     * @param tagVO 待添加的标签信息
     */
    @ApiOperation(value = "添加标签") // Swagger注解，用于给接口添加描述信息
    @PostMapping // 处理HTTP POST请求
    @OperationLogSys(description = "添加标签", operationType = OperationTypeEnum.INSERT)
    public ResponseDataDTO<Boolean> addTag(@RequestBody @Valid TagVO tagVO){
        log.info(TAG + "addTag()");
        boolean result = tagService.addTag(tagVO); // 调用TagService的方法添加标签
        return new ResponseDataDTO<>(result ? SUCCESS.getCode() : FAIL.getCode(), result, result ? "添加标签成功！" : "已存在该标签！"); // 返回响应数据
    }

    /**
     * 修改标签
     * @param tagVO 新的标签信息
     */
    @ApiOperation(value = "修改标签") // Swagger注解，用于给接口添加描述信息
    @PutMapping // 处理HTTP PUT请求
    @OperationLogSys(description = "修改标签", operationType = OperationTypeEnum.UPDATE)
    public ResponseDataDTO<Boolean> updateTag(@RequestBody @Valid TagVO tagVO){
        log.info(TAG + "updateTag()");

        boolean result = tagService.updateTag(tagVO); // 调用TagService的方法修改标签
        return new ResponseDataDTO<>(result ? SUCCESS.getCode() : FAIL.getCode(), result, result ? "更新标签成功！" : "已存在该标签！"); // 返回响应数据
    }

    /**
     * 删除标签
     * @param tagId 待删除标签id
     */
    @ApiOperation(value = "删除标签") // Swagger注解，用于给接口添加描述信息
    @DeleteMapping("/{tagId}") // 处理HTTP DELETE请求，并将路径参数tagId映射到方法参数
    @OperationLogSys(description = "删除标签", operationType = OperationTypeEnum.DELETE)
    public ResponseDataDTO<Boolean> deleteTag(@PathVariable Long tagId){
        log.info(TAG + "deleteTag()");
        boolean result = tagService.deleteTagById(tagId); // 调用TagService的方法删除标签
        return new ResponseDataDTO<>(result ? SUCCESS.getCode() : FAIL.getCode(), result); // 返回响应数据
    }
}
