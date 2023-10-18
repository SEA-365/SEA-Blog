package com.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 接收前端分页请求对象，一般是两个参数：当前页码以及每页的记录数
 * @author: sea
 * @date: 2023/10/18 13:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {
    private Integer pageNum;//当前页码
    private Integer pageSize;//每页的记录数
}
