package com.sea.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.sea.model.vo.CategoryVO;
import com.sea.model.vo.ConditionVO;
import com.sea.entity.Category;
import com.sea.service.CategoryService;
import com.sea.dao.CategoryDao;
import com.sea.util.BeanCopyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: sea
 * @date: 2023/10/18 15:21
 */
@Service // 表示这是一个服务类
public class CategoryServiceImpl implements CategoryService {
    @Autowired // 自动注入CategoryDao对象
    CategoryDao categoryDao;

    //日志打印
    public static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);

    public static final String TAG = "CategoryServiceImpl ====> ";

    @Override
    public List<Category> getCategoryList(ConditionVO conditionVO) {
        log.info(TAG + " " + conditionVO);

        PageHelper.startPage(conditionVO.getPageNum(), conditionVO.getPageSize());//设置分页查询参数
        List<Category> categoryList = categoryDao.selectList(null); // 此时查询的记录为所有记录
        return categoryList; // 返回分类列表
    }

    @Override
    public Category getCategoryById(Long categoryId) {
        return categoryDao.selectById(categoryId); // 根据分类ID查询分类
    }

    @Override
    public boolean addCategory(CategoryVO categoryVO) {
        Category category = BeanCopyUtil.copyObject(categoryVO, Category.class);
        categoryDao.insert(category); // 添加分类
        return true;
    }

    @Override
    public boolean updateCategory(CategoryVO categoryVO) {
        Category category = BeanCopyUtil.copyObject(categoryVO, Category.class);
        Long id = categoryVO.getId();
        // 使用条件构造器。指定更新条件
        UpdateWrapper<Category> categoryUpdateWrapper = new UpdateWrapper<>();
        // 此处第一个参数为列名，第二个参数为值，相当于子句：where id = Category.id
        categoryUpdateWrapper.eq("id", id);
        categoryDao.update(category, categoryUpdateWrapper); // 更新分类
        return true;
    }

    @Override
    public boolean deleteCategoryById(Long categoryId) {
        categoryDao.deleteById(categoryId); // 根据分类ID删除分类
        return true;
    }
}
