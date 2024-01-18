package com.sea.service.impl;

import com.sea.dao.StatisticsCountDao;
import com.sea.model.vo.StatisticsCountVO;
import com.sea.service.StatisticsCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: sea
 * @date: 2024/1/18 15:17
 */
@Service
public class StatisticsCountServiceImpl implements StatisticsCountService {

    @Autowired
    private StatisticsCountDao statisticsCountDao;
    @Override
    public StatisticsCountVO getPanelCount() {
        StatisticsCountVO statisticsCountVO = statisticsCountDao.getCount();
        return statisticsCountVO;
    }
}
