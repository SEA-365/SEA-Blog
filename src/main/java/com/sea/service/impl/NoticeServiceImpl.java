package com.sea.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.sea.dao.NoticeDao;
import com.sea.entity.Notice;
import com.sea.model.vo.ConditionVO;
import com.sea.model.vo.NoticeVO;
import com.sea.service.NoticeService;
import com.sea.util.BeanCopyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: sea
 * @date: 2023/12/27 18:37
 */
@Service
public class NoticeServiceImpl implements NoticeService{
    @Autowired
    NoticeDao noticeDao;

    //日志打印
    public static final Logger log = LoggerFactory.getLogger(NoticeServiceImpl.class);

    public static final String TAG = "NoticeServiceImpl ====> ";

    @Override
    public List<Notice> getNoticeList(ConditionVO conditionVO) {
        log.info(TAG + " " + conditionVO);

        PageHelper.startPage(conditionVO.getPageNum(), conditionVO.getPageSize());//设置分页查询参数
        List<Notice> noticeList = noticeDao.selectList(null); // 此时查询的记录为所有记录
        return noticeList; // 返回公告列表
    }

    @Override
    public Notice getNoticeById(Long noticeId) {
        return noticeDao.selectById(noticeId); // 根据公告ID查询公告
    }

    @Override
    public boolean addNotice(NoticeVO noticeVO) {
        Notice notice = BeanCopyUtil.copyObject(noticeVO, Notice.class);
        noticeDao.insert(notice); // 添加公告
        return true;
    }

    @Override
    public boolean updateNotice(NoticeVO noticeVO) {
        Notice notice = BeanCopyUtil.copyObject(noticeVO, Notice.class);
        Long id = noticeVO.getId();
        // 使用条件构造器。指定更新条件
        UpdateWrapper<Notice> noticeUpdateWrapper = new UpdateWrapper<>();
        // 此处第一个参数为列名，第二个参数为值，相当于子句：where id = notice.id
        noticeUpdateWrapper.eq("id", id);
        noticeDao.update(notice, noticeUpdateWrapper); // 更新公告
        return true;
    }

    @Override
    public boolean deleteNoticeById(Long noticeId) {
        QueryWrapper<Notice> noticeQueryWrapper = new QueryWrapper<>();
        noticeQueryWrapper.eq("id", noticeId);
        noticeDao.delete(noticeQueryWrapper); // 根据公告ID删除公告
        return true;
    }
}
