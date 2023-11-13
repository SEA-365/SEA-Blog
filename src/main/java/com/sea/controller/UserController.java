package com.sea.controller;

import com.sea.model.vo.UserVO;
import com.sea.entity.User;
import com.sea.service.UserService;
import com.sea.model.dto.ResultDataDTO;
import static com.sea.enums.StatusCodeEnum.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

/**
 * @author: sea
 * @date: 2023/10/11 16:14
 */
@Api(tags = "用户信息管理模块") // Swagger注解，用于给接口添加标签信息
@RestController // 声明该类是一个控制器
@RequestMapping("/users") // 定义映射路径
public class UserController {
    @Autowired // 自动注入UserService对象
    UserService userService;
    //日志打印
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private static final String TAG = "UserController ====> ";

    /**
     * 获取所有用户列表
     * @return 用户列表
     */
    @ApiOperation(value = "获取所有用户列表") // Swagger注解，用于给接口添加描述信息
    @GetMapping // 处理HTTP GET请求
    public ResultDataDTO<List<User>> getAllUser(){
        log.info(TAG + "getAllUser()");
        ResultDataDTO<List<User>> resultData = new ResultDataDTO<>(); // 创建响应数据对象
        List<User> allUser = userService.getAllUser(); // 调用UserService的方法获取所有用户
        if(allUser != null){
            resultData.setStatusCode(SUCCESS.getCode()); // 设置响应状态码
            resultData.setData(allUser); // 设置响应数据
        }
        else {
            resultData.setStatusCode(FAIL.getCode());
            resultData.setData(allUser);
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
    public ResultDataDTO<User> getUserById(@PathVariable Long userId){
        log.info(TAG + "getUserById()");
        ResultDataDTO<User> resultData = new ResultDataDTO<>(); // 创建响应数据对象
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
    public ResultDataDTO<Boolean> addUser(@RequestBody @Valid UserVO userVO, BindingResult bindingResult){
        log.info(TAG + "addUser()");
        /**
         * 用户信息合法性验证
         */
        if(bindingResult.hasFieldErrors("email")){ // 判断是否存在email字段的验证错误
            String emailError = Objects.requireNonNull(bindingResult.getFieldError("email")).getDefaultMessage();
            return new ResultDataDTO<>(FAIL.getCode(), null, emailError); // 返回带错误信息的响应数据
        }
        else if(bindingResult.hasFieldErrors("phone")){ // 判断是否存在phone字段的验证错误
            String phoneError = Objects.requireNonNull(bindingResult.getFieldError("phone")).getDefaultMessage();
            return new ResultDataDTO<>(FAIL.getCode(), null, phoneError); // 返回带错误信息的响应数据
        }
        else{ // 用户信息合法
            boolean result = userService.addUser(userVO); // 调用UserService的方法添加用户
            return new ResultDataDTO<>(result ? SUCCESS.getCode() : FAIL.getCode(), result); // 返回响应数据
        }
    }

    /**
     * 修改用户
     * @param userVO 新的用户信息
     */
    @ApiOperation(value = "修改用户") // Swagger注解，用于给接口添加描述信息
    @PutMapping // 处理HTTP PUT请求
    public ResultDataDTO<Boolean> updateUser(@RequestBody @Valid UserVO userVO, BindingResult bindingResult){
        log.info(TAG + "updateUser()");
        /**
         * 用户信息合法性验证
         */
        if(bindingResult.hasFieldErrors("email")){ // 判断是否存在email字段的验证错误
            String emailError = Objects.requireNonNull(bindingResult.getFieldError("email")).getDefaultMessage();
            return new ResultDataDTO<>(FAIL.getCode(), null, emailError); // 返回带错误信息的响应数据
        }
        else if(bindingResult.hasFieldErrors("phone")){ // 判断是否存在phone字段的验证错误
            String phoneError = Objects.requireNonNull(bindingResult.getFieldError("phone")).getDefaultMessage();
            return new ResultDataDTO<>(FAIL.getCode(), null, phoneError); // 返回带错误信息的响应数据
        }
        else{ // 用户信息合法
            boolean result = userService.updateUser(userVO); // 调用UserService的方法修改用户
            return new ResultDataDTO<>(result ? SUCCESS.getCode() : FAIL.getCode(), result); // 返回响应数据
        }
    }

    /**
     * 删除用户
     * @param userId 待删除用户id
     */
    @ApiOperation(value = "删除用户") // Swagger注解，用于给接口添加描述信息
    @DeleteMapping("/{userId}") // 处理HTTP DELETE请求，并将路径参数userId映射到方法参数
    public ResultDataDTO<Boolean> deleteUser(@PathVariable Long userId){
        log.info(TAG + "deleteUser()");
        boolean result = userService.deleteUserById(userId); // 调用UserService的方法删除用户
        return new ResultDataDTO<>(result ? SUCCESS.getCode() : FAIL.getCode(), result); // 返回响应数据
    }
}
