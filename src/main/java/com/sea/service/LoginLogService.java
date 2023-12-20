package com.sea.service;

import com.sea.entity.LoginLog;
import com.sea.model.vo.ConditionVO;
import com.sea.model.vo.LoginLogVO;

import java.util.List;

/**
 * 登录日志记录表业务层接口
 * @author: sea
 * @date: 2023/12/20 11:00
 */

public interface LoginLogService {
    /**
     * 获取登录日志记录信息列表
     * @return 指定页的登录日志记录信息
     */
    public List<LoginLog> getLoginLogList(ConditionVO conditionVO);

    /**
     * 根据id获取指定登录日志记录信息
     * @param loginLogId 登录日志记录id
     * @return 指定登录日志记录信息
     */
    LoginLog getLoginLogById(Long loginLogId);

    /**
     * 添加登录日志记录
     * @param loginLogVO 待添加的登录日志记录信息
     */
    boolean addLoginLog(LoginLogVO loginLogVO);

    /**
     * 修改登录日志记录
     * @param loginLogVO 新的登录日志记录信息
     */
    boolean updateLoginLog(LoginLogVO loginLogVO);

    /**
     * 删除登录日志记录
     * @param loginLogId 待删除登录日志记录id
     */
    boolean deleteLoginLogById(Long loginLogId);

}
