package com.sea.controller;

import com.github.pagehelper.PageInfo;
import com.sea.annotation.OperationLogSys;
import com.sea.common.PageRequestApi;
import com.sea.enums.OperationTypeEnum;
import com.sea.model.dto.PageResultDTO;
import com.sea.model.dto.ResponseDataDTO;
import com.sea.model.vo.CategoryVO;
import com.sea.model.vo.ConditionVO;
import com.sea.entity.Category;
import com.sea.service.CategoryService;
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
@Api(tags = "分类信息管理模块")
@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired // 自动注入CategoryService对象
    CategoryService categoryService;
    //日志打印
    private static final Logger log = LoggerFactory.getLogger(CategoryController.class);
    private static final String TAG = "CategoryController ====> ";

    /**
     * 请求指定页的分类信息
     * @return 指定页的分类信息
     */
    @ApiOperation(value = "请求指定页的分类信息") // Swagger注解，用于给接口添加描述信息
    @PostMapping("list") // 需要换成Post请求
    @OperationLogSys(description = "请求指定页的分类信息", operationType = OperationTypeEnum.SELECT)
    public ResponseDataDTO<PageResultDTO> getCategoryPage(@RequestBody @Valid PageRequestApi<ConditionVO> conditionVO){
        log.info(TAG + "getCategoryPage()");
        ResponseDataDTO<PageResultDTO> resultData = new ResponseDataDTO<>(); // 创建响应数据对象

        List<Category> categoryPage = categoryService.getCategoryList(conditionVO.getBody()); // 调用categoryService的方法获取全部分类信息

        PageInfo<Category> categoryPageInfo = new PageInfo<>(categoryPage);//这一步，会计算出相关的参数，例如总页数，总记录数等；
        //log.info(TAG + " " + categoryPageInfo);
        PageResultDTO pageResultDTO = PageUtil.getPageResultDTO(conditionVO.getBody(), categoryPageInfo);//封装数据

        if(categoryPage != null){
            resultData.setStatusCode(SUCCESS.getCode()); // 设置响应状态码
            resultData.setData(pageResultDTO); // 设置响应数据
        }
        else {
            resultData.setStatusCode(FAIL.getCode());
            resultData.setData(pageResultDTO);
            resultData.setMsg("没有查询到指定页面的分类信息，请检查后重试！"); // 设置响应消息
        }
        return resultData; // 返回响应数据
    }

    /**
     * 根据id获取指定分类
     * @param categoryId 分类id
     * @return 指定分类信息
     */
    @ApiOperation(value = "根据id获取指定分类") // Swagger注解，用于给接口添加描述信息
    @GetMapping("/{categoryId}") // 处理HTTP GET请求，并将路径参数categoryId映射到方法参数
    @OperationLogSys(description = "根据id获取指定分类", operationType = OperationTypeEnum.SELECT)
    public ResponseDataDTO<Category> getCategoryById(@PathVariable Long categoryId){
        log.info(TAG + "getCategoryById()");
        ResponseDataDTO<Category> resultData = new ResponseDataDTO<>(); // 创建响应数据对象
        Category category = categoryService.getCategoryById(categoryId); // 调用CategoryService的方法根据id获取分类
        if(category != null){
            resultData.setStatusCode(SUCCESS.getCode()); // 设置响应状态码
            resultData.setData(category); // 设置响应数据
        }
        else {
            resultData.setStatusCode(FAIL.getCode());
            resultData.setData(category);
            resultData.setMsg("查询分类失败，请检查重试！"); // 设置响应消息
        }
        return resultData; // 返回响应数据
    }

    /**
     * 添加分类
     * @param categoryVO 待添加的分类信息
     */
    @ApiOperation(value = "添加分类") // Swagger注解，用于给接口添加描述信息
    @PostMapping // 处理HTTP POST请求
    @OperationLogSys(description = "添加分类", operationType = OperationTypeEnum.INSERT)
    public ResponseDataDTO<Boolean> addCategory(@RequestBody @Valid CategoryVO categoryVO){
        log.info(TAG + "addCategory()");
        boolean result = categoryService.addCategory(categoryVO); // 调用CategoryService的方法添加分类
        return new ResponseDataDTO<>(result ? SUCCESS.getCode() : FAIL.getCode(), result); // 返回响应数据
    }

    /**
     * 修改分类
     * @param categoryVO 新的分类信息
     */
    @ApiOperation(value = "修改分类") // Swagger注解，用于给接口添加描述信息
    @PutMapping // 处理HTTP PUT请求
    @OperationLogSys(description = "修改分类", operationType = OperationTypeEnum.UPDATE)
    public ResponseDataDTO<Boolean> updateCategory(@RequestBody @Valid CategoryVO categoryVO){
        log.info(TAG + "updateCategory()");

        boolean result = categoryService.updateCategory(categoryVO); // 调用CategoryService的方法修改分类
        return new ResponseDataDTO<>(result ? SUCCESS.getCode() : FAIL.getCode(), result); // 返回响应数据
    }

    /**
     * 删除分类
     * @param categoryId 待删除分类id
     */
    @ApiOperation(value = "删除分类") // Swagger注解，用于给接口添加描述信息
    @DeleteMapping("/{categoryId}") // 处理HTTP DELETE请求，并将路径参数categoryId映射到方法参数
    @OperationLogSys(description = "删除分类", operationType = OperationTypeEnum.DELETE)
    public ResponseDataDTO<Boolean> deleteCategory(@PathVariable Long categoryId){
        log.info(TAG + "deleteCategory()");
        boolean result = categoryService.deleteCategoryById(categoryId); // 调用CategoryService的方法删除分类
        return new ResponseDataDTO<>(result ? SUCCESS.getCode() : FAIL.getCode(), result); // 返回响应数据
    }
}
