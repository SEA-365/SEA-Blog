package com.sea.util;

import com.github.pagehelper.PageInfo;
import com.sea.model.dto.PageResultDTO;
import com.sea.model.vo.ConditionVO;

/**
 * 分页查询工具类
 * @author: sea
 * @date: 2023/10/18 13:42
 */
public class PageUtil {
    /**
     * 封装分页信息
     * @param conditionVO 分页请求对象，包分页参数
     * @param pageInfo PageHelper插件中的一个类
     * @return 封装好的分页信息
     */
    public static PageResultDTO getPageResultDTO(ConditionVO conditionVO, PageInfo<?> pageInfo){
        PageResultDTO pageResultDTO = new PageResultDTO();
        pageResultDTO.setPageNum(pageInfo.getPageNum());
        pageResultDTO.setPageSize(pageInfo.getPageSize());
        pageResultDTO.setTotalSize(pageInfo.getSize());
        pageResultDTO.setTotalPages(pageInfo.getPages());
        pageResultDTO.setResult(pageInfo.getList());
        return pageResultDTO;
    }
}
