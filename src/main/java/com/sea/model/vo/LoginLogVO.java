package com.sea.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 登录日志记录信息值对象 - 前端新增登录记录时，需要封装的信息
 * @author: sea
 * @date: 2023/12/20 10:52
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "登录日志记录信息值对象")
public class LoginLogVO {
    @ApiModelProperty(name = "id", value = "登录日志记录id", dataType = "Long")
    private Long id;

    @ApiModelProperty(name = "loginName", value = "登录账号用户名", dataType = "String")
    private String loginName;

    @ApiModelProperty(name = "ipAddress", value = "登录ip地址", dataType = "String")
    private String ipAddress;

    @ApiModelProperty(name = "loginLocation", value = "登录地点", dataType = "String")
    private String loginLocation;

    @ApiModelProperty(name = "browserType", value = "浏览器类型", dataType = "String")
    private String browserType;

    @ApiModelProperty(name = "os", value = "操作系统", dataType = "String")
    private String os;

    @ApiModelProperty(name = "loginStatus", value = "登录状态，默认0，0-成功，1-失败", dataType = "Integer")
    private Integer loginStatus;






}
