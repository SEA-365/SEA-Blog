package com.sea.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sea.entity.OperationLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 操作日志记录表数据访问层接口
 * @author: sea
 * @date: 2023/12/18 15:15
 */
@Mapper
public interface OperationLogDao extends BaseMapper<OperationLog> {

}
