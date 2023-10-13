package com.sea.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

/**
 * 用户表
 * @author: sea
 * @date: 2023/10/11 15:35
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_user")
public class User {
    private Long id;//用户id
    private String username;//用户名
    private String password;//密码

    @Email(message = "邮箱格式不正确！")
    private String email;//邮箱

    @NotBlank(message = "手机号码不能为空！")
    @Pattern(regexp = "^1[3-9][0-9]{9}", message = "手机号码格式不正确！")
    private String phone;//手机号码

    private String gender;//性别
    private String intro;//个人介绍
    private String avatarUrl;//头像url
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间

}
