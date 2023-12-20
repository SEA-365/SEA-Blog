package com.sea.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.sea.dao.LoginLogDao;
import com.sea.entity.LoginLog;
import com.sea.model.vo.ConditionVO;
import com.sea.model.vo.LoginLogVO;
import com.sea.service.LoginLogService;
import com.sea.util.BeanCopyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: sea
 * @date: 2023/12/20 11:03
 */
@Service
public class LoginLogServiceImpl implements LoginLogService {
    @Autowired // 自动注入LoginLogDao对象
    LoginLogDao loginLogDao;

    //日志打印
    public static final Logger log = LoggerFactory.getLogger(LoginLogDao.class);

    public static final String TAG = "LoginLogServiceImpl ====> ";

    @Override
    public List<LoginLog> getLoginLogList(ConditionVO conditionVO) {
        log.info(TAG + " " + conditionVO);

        PageHelper.startPage(conditionVO.getPageNum(), conditionVO.getPageSize());//设置分页查询参数
        List<LoginLog> loginLogList = loginLogDao.selectList(null); // 此时查询的记录为所有记录
        return loginLogList; // 返回操作日志记录列表
    }

    @Override
    public LoginLog getLoginLogById(Long loginLogId) {
        return loginLogDao.selectById(loginLogId);
    }

    @Override
    public boolean addLoginLog(LoginLogVO loginLogVO) {
        LoginLog loginLog = BeanCopyUtil.copyObject(loginLogVO, LoginLog.class);
        loginLogDao.insert(loginLog); // 添加操作日志
        return true;
    }

    @Override
    public boolean updateLoginLog(LoginLogVO loginLogVO) {
        LoginLog loginLog = BeanCopyUtil.copyObject(loginLogVO, LoginLog.class);
        Long id = loginLog.getId();
        UpdateWrapper<LoginLog> loginLogUpdateWrapper = new UpdateWrapper<>();
        loginLogUpdateWrapper.eq("id", id);
        loginLogDao.update(loginLog, loginLogUpdateWrapper);
        return true;
    }

    @Override
    public boolean deleteLoginLogById(Long loginLogId) {
        loginLogDao.deleteById(loginLogId);
        return true;
    }
}
