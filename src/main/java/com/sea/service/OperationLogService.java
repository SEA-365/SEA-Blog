package com.sea.service;

import com.sea.entity.OperationLog;
import com.sea.model.vo.ConditionVO;
import com.sea.model.vo.OperationLogVO;

import java.util.List;

/**
 * 操作日志记录表业务层接口
 * @author: sea
 * @date: 2023/12/18 15:18
 */

public interface OperationLogService {
    /**
     * 获取操作日志记录信息列表
     * @return 指定页的操作日志记录信息
     */
    public List<OperationLog> getOperationLogList(ConditionVO conditionVO);

    /**
     * 根据id获取指定操作日志记录信息
     * @param operationLogId 操作日志记录id
     * @return 指定操作日志记录信息
     */
    OperationLog getOperationLogById(Long operationLogId);

    /**
     * 添加操作日志记录
     * @param operationLogVO 待添加的操作日志记录信息
     */
    boolean addOperationLog(OperationLogVO operationLogVO);

    /**
     * 修改操作日志记录
     * @param operationLogVO 新的操作日志记录信息
     */
    boolean updateOperationLog(OperationLogVO operationLogVO);

    /**
     * 删除操作日志记录
     * @param operationLogId 待删除操作日志记录id
     */
    boolean deleteOperationLogById(Long operationLogId);

}
