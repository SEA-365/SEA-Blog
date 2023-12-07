package com.sea.shiro;

import com.sea.entity.User;
import com.sea.exception.BizException;
import com.sea.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

/**
 * 安全框架shiro中的Realm
 *
 * Realm（领域）： `Realm` 是与安全相关的数据源，它从数据源中获取安全数据，例如用户信息、角色和权限。
 *               Shiro 可以配置多个 Realm，用于从不同的数据源获取安全信息。
 * @author: sea
 * @date: 2023/11/13 20:43
 */
public class MyShiroRealm extends AuthorizingRealm {
    //继承父类AuthorizingRealm，并重写获取用户授权信息与用户身份认证信息的方法

    //日志打印
    public static final Logger log = LoggerFactory.getLogger(MyShiroRealm.class);

    public static final String TAG = "MyShiroRealm ====> ";

    @Resource //此注解用于标注类的属性或方法，表示资源的注入；可以替代 @Autowired 或 @Inject，用于实现依赖注入
    UserService userService;

    /**
     * 获取用户的授权信息
     * @param principalCollection 存放身份信息的集合
     * @return 用户授权信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info(TAG + "获取用户的授权信息：doGetAuthorizationInfo()");
        //1.创建用户验证信息实例对象
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //2.获取主体的主要身份信息
        User user = (User) principalCollection.getPrimaryPrincipal();

        //todo:目前没有实现权限机制，模拟获得admin权限，后续完善！
        //3.从数据源（库）中获取用户的权限信息
        String rolePermission = "[admin]";

        authorizationInfo.addStringPermission(rolePermission);
        return authorizationInfo;
    }

    /**
     * 获取身份验证信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info(TAG + "获取用户的身份验证信息：doGetAuthenticationInfo()");

        //1.获取待验证的用户信息
        String username = (String) authenticationToken.getPrincipal();
        String password = new String((char[]) authenticationToken.getCredentials());
        log.info(TAG + username + " ===== " + password);

        //2.通过username查询数据库
        User user = userService.getUserByUsername(username);

        //3.验证用户名和密码是否匹配
        if(user == null || !manualPasswordCheck(password, user.getPassword())){
            throw new BizException("用户名或密码不正确！");
        }
        //4.验证成功后，构建身份验证信息，包括：用户名【身份信息】，加密的密码【凭证信息】，盐值，Realm名称
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getUsername(),
                user.getPassword(),
                null,
                getName()
        );//内部还有一次验证操作
        log.info(TAG + "验证成功！");

        return authenticationInfo;
    }

    // 手动验证密码的方法
    private boolean manualPasswordCheck(String rawPassword, String hashedPassword) {
        // 在这里使用 BCrypt 进行密码比较
        return new BCryptPasswordEncoder().matches(rawPassword, hashedPassword);
    }
}
