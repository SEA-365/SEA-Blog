package com.sea.controller;

import com.github.pagehelper.PageInfo;
import com.sea.annotation.OperationLogSys;
import com.sea.common.PageRequestApi;
import com.sea.entity.LoginLog;
import com.sea.enums.OperationTypeEnum;
import com.sea.model.dto.PageResultDTO;
import com.sea.model.dto.ResponseDataDTO;
import com.sea.model.vo.ConditionVO;
import com.sea.service.LoginLogService;
import com.sea.util.PageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static com.sea.enums.StatusCodeEnum.FAIL;
import static com.sea.enums.StatusCodeEnum.SUCCESS;

/**
 * @author: sea
 * @date: 2023/12/21 16:22
 */
@Api(tags = "登录日志管理模块")
@RestController
@RequestMapping("/loginLog")
public class LoginLogController {
    @Autowired
    LoginLogService loginLogService;

    //日志打印
    private static final Logger log = LoggerFactory.getLogger(LoginLogController.class);
    private static final String TAG = "LoginLogController ====> ";


    /**
     * 请求指定页登录日志列表
     * @param conditionVO 请求参数
     * @return 分页查询信息列表
     */
    @ApiOperation(value = "请求指定页登录日志列表")
    @PostMapping("/list")
    @OperationLogSys(description = "请求指定页登录日志列表", operationType = OperationTypeEnum.SELECT)
    public ResponseDataDTO<PageResultDTO> getLoginLogByPage(@RequestBody @Valid PageRequestApi<ConditionVO> conditionVO){
        log.info(TAG + "getLoginLogByPage()");
        ResponseDataDTO<PageResultDTO> resultData = new ResponseDataDTO<>();

        List<LoginLog> loginLogListByPage = loginLogService.getLoginLogList(conditionVO.getBody());

        PageInfo<LoginLog> loginLogPageInfo = new PageInfo<>(loginLogListByPage);
        PageResultDTO pageResultDTO = PageUtil.getPageResultDTO(conditionVO.getBody(), loginLogPageInfo);

        if(loginLogListByPage != null){
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

}
