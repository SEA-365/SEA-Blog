package com.sea.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 使得Shiro兼容Spring Security的加密算法
 * @author: sea
 * @date: 2023/11/15 21:10
 */
public class SpringSecurityBCryptMatcher extends HashedCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        // 根据 Spring Security 的 BCrypt 校验逻辑进行匹配
        String rawPassword = new String((char[]) token.getCredentials());
        String hashedPassword = info.getCredentials().toString();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return passwordEncoder.matches(rawPassword, hashedPassword);
    }
}