package com.sea.controller;

import com.sea.annotation.OperationLogSys;
import com.sea.enums.OperationTypeEnum;
import com.sea.model.dto.ResponseDataDTO;
import com.sea.model.vo.StatisticsCountVO;
import com.sea.service.StatisticsCountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.sea.enums.StatusCodeEnum.SUCCESS;

/**
 * @author: sea
 * @date: 2024/1/18 15:26
 */
@Api(tags = "数据统计面板")
@RestController
@RequestMapping("/dashboard")
public class DashboardController {
    @Autowired
    StatisticsCountService statisticsCountService;

    //日志打印
    private static final Logger log = LoggerFactory.getLogger(DashboardController.class);
    private static final String TAG = "DashboardController ====> ";

    /**
     * 获取统计数据
     */
    @ApiOperation(value = "获取统计数据") // Swagger注解，用于给接口添加描述信息
    @GetMapping("/getPanelCount") // 需要换成Post请求
    @OperationLogSys(description = "获取统计数据", operationType = OperationTypeEnum.SELECT)
    public ResponseDataDTO<StatisticsCountVO> getPanelCount(){
        StatisticsCountVO statistics = statisticsCountService.getPanelCount();
        return new ResponseDataDTO<>(SUCCESS.getCode(), statistics, "获取统计数据成功！");
    }

}
