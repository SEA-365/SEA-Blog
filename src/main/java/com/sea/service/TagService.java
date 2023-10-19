package com.sea.service;

import com.model.dto.PageRequestDTO;
import com.model.vo.TagVO;
import com.sea.entity.Tag;

import java.util.List;

/**
 * 标签表业务层接口
 * @author: sea
 * @date: 2023/10/19 11:10
 */
public interface TagService {
    /**
     * 获取标签信息列表
     * @return 指定页的标签信息
     */
    public List<Tag> getTagList(PageRequestDTO pageRequestDTO);

    /**
     * 根据id获取指定标签信息
     * @param tagId 标签id
     * @return 指定标签信息
     */
    Tag getTagById(Long tagId);

    /**
     * 添加标签
     * @param tagVO 待添加的标签信息
     */
    boolean addTag(TagVO tagVO);

    /**
     * 修改标签
     * @param tagVO 新的标签信息
     */
    boolean updateTag(TagVO tagVO);

    /**
     * 删除标签
     * @param tagId 待删除标签id
     */
    boolean deleteTagById(Long tagId);
}
