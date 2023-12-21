package com.sea.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 操作日志记录信息值对象 - 前端新增操作记录时，需要封装的信息
 * @author: sea
 * @date: 2023/12/18 15:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "操作日志记录信息值对象")
public class OperationLogVO {
    @ApiModelProperty(name = "id", value = "操作日志记录id", dataType = "Long")
    private Long id;

    @ApiModelProperty(name = "operation_ip", value = "主机ip", dataType = "String")
    private String operationIp;

    @ApiModelProperty(name = "operation_location", value = "操作地点", dataType = "String")
    private String operationLocation;

    @ApiModelProperty(name = "method", value = "方法名", dataType = "String")
    private String method;

    @ApiModelProperty(name = "args", value = "方法参数", dataType = "String")
    private String args;

    @NotBlank(message = "操作人不能为空！")
    @ApiModelProperty(name = "operation_name", value = "操作人", dataType = "String")
    private String operationName;

    @NotBlank(message = "操作类型不能为空！")
    @ApiModelProperty(name = "operation_type", value = "操作类型", dataType = "String")
    private String operationType;

    @ApiModelProperty(name = "return_value", value = "返回参数", dataType = "String")
    private String returnValue;






}
