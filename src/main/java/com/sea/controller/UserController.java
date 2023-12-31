package com.sea.controller;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.github.pagehelper.PageInfo;
import com.sea.annotation.OperationLogSys;
import com.sea.common.PageRequestApi;
import com.sea.entity.User;
import com.sea.enums.OperationTypeEnum;
import com.sea.model.dto.PageResultDTO;
import com.sea.model.vo.ConditionVO;
import com.sea.model.vo.LoginLogVO;
import com.sea.model.vo.UserLoginVO;
import com.sea.model.vo.UserVO;
import com.sea.service.LoginLogService;
import com.sea.service.UserService;
import com.sea.model.dto.ResponseDataDTO;
import static com.sea.enums.StatusCodeEnum.*;

import com.sea.util.IpUtil;
import com.sea.util.PageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author: sea
 * @date: 2023/10/11 16:14
 */
@Api(tags = "用户信息管理模块") // Swagger注解，用于给接口添加标签信息
@RestController // 声明该类是一个控制器
//@CrossOrigin(origins = "http://localhost:7070")
@RequestMapping("/users") // 定义映射路径
public class UserController {
    @Autowired // 自动注入UserService对象
    UserService userService;

    @Autowired // 自动注入LoginLogService对象
    LoginLogService loginLogService;

    //日志打印
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private static final String TAG = "UserController ====> ";

    /**
     * 获取指定页用户列表
     * @return 用户列表
     */
    @ApiOperation(value = "获取指定页用户列表") // Swagger注解，用于给接口添加描述信息
    @PostMapping("/list")// 需要使用Post请求
    @OperationLogSys(description = "获取指定页用户列表", operationType = OperationTypeEnum.SELECT)
    public ResponseDataDTO<PageResultDTO> getUserList(@RequestBody PageRequestApi<ConditionVO> conditionVO){
        log.info(TAG + "getUserList()" + conditionVO.getBody());
        ResponseDataDTO<PageResultDTO> resultData = new ResponseDataDTO<>(); // 创建响应数据对象

        List<User> userPage = userService.getUserList(conditionVO.getBody()); // 调用userService的方法获取全部评论信息

        PageInfo<User> userPageInfo = new PageInfo<>(userPage);//这一步，会计算出相关的参数，例如总页数，总记录数等；
        //log.info(TAG + " " + userPageInfo);
        PageResultDTO pageResultDTO = PageUtil.getPageResultDTO(conditionVO.getBody(), userPageInfo);//封装数据
        if(userPageInfo != null){
            resultData.setStatusCode(SUCCESS.getCode()); // 设置响应状态码
            resultData.setData(pageResultDTO); // 设置响应数据
        }
        else {
            resultData.setStatusCode(FAIL.getCode());
            resultData.setData(pageResultDTO);
            resultData.setMsg("没有查询到用户列表，请检查后重试！"); // 设置响应消息
        }
        return resultData; // 返回响应数据
    }

    /**
     * 根据id获取指定用户
     * @param userId 用户id
     * @return 指定用户信息
     */
    @ApiOperation(value = "根据id获取指定用户") // Swagger注解，用于给接口添加描述信息
    @GetMapping("/{userId}") // 处理HTTP GET请求，并将路径参数userId映射到方法参数
    @OperationLogSys(description = "根据id获取指定用户", operationType = OperationTypeEnum.SELECT)
    public ResponseDataDTO<User> getUserById(@PathVariable Long userId){
        log.info(TAG + "getUserById()");
        ResponseDataDTO<User> resultData = new ResponseDataDTO<>(); // 创建响应数据对象
        User user = userService.getUserById(userId); // 调用UserService的方法根据id获取用户
        if(user != null){
            resultData.setStatusCode(SUCCESS.getCode()); // 设置响应状态码
            resultData.setData(user); // 设置响应数据
        }
        else {
            resultData.setStatusCode(FAIL.getCode());
            resultData.setData(user);
            resultData.setMsg("查询用户失败，请检查重试！"); // 设置响应消息
        }
        return resultData; // 返回响应数据
    }

    /**
     * 添加用户
     * @param userVO 待添加的用户信息
     */
    @ApiOperation(value = "添加用户") // Swagger注解，用于给接口添加描述信息
    @PostMapping // 处理HTTP POST请求
    @OperationLogSys(description = "添加用户", operationType = OperationTypeEnum.INSERT)
    public ResponseDataDTO<Boolean> addUser(@RequestBody @Valid UserVO userVO, BindingResult bindingResult){
        log.info(TAG + "addUser()");
        /**
         * 用户信息合法性验证
         */
        if(bindingResult.hasFieldErrors("email")){ // 判断是否存在email字段的验证错误
            String emailError = Objects.requireNonNull(bindingResult.getFieldError("email")).getDefaultMessage();
            return new ResponseDataDTO<>(FAIL.getCode(), null, emailError); // 返回带错误信息的响应数据
        }
        else if(bindingResult.hasFieldErrors("phone")){ // 判断是否存在phone字段的验证错误
            String phoneError = Objects.requireNonNull(bindingResult.getFieldError("phone")).getDefaultMessage();
            return new ResponseDataDTO<>(FAIL.getCode(), null, phoneError); // 返回带错误信息的响应数据
        }
        else{ // 用户信息合法
            boolean result = userService.addUser(userVO); // 调用UserService的方法添加用户
            return new ResponseDataDTO<>(result ? SUCCESS.getCode() : FAIL.getCode(), result); // 返回响应数据
        }
    }

    /**
     * 修改用户
     * @param userVO 新的用户信息
     */
    @ApiOperation(value = "修改用户") // Swagger注解，用于给接口添加描述信息
    @PutMapping // 处理HTTP PUT请求
    @OperationLogSys(description = "修改用户", operationType = OperationTypeEnum.UPDATE)
    public ResponseDataDTO<Boolean> updateUser(@RequestBody @Valid UserVO userVO, BindingResult bindingResult){
        log.info(TAG + "updateUser()");
        /**
         * 用户信息合法性验证
         */
        if(bindingResult.hasFieldErrors("email")){ // 判断是否存在email字段的验证错误
            String emailError = Objects.requireNonNull(bindingResult.getFieldError("email")).getDefaultMessage();
            return new ResponseDataDTO<>(FAIL.getCode(), null, emailError); // 返回带错误信息的响应数据
        }
        else if(bindingResult.hasFieldErrors("phone")){ // 判断是否存在phone字段的验证错误
            String phoneError = Objects.requireNonNull(bindingResult.getFieldError("phone")).getDefaultMessage();
            return new ResponseDataDTO<>(FAIL.getCode(), null, phoneError); // 返回带错误信息的响应数据
        }
        else{ // 用户信息合法
            boolean result = userService.updateUser(userVO); // 调用UserService的方法修改用户
            return new ResponseDataDTO<>(result ? SUCCESS.getCode() : FAIL.getCode(), result); // 返回响应数据
        }
    }

    /**
     * 删除用户
     * @param userId 待删除用户id
     */
    @ApiOperation(value = "删除用户") // Swagger注解，用于给接口添加描述信息
    @DeleteMapping("/{userId}") // 处理HTTP DELETE请求，并将路径参数userId映射到方法参数
    @OperationLogSys(description = "删除用户", operationType = OperationTypeEnum.DELETE)
    public ResponseDataDTO<Boolean> deleteUser(@PathVariable Long userId){
        log.info(TAG + "deleteUser()");
        boolean result = userService.deleteUserById(userId); // 调用UserService的方法删除用户
        return new ResponseDataDTO<>(result ? SUCCESS.getCode() : FAIL.getCode(), result); // 返回响应数据
    }

    /**
     * 用户登录
     * @param userLoginVO 前端发送的用户登录信息
     * @return 返回请求登录结果
     */
    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    @OperationLogSys(description = "用户登录", operationType = OperationTypeEnum.LOGIN)
    public ResponseDataDTO<Object> login(@RequestBody UserLoginVO userLoginVO){
        log.info(TAG + "用户 " + userLoginVO.getUsername() + " 请求登录！");

        //1.获得当前用户的登录对象【未认证】
        Subject subject = SecurityUtils.getSubject();

        //2.获得待验证的用户名密码
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userLoginVO.getUsername(), userLoginVO.getPassword(), false);

        //3.进行身份验证
        try {
            subject.login(usernamePasswordToken);
            log.info(TAG + "subject.isAuthenticated() ==> " + subject.isAuthenticated());

            HashMap<String, Object> responseData = new HashMap<>();
            responseData.put("token", subject.getSession().getId());
            log.info(TAG + "{} login success!", userLoginVO.getUsername());

            subject.getSession().setAttribute("username", userLoginVO.getUsername());
            subject.getSession().setAttribute("password", userLoginVO.getPassword());

            log.info(TAG + "login() + username: " + subject.getSession().getAttribute("username"));
            log.info(TAG + "login() + password: " + subject.getSession().getAttribute("password"));

            saveLoginLog(userLoginVO, 0);

            return new ResponseDataDTO<>(SUCCESS.getCode(), responseData, "登录成功!");
        } catch (IncorrectCredentialsException e) {
            log.info("login fail: " + e.getMessage());
            return new ResponseDataDTO<>(NO_LOGIN.getCode(), "未登录成功，检查密码是否正确！！");
        } catch (LockedAccountException e) {
            log.info("login fail: " + e.getMessage());
            return new ResponseDataDTO<>(SYSTEM_ERROR.getCode(), "登录异常！！");
        } catch (AuthenticationException e) {
            log.info("login fail: " + e.getMessage());
            return new ResponseDataDTO<>(USERNAME_NOT_EXIST.getCode(), "用户名或密码不存在！！");
        } catch (Exception e) {
            log.info("login fail: " + e.getMessage() + "   ");
            e.printStackTrace();
            saveLoginLog(userLoginVO, 1);
            return new ResponseDataDTO<>(FAIL.getCode(), "其他错误！！");
        }
    }

    /**
     * 获取用户登录信息
     * @return 用户登录信息
     */
    @ApiOperation(value = "获取用户登录信息")
    @GetMapping("/info")
    @OperationLogSys(description = "获取用户登录信息", operationType = OperationTypeEnum.SELECT)
    public ResponseDataDTO<Object> info(){
        log.info(TAG + "获取用户登录信息");
        //存放用户信息
        Map<String, Object> responseData = new HashMap<>(3);

        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getSession().getAttribute("username");

        Session session = subject.getSession();

        log.info(TAG + "info() + username: " + session.getAttribute("username"));
        log.info(TAG + "info() + password: " + session.getAttribute("password"));

        log.info(TAG + "subject.isAuthenticated() ==> " + subject.isAuthenticated());
//        if(subject.isAuthenticated()){
        if (username != null) {
            String principal = (String) subject.getPrincipal();
            log.info(TAG + "user: " + principal);

            responseData.put("roles", "[admin]");//todo：先写死，后续增加权限机制
            responseData.put("id", userService.getUserByUsername(principal).getId().toString());//todo：先写死，后续增加权限机制
            responseData.put("username", principal);
            responseData.put("avatar", userService.getUserByUsername(principal).getAvatarUrl());
            return new ResponseDataDTO<>(SUCCESS.getCode(), responseData);
        }
        else{
            return new ResponseDataDTO<>(FAIL.getCode(), "用户未登录或身份未认证！");
        }
    }

    /**
     * 登出操作
     */
    @ApiOperation(value = "登出操作")
    @RequestMapping("/logout")
    @OperationLogSys(description = "登出操作", operationType = OperationTypeEnum.LOGOUT)
    public ResponseDataDTO<Object> logout(){
        log.info(TAG + "users/logout: 登出操作");
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated())
            subject.logout();
        else
            return new ResponseDataDTO<>(FAIL.getCode(), TAG + " /users/logout: " + ((User)subject.getPrincipal()).getUsername() + " 未登录/未认证！");
        return new ResponseDataDTO<>(SUCCESS.getCode(), "登出成功！");
    }

    /**
     * 未登录/登录错误重定向到这个接口
     */
    @ApiOperation(value = "未登录/登录错误重定向到这个接口")
    @RequestMapping("/unLogin")
    @OperationLogSys(description = "未登录/登录错误重定向到这个接口", operationType = OperationTypeEnum.SYSTEM)
    public ResponseDataDTO<Object> unLogin(){
        log.info(TAG + "未登录/登录错误重定向到这个接口");
        return new ResponseDataDTO<>(NO_LOGIN.getCode(), NO_LOGIN.getDescription());
    }

    /**
     * 无权限时重定向到这个接口
     */
    @ApiOperation(value = "无权限时重定向到这个接口")
    @RequestMapping("/unAuth")
    @OperationLogSys(description = "无权限时重定向到这个接口", operationType = OperationTypeEnum.SYSTEM)
    public ResponseDataDTO<Object> unAuth(){
        log.info(TAG + "无权限时重定向到这个接口");
        return new ResponseDataDTO<>(AUTHORIZED.getCode(), AUTHORIZED.getDescription());
    }

    public void saveLoginLog(UserLoginVO userLoginVO, Integer status){
        // 获取当前线程的请求属性RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从请求属性中解析出 HttpServletRequest 对象，通常用于获取 HTTP 请求相关的信息，如请求头、请求参数等。
        HttpServletRequest httpServletRequest = (HttpServletRequest) Objects.requireNonNull(requestAttributes).resolveReference(RequestAttributes.REFERENCE_REQUEST);

        assert httpServletRequest != null;
        UserAgent userAgent = UserAgentUtil.parse(httpServletRequest.getHeader("User-Agent"));

        LoginLogVO loginLogVO = new LoginLogVO();

        loginLogVO.setLoginName(userLoginVO.getUsername());

        String ipAddr = IpUtil.getIpAddr(httpServletRequest);
        loginLogVO.setIpAddress(ipAddr);
        loginLogVO.setLoginLocation(IpUtil.getIpInfo(ipAddr));

        loginLogVO.setBrowserType(userAgent.getBrowser().getName());

        loginLogVO.setOs(userAgent.getOs().getName());

        loginLogVO.setLoginStatus(status);

        loginLogService.addLoginLog(loginLogVO);
    }


}
