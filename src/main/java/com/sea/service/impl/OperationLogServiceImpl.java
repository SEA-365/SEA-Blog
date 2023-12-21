package com.sea.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.sea.dao.OperationLogDao;
import com.sea.entity.Category;
import com.sea.entity.OperationLog;
import com.sea.model.vo.ConditionVO;
import com.sea.model.vo.OperationLogVO;
import com.sea.service.OperationLogService;
import com.sea.util.BeanCopyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: sea
 * @date: 2023/12/18 16:09
 */
@Service
public class OperationLogServiceImpl implements OperationLogService {
    @Autowired // 自动注入OperationLogDao对象
    OperationLogDao operationLogDao;

    //日志打印
    public static final Logger log = LoggerFactory.getLogger(OperationLogDao.class);

    public static final String TAG = "OperationLogServiceImpl ====> ";

    @Override
    public List<OperationLog> getOperationLogList(ConditionVO conditionVO) {
        log.info(TAG + " " + conditionVO);

        PageHelper.startPage(conditionVO.getPageNum(), conditionVO.getPageSize());//设置分页查询参数
        List<OperationLog> operationLogList = operationLogDao.selectList(null); // 此时查询的记录为所有记录
        log.info(TAG + " 查询到的全部数据：" + operationLogList);
        return operationLogList; // 返回操作日志记录列表
    }

    @Override
    public OperationLog getOperationLogById(Long operationLogId) {
        return operationLogDao.selectById(operationLogId);
    }

    @Override
    public boolean addOperationLog(OperationLogVO operationLogVO) {
        OperationLog operationLog = BeanCopyUtil.copyObject(operationLogVO, OperationLog.class);
        operationLogDao.insert(operationLog); // 添加操作日志
        return true;
    }

    @Override
    public boolean updateOperationLog(OperationLogVO operationLogVO) {
        OperationLog operationLog = BeanCopyUtil.copyObject(operationLogVO, OperationLog.class);
        Long id = operationLog.getId();
        UpdateWrapper<OperationLog> operationLogUpdateWrapper = new UpdateWrapper<>();
        operationLogUpdateWrapper.eq("id", id);
        operationLogDao.update(operationLog, operationLogUpdateWrapper);
        return true;
    }

    @Override
    public boolean deleteOperationLogById(Long operationLogId) {
        operationLogDao.deleteById(operationLogId);
        return true;
    }
}
