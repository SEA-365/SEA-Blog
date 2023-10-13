package com.sea.controller;

import com.sea.entity.User;
import com.sea.service.UserService;
import com.sea.util.ResultData;
import com.sea.util.StatusCode;
import com.sun.org.apache.xpath.internal.operations.Bool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

/**
 * @author: sea
 * @date: 2023/10/11 16:14
 */
@Api(tags = "用户信息管理模块")
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    //日志打印
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private static final String TAG = "UserController ====> ";

    /**
     * 获取所有用户列表
     * @return 用户列表
     */
    @ApiOperation(value = "获取所有用户列表")
    @GetMapping
    public ResultData<List<User>> getAllUser(){
        log.info(TAG + "getAllUser()");
        ResultData<List<User>> resultData = new ResultData<>();
        List<User> allUser = userService.getAllUser();
        if(allUser != null){
            resultData.setStatusCode(StatusCode.SELECT_OK);
            resultData.setData(allUser);
        }
        else {
            resultData.setStatusCode(StatusCode.SELECT_ERROR);
            resultData.setData(allUser);
            resultData.setMsg("没有查询到用户列表，请检查后重试！");
        }
        return resultData;
    }

    /**
     * 根据id获取指定用户
     * @param userId 用户id
     * @return 指定用户信息
     */
    @ApiOperation(value = "根据id获取指定用户")
    @GetMapping("/{userId}")
    public ResultData<User> getUserById(@PathVariable Long userId){
        log.info(TAG + "getUserById()");
        ResultData<User> resultData = new ResultData<>();
        User user = userService.getUserById(userId);
        if(user != null){
            resultData.setStatusCode(StatusCode.SELECT_OK);
            resultData.setData(user);
        }
        else {
            resultData.setStatusCode(StatusCode.SELECT_ERROR);
            resultData.setData(user);
            resultData.setMsg("查询用户失败，请检查重试！");
        }
        return resultData;
    }

    /**
     * 添加用户
     * @param user 待添加的用户信息
     */
    @ApiOperation(value = "添加用户")
    @PostMapping
    public ResultData<Boolean> addUser(@RequestBody @Valid User user, BindingResult bindingResult){
        log.info(TAG + "addUser()");

        /**
         * 用户信息合法性验证
         */
        if(bindingResult.hasFieldErrors("email")){//邮箱格式合法性验证未通过
            String emailError = Objects.requireNonNull(bindingResult.getFieldError("email")).getDefaultMessage();
            return new ResultData<>(StatusCode.SAVE_ERROR, null, emailError);
        }
        else if(bindingResult.hasFieldErrors("phone")){//手机号码合法性验证未通过
            String phoneError = Objects.requireNonNull(bindingResult.getFieldError("phone")).getDefaultMessage();
            return new ResultData<>(StatusCode.SAVE_ERROR, null, phoneError);
        }
        else{//用户信息合法
            boolean result = userService.addUser(user);
            return new ResultData<>(result ? StatusCode.SAVE_OK : StatusCode.SAVE_ERROR, result);
        }
    }

    /**
     * 修改用户
     * @param user 新的用户信息
     */
    @ApiOperation(value = "修改用户")
    @PutMapping
    public ResultData<Boolean> updateUser(@RequestBody @Valid User user, BindingResult bindingResult){
        log.info(TAG + "updateUser()");

        /**
         * 用户信息合法性验证
         */
        if(bindingResult.hasFieldErrors("email")){//邮箱格式合法性验证未通过
            String emailError = Objects.requireNonNull(bindingResult.getFieldError("email")).getDefaultMessage();
            return new ResultData<>(StatusCode.UPDATE_ERROR, null, emailError);
        }
        else if(bindingResult.hasFieldErrors("phone")){//手机号码合法性验证未通过
            String phoneError = Objects.requireNonNull(bindingResult.getFieldError("phone")).getDefaultMessage();
            return new ResultData<>(StatusCode.UPDATE_ERROR, null, phoneError);
        }
        else{//用户信息合法
            boolean result = userService.updateUser(user);
            return new ResultData<>(result ? StatusCode.UPDATE_OK : StatusCode.UPDATE_ERROR, result);
        }

    }

    /**
     * 删除用户
     * @param userId 待删除用户id
     */
    @ApiOperation(value = "删除用户")
    @DeleteMapping("/{userId}")
    public ResultData<Boolean> deleteUser(@PathVariable Long userId){
        log.info(TAG + "deleteUser()");
        boolean result = userService.deleteUserById(userId);
        return new ResultData<>(result ? StatusCode.DELETE_OK : StatusCode.DELETE_ERROR, result);
    }
}
