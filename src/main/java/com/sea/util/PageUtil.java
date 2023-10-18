package com.sea.util;

import com.github.pagehelper.PageInfo;
import com.model.dto.PageRequestDTO;
import com.model.dto.PageResultDTO;

/**
 * 分页查询工具类
 * @author: sea
 * @date: 2023/10/18 13:42
 */
public class PageUtil {
    /**
     * 封装分页信息
     * @param pageRequestDTO 分页请求对象，包分页参数
     * @param pageInfo PageHelper插件中的一个类
     * @return 封装好的分页信息
     */
    public static PageResultDTO getPageResultDTO(PageRequestDTO pageRequestDTO, PageInfo<?> pageInfo){
        PageResultDTO pageResultDTO = new PageResultDTO();
        pageResultDTO.setPageNum(pageInfo.getPageNum());
        pageResultDTO.setPageSize(pageInfo.getPageSize());
        pageResultDTO.setTotalSize(pageInfo.getSize());
        pageResultDTO.setTotalPages(pageInfo.getPages());
        pageResultDTO.setResult(pageInfo.getList());
        return pageResultDTO;
    }
}
