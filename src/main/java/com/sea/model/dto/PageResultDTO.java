package com.sea.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 给前端返回分页信息
 * @author: sea
 * @date: 2023/10/18 13:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResultDTO {
    private Integer pageNum;//当前页码
    private Integer pageSize;//每页的记录数
    private Integer totalSize;//总记录数
    private Integer totalPages;//总页数
    private List<?> result;//返回的数据
}
