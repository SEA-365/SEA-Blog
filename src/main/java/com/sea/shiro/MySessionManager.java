package com.sea.shiro;

import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.session.mgt.WebSessionKey;

import java.io.Serializable;

/**
 * Shiro安全框架的会话管理器
 * @author: sea
 * @date: 2023/12/7 11:22
 */
public class MySessionManager extends DefaultSessionManager {
    @Override
    protected Serializable getSessionId(SessionKey sessionKey) {
        Serializable sessionId = super.getSessionId(sessionKey);
        System.out.println("SessionKey: " + sessionKey);
        System.out.println("sessionId: " + sessionId);

        if (sessionKey instanceof WebSessionKey) {
            WebSessionKey webSessionKey = (WebSessionKey) sessionKey;
            System.out.println("ServletRequestURI: " + webSessionKey.getServletRequest().getServerName());
            System.out.println("ServletRequest: " + webSessionKey.getServletRequest());
        }

        return super.getSessionId(sessionKey);
    }
}
