package com.sea.service;

import com.sea.model.vo.UserVO;
import com.sea.entity.User;

import java.util.List;

/**
 * 用户表业务层接口
 * @author: sea
 * @date: 2023/10/11 15:46
 */
public interface UserService {
    /**
     * 获取所有用户列表
     * @return 用户列表
     */
    List<User> getAllUser();

    /**
     * 根据id获取指定用户
     * @param userId 用户id
     * @return 指定用户信息
     */
    User getUserById(Long userId);

    /**
     * 添加用户
     * @param userVO 待添加的用户信息
     */
    boolean addUser(UserVO userVO);

    /**
     * 修改用户
     * @param userVO 新的用户信息
     */
    boolean updateUser(UserVO userVO);

    /**
     * 删除用户
     * @param userId 待删除用户id
     */
    boolean deleteUserById(Long userId);

    /**
     * 根据用户名获取用户
     * @param username 用户名
     * @return 指定用户
     */
    User getUserByUsername(String username);
}
