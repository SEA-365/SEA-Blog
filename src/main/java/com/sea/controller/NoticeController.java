package com.sea.controller;

import com.github.pagehelper.PageInfo;
import com.sea.annotation.OperationLogSys;
import com.sea.common.PageRequestApi;
import com.sea.entity.Notice;
import com.sea.enums.OperationTypeEnum;
import com.sea.model.dto.PageResultDTO;
import com.sea.model.dto.ResponseDataDTO;
import com.sea.model.vo.NoticeVO;
import com.sea.model.vo.ConditionVO;
import com.sea.service.NoticeService;
import com.sea.util.PageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.sea.enums.StatusCodeEnum.FAIL;
import static com.sea.enums.StatusCodeEnum.SUCCESS;

/**
 * @author: sea
 * @date: 2023/12/27 18:42
 */
@Api(tags = "通知公告信息管理模块")
@RestController
@RequestMapping("/notices")
public class NoticeController {
    @Autowired
    NoticeService noticeService;

    //日志打印
    private static final Logger log = LoggerFactory.getLogger(NoticeController.class);
    private static final String TAG = "NoticeController ====> ";

    /**
     * 请求指定页的公告信息
     * @return 指定页的公告信息
     */
    @ApiOperation(value = "请求指定页的公告信息") // Swagger注解，用于给接口添加描述信息
    @PostMapping("list") // 需要换成Post请求
    @OperationLogSys(description = "请求指定页的公告信息", operationType = OperationTypeEnum.SELECT)
    public ResponseDataDTO<PageResultDTO> getNoticePage(@RequestBody @Valid PageRequestApi<ConditionVO> conditionVO){
        log.info(TAG + "getNoticePage()");
        ResponseDataDTO<PageResultDTO> resultData = new ResponseDataDTO<>(); // 创建响应数据对象

        List<Notice> noticePage = noticeService.getNoticeList(conditionVO.getBody()); // 调用noticeService的方法获取全部公告信息

        PageInfo<Notice> noticePageInfo = new PageInfo<>(noticePage);//这一步，会计算出相关的参数，例如总页数，总记录数等；
        //log.info(TAG + " " + noticePageInfo);
        PageResultDTO pageResultDTO = PageUtil.getPageResultDTO(conditionVO.getBody(), noticePageInfo);//封装数据

        if(noticePage != null){
            resultData.setStatusCode(SUCCESS.getCode()); // 设置响应状态码
            resultData.setData(pageResultDTO); // 设置响应数据
        }
        else {
            resultData.setStatusCode(FAIL.getCode());
            resultData.setData(pageResultDTO);
            resultData.setMsg("没有查询到指定页面的公告信息，请检查后重试！"); // 设置响应消息
        }
        return resultData; // 返回响应数据
    }

    /**
     * 根据id获取指定公告
     * @param noticeId 公告id
     * @return 指定公告信息
     */
    @ApiOperation(value = "根据id获取指定公告") // Swagger注解，用于给接口添加描述信息
    @GetMapping("/{noticeId}") // 处理HTTP GET请求，并将路径参数noticeId映射到方法参数
    @OperationLogSys(description = "根据id获取指定分类", operationType = OperationTypeEnum.SELECT)
    public ResponseDataDTO<Notice> getNoticeById(@PathVariable Long noticeId){
        log.info(TAG + "getNoticeById()");
        ResponseDataDTO<Notice> resultData = new ResponseDataDTO<>(); // 创建响应数据对象
        Notice notice = noticeService.getNoticeById(noticeId); // 调用NoticeService的方法根据id获取公告
        if(notice != null){
            resultData.setStatusCode(SUCCESS.getCode()); // 设置响应状态码
            resultData.setData(notice); // 设置响应数据
        }
        else {
            resultData.setStatusCode(FAIL.getCode());
            resultData.setData(notice);
            resultData.setMsg("查询公告失败，请检查重试！"); // 设置响应消息
        }
        return resultData; // 返回响应数据
    }

    /**
     * 添加公告
     * @param noticeVO 待添加的公告信息
     */
    @ApiOperation(value = "添加公告") // Swagger注解，用于给接口添加描述信息
    @PostMapping // 处理HTTP POST请求
    @OperationLogSys(description = "添加公告", operationType = OperationTypeEnum.INSERT)
    public ResponseDataDTO<Boolean> addNotice(@RequestBody @Valid NoticeVO noticeVO){
        log.info(TAG + "addNotice()");
        boolean result = noticeService.addNotice(noticeVO); // 调用NoticeService的方法添加公告
        return new ResponseDataDTO<>(result ? SUCCESS.getCode() : FAIL.getCode(), result); // 返回响应数据
    }

    /**
     * 删除公告
     * @param noticeId 待删除公告id
     */
    @ApiOperation(value = "删除公告-逻辑删除") // Swagger注解，用于给接口添加描述信息
    @DeleteMapping("/{noticeId}") // 处理HTTP DELETE请求，并将路径参数noticeId映射到方法参数
    @OperationLogSys(description = "删除公告-逻辑删除", operationType = OperationTypeEnum.DELETE)
    public ResponseDataDTO<Boolean> deleteNotice(@PathVariable Long noticeId){
        log.info(TAG + "deleteNotice()");
        boolean result = noticeService.deleteNoticeById(noticeId); // 调用NoticeService的方法删除公告
        return new ResponseDataDTO<>(result ? SUCCESS.getCode() : FAIL.getCode(), result); // 返回响应数据
    }

    /**
     * 修改公告
     * @param noticeVO 新的公告信息
     */
    @ApiOperation(value = "修改公告") // Swagger注解，用于给接口添加描述信息
    @PutMapping // 处理HTTP PUT请求
    @OperationLogSys(description = "修改公告", operationType = OperationTypeEnum.UPDATE)
    public ResponseDataDTO<Boolean> updateNotice(@RequestBody @Valid NoticeVO noticeVO){
        log.info(TAG + "updateNotice()");

        boolean result = noticeService.updateNotice(noticeVO); // 调用NoticeService的方法修改公告
        return new ResponseDataDTO<>(result ? SUCCESS.getCode() : FAIL.getCode(), result, result ? "更新公告成功！" : "已存在该公告！"); // 返回响应数据
    }


}
