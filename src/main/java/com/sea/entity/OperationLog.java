package com.sea.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 操作日志记录表
 * @author: sea
 * @date: 2023/12/18 15:09
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_operation_log")
public class OperationLog {
    private Long id;//操作日志记录id
    private String operationIp;//主机ip
    private String operationLocation;//操作地点
    private String method;//方法名
    private String args;//方法参数
    private String operationName;//操作人
    private String operationType;//操作类型
    private String returnValue;//返回参数
    private LocalDateTime createTime;//创建时间
}
