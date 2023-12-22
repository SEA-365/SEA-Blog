package com.sea.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 登录日志记录表
 * @author: sea
 * @date: 2023/12/19 10:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_login_log")
public class LoginLog {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;//登录日志id
    private String loginName;//登录账号用户名
    private String ipAddress;//登录ip地址
    private String loginLocation;//登录地点
    private String browserType;//浏览器类型
    private String os;//操作系统
    private Integer loginStatus;//登录状态，默认0，0-成功，1-失败
    private LocalDateTime createTime;//创建时间

}
