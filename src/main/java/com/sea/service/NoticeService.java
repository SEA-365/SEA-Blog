package com.sea.service;

import com.sea.entity.Notice;
import com.sea.model.vo.ConditionVO;
import com.sea.model.vo.NoticeVO;

import java.util.List;

/**
 * 通知公告表业务层接口
 * @author: sea
 * @date: 2023/12/27 18:34
 */
public interface NoticeService {
    /**
     * 获取公告信息列表
     * @return 指定页的公告信息
     */
    public List<Notice> getNoticeList(ConditionVO conditionVO);

    /**
     * 根据id获取指定公告信息
     * @param noticeId 公告id
     * @return 指定公告信息
     */
    Notice getNoticeById(Long noticeId);

    /**
     * 添加公告
     * @param noticeVO 待添加的公告信息
     */
    boolean addNotice(NoticeVO noticeVO);

    /**
     * 修改公告
     * @param noticeVO 新的公告信息
     */
    boolean updateNotice(NoticeVO noticeVO);

    /**
     * 删除公告
     * @param noticeId 待删除公告id
     */
    boolean deleteNoticeById(Long noticeId);
}
