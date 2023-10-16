package com.sea.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.sea.dao.UserDao;
import com.sea.entity.User;
import com.sea.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: sea
 * @date: 2023/10/11 15:59
 */
@Service // 表示这是一个服务类
public class UserServiceImpl implements UserService {
    @Autowired // 自动注入UserDao对象
    UserDao userDao;

    @Override
    public List<User> getAllUser() {
        List<User> userList = userDao.selectList(null); // 查询所有用户
        return userList; // 返回用户列表
    }

    @Override
    public User getUserById(Long userId) {
        return userDao.selectById(userId); // 根据用户ID查询用户
    }

    @Override
    public boolean addUser(User user) {
        // 密码加密存储到数据库
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword())); // 对密码进行加密
        userDao.insert(user); // 添加用户
        return true;
    }

    @Override
    public boolean updateUser(User user) {
        // 密码加密存储到数据库
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword())); // 对密码进行加密
        Long id = user.getId();
        // 使用条件构造器。指定更新条件
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        // 此处第一个参数为列名，第二个参数为值，相当于子句：where id = user.id
        userUpdateWrapper.eq("id", id);
        userDao.update(user, userUpdateWrapper); // 更新用户
        return true;
    }

    @Override
    public boolean deleteUserById(Long userId) {
        userDao.deleteById(userId); // 根据用户ID删除用户
        return true;
    }
}
