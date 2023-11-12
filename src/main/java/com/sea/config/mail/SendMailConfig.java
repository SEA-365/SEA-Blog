package com.sea.config.mail;

import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import com.alibaba.druid.support.json.JSONUtils;
import lombok.extern.log4j.Log4j2;

/**
 * 邮件发送配置类
 * @author: sea
 * @date: 2023/11/12 15:03
 */
@Log4j2
public class SendMailConfig {

    public static void sendMail(MailInfo mailInfo){
        try{
            MailAccount account = new MailAccount();
            //设置QQ邮箱的SMTP服务器地址
            account.setHost("smtp.qq.com");
            //设置邮件服务器端口号
            account.setPort(465);
            //设置发送方邮箱地址
            account.setFrom("2472767932@qq.com");
            //设置发送方的授权码
            account.setPass("aknwcohmuqwodifj");
            //是否启用 SSL 加密
            account.setSslEnable(true);

            //发送邮件
            MailUtil.send(account, mailInfo.getReceiveMail(), mailInfo.getTitle(), mailInfo.getContent(), false);

            log.info("邮件发送成功！" + mailInfo);
        }catch (Exception e){
            log.error("邮件发送失败！" + e);
        }
    }
}
