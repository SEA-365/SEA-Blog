package com.sea.service;

import com.sea.model.vo.CategoryVO;
import com.sea.model.vo.ConditionVO;
import com.sea.entity.Category;

import java.util.List;

/**
 * 分类表业务层接口
 * @author: sea
 * @date: 2023/10/18 15:17
 */
public interface CategoryService {
    /**
     * 获取分类信息列表
     * @return 指定页的分类信息
     */
    public List<Category> getCategoryList(ConditionVO conditionVO);

    /**
     * 根据id获取指定分类信息
     * @param categoryId 分类id
     * @return 指定分类信息
     */
    Category getCategoryById(Long categoryId);

    /**
     * 根据名称查询分类
     * @param conditionVO 查询条件
     * @return 查询结果
     */
    List<Category> getCategoryByName(ConditionVO conditionVO);

    /**
     * 添加分类
     * @param categoryVO 待添加的分类信息
     */
    boolean addCategory(CategoryVO categoryVO);

    /**
     * 修改分类
     * @param categoryVO 新的分类信息
     */
    boolean updateCategory(CategoryVO categoryVO);

    /**
     * 删除分类
     * @param categoryId 待删除分类id
     */
    boolean deleteCategoryById(Long categoryId);
}
