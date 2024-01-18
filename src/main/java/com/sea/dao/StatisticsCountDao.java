package com.sea.dao;

import com.sea.model.vo.StatisticsCountVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: sea
 * @date: 2024/1/18 15:20
 */
@Mapper
public interface StatisticsCountDao {
    /**
     * 获取统计数据
     */
    StatisticsCountVO getCount();

}
