package com.sea.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    Long id;//用户id
    String username;//用户名
    String password;//密码
    String email;//邮箱
    Long phone;//手机号码
    String gender;//性别
    String intro;//个人介绍
    String avatarUrl;//头像url
    LocalDateTime createTime;//创建时间
    LocalDateTime updateTime;//更新时间


}
