package com.sea.config.mail;

import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;

/**
 * 邮件发送配置类
 * @author: sea
 * @date: 2023/11/12 15:03
 */
@Log4j2
public class SendMailConfig {

    @Value("${send.mail.host}")
    private String host;

    @Value("${send.mail.port}")
    private Integer port;

    @Value("${send.mail.from}")
    private String from;

    @Value("${send.mail.password}")
    private String password;

    public void sendMail(MailInfo mailInfo){
        try{
            MailAccount account = new MailAccount();
            //设置QQ邮箱的SMTP服务器地址
            account.setHost(host);
            //设置邮件服务器端口号
            account.setPort(port);//这个端口号，QQ邮箱一般是465或587
            //设置发送方邮箱地址
            account.setFrom(from);
            //设置发送方的授权码
            account.setPass(password);//qq邮箱授权码
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
