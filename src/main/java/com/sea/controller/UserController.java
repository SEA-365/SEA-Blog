package com.sea.controller;

import com.sea.entity.User;
import com.sea.service.UserService;
import com.sea.util.ResultData;
import com.sea.util.StatusCode;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: sea
 * @date: 2023/10/11 16:14
 */

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    /**
     * 获取所有用户列表
     * @return 用户列表
     */
    @GetMapping
    public ResultData<List<User>> getAllUser(){
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
    @GetMapping("/{userId}")
    public ResultData<User> getUserById(@PathVariable Long userId){
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
    @PostMapping
    public ResultData<Boolean> addUser(@RequestBody User user){
        boolean result = userService.addUser(user);
        return new ResultData<>(result ? StatusCode.SAVE_OK : StatusCode.SAVE_ERROR, result);
    }

    /**
     * 修改用户
     * @param user 新的用户信息
     */
    @PutMapping
    public ResultData<Boolean> updateUser(@RequestBody User user){
        boolean result = userService.updateUser(user);
        return new ResultData<>(result ? StatusCode.UPDATE_OK : StatusCode.UPDATE_ERROR, result);
    }

    /**
     * 删除用户
     * @param userId 待删除用户id
     */
    @DeleteMapping("/{userId}")
    public ResultData<Boolean> deleteUser(@PathVariable Long userId){
        boolean result = userService.deleteUserById(userId);
        return new ResultData<>(result ? StatusCode.DELETE_OK : StatusCode.DELETE_ERROR, result);
    }
}
