package com.sea.model.vo;

import lombok.Data;

/**
 * 前端发送的用户登录信息值对象
 * @author: sea
 * @date: 2023/11/13 19:02
 */
@Data
public class UserLoginVO {

    private String username;//用户名
    private String password;//密码

}
