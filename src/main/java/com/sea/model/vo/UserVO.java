package com.sea.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 用户信息值对象 - 前端新增用户时，需要封装的信息
 * @author: sea
 * @date: 2023/10/19 15:02
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "用户信息对象")
public class UserVO {
    @ApiModelProperty(name = "id", value = "用户id", dataType = "Long")
    private Long id;//用户id

    @NotBlank(message = "用户名不能为空！")
    @ApiModelProperty(name = "username", value = "用户名", dataType = "String")
    private String username;//用户名

    @NotBlank(message = "密码不能为空！")
    @ApiModelProperty(name = "password", value = "密码", dataType = "String")
    private String password;//密码

    @Email(message = "邮箱格式不正确！")
    @ApiModelProperty(name = "email", value = "邮箱", dataType = "String")
    private String email;//邮箱

    @NotBlank(message = "手机号码不能为空！")
    @Pattern(regexp = "^1[3-9][0-9]{9}", message = "手机号码格式不正确！")
    @ApiModelProperty(name = "phone", value = "手机号码", dataType = "String")
    private String phone;//手机号码

    private String gender;//性别
    private String intro;//个人介绍

    @ApiModelProperty(name = "avatarUrl", value = "头像Url", dataType = "String")
    private String avatarUrl;//头像url

    private Integer loginStatus;//登录状态
}
