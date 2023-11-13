package com.sea.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * 与前端约定统一的返回数据格式
 * @author: SEA
 * @date: 2023/9/12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultDataDTO<T> {
    private Integer statusCode;//结果的状态码
    private T data;//返回的数据
    private String msg;//提示信息

    public ResultDataDTO(Integer statusCode, T data) {
        this.statusCode = statusCode;
        this.data = data;
    }
}