package com.sea.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.model.dto.PageRequestDTO;
import com.model.vo.TagVO;
import com.sea.dao.TagDao;
import com.sea.entity.Tag;
import com.sea.service.TagService;
import com.sea.util.BeanCopyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: sea
 * @date: 2023/10/19 11:15
 */
@Service // 表示这是一个服务类
public class TagServiceImpl implements TagService {
    @Autowired // 自动注入TagDao对象
    TagDao tagDao;

    //日志打印
    public static final Logger log = LoggerFactory.getLogger(TagServiceImpl.class);

    public static final String TAG = "tagServiceImpl ====> ";

    @Override
    public List<Tag> getTagList(PageRequestDTO pageRequestDTO) {
        log.info(TAG + " " + pageRequestDTO);

        PageHelper.startPage(pageRequestDTO.getPageNum(), pageRequestDTO.getPageSize());//设置分页查询参数
        List<Tag> tagList = tagDao.selectList(null); // 此时查询的记录为所有记录
        return tagList; // 返回分类列表
    }

    @Override
    public Tag getTagById(Long tagId) {
        return tagDao.selectById(tagId); // 根据分类ID查询分类
    }

    @Override
    public boolean addTag(TagVO tagVO) {
        Tag tag = BeanCopyUtil.copyObject(tagVO, Tag.class);
        tagDao.insert(tag); // 添加分类
        return true;
    }

    @Override
    public boolean updateTag(TagVO tagVO) {
        Tag tag = BeanCopyUtil.copyObject(tagVO, Tag.class);
        Long id = tagVO.getId();
        // 使用条件构造器。指定更新条件
        UpdateWrapper<Tag> tagUpdateWrapper = new UpdateWrapper<>();
        // 此处第一个参数为列名，第二个参数为值，相当于子句：where id = Tag.id
        tagUpdateWrapper.eq("id", id);
        tagDao.update(tag, tagUpdateWrapper); // 更新分类
        return true;
    }

    @Override
    public boolean deleteTagById(Long tagId) {
        tagDao.deleteById(tagId); // 根据分类ID删除分类
        return true;
    }
}