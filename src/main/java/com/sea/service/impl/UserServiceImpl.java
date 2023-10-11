package com.sea.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.sea.dao.UserDao;
import com.sea.entity.User;
import com.sea.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: sea
 * @date: 2023/10/11 15:59
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;
    @Override
    public List<User> getAllUser() {
        List<User> userList = userDao.selectList(null);
        return userList;
    }

    @Override
    public User getUserById(Long userId) {
        return userDao.selectById(userId);
    }

    @Override
    public boolean addUser(User user) {
        userDao.insert(user);
        return true;
    }

    @Override
    public boolean updateUser(User user) {
        Long id = user.getId();
        //使用条件构造器。指定更新条件
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        //此处第一个参数为列名，第二个参数为值，相当于子句：where id = user.id
        userUpdateWrapper.eq("id", id);
        userDao.update(user, userUpdateWrapper);
        return true;
    }

    @Override
    public boolean deleteUserById(Long userId) {
        userDao.deleteById(userId);
        return true;
    }
}
