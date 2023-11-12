package com.sea.config.mail;

import lombok.Builder;
import lombok.Data;

/**
 * 邮件接收配置信息
 * @author: sea
 * @date: 2023/11/12 15:03
 */

@Builder
@Data
public class MailInfo {
    //接收方邮箱
    private String receiveMail;

    //邮件标题
    private String title;

    //邮件内容
    private String content;
}
