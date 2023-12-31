package com.sea.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.sea.model.vo.ConditionVO;
import com.sea.model.vo.UserVO;
import com.sea.dao.UserDao;
import com.sea.entity.User;
import com.sea.service.UserService;
import com.sea.util.BeanCopyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    //日志打印
    public static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    public static final String TAG = "UserServiceImpl ====> ";

    @Override
    public List<User> getUserList(ConditionVO conditionVO) {
        log.info(TAG + " " + conditionVO);

        PageHelper.startPage(conditionVO.getPageNum(), conditionVO.getPageSize());//设置分页查询参数
        List<User> userList = userDao.selectList(null); // 查询所有用户
        return userList; // 返回用户列表
    }

    @Override
    public User getUserById(Long userId) {
        return userDao.selectById(userId); // 根据用户ID查询用户
    }

    @Override
    public boolean addUser(UserVO userVO) {
        // 密码加密存储到数据库
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userVO.setPassword(passwordEncoder.encode(userVO.getPassword())); // 对密码进行加密
        //log.info(TAG + "addUser() ===> " + userVO);
        User user = BeanCopyUtil.copyObject(userVO, User.class);
        //log.info(TAG + "addUser() ===> " + user);
        userDao.insert(user); // 添加用户
        return true;
    }

    @Override
    public boolean updateUser(UserVO userVO) {
        // 密码加密存储到数据库
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userVO.setPassword(passwordEncoder.encode(userVO.getPassword())); // 对密码进行加密
        Long id = userVO.getId();
        User user = BeanCopyUtil.copyObject(userVO, User.class);
        // 使用条件构造器。指定更新条件
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        // 此处第一个参数为列名，第二个参数为值，相当于子句：where id = userVO.id
        userUpdateWrapper.eq("id", id);
        userDao.update(user, userUpdateWrapper); // 更新用户
        return true;
    }

    @Override
    public boolean deleteUserById(Long userId) {
        userDao.deleteById(userId); // 根据用户ID删除用户
        return true;
    }

    @Override
    public User getUserByUsername(String username) {
        if(username == null)
            return null;
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", username);//构造查询条件
        User user = userDao.selectOne(userQueryWrapper);
        return user;
    }
}
