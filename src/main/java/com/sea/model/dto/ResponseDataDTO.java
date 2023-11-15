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
public class ResponseDataDTO<T> {
    private Integer statusCode;//结果的状态码
    private T data;//返回的数据
    private String msg;//提示信息

    public ResponseDataDTO(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public ResponseDataDTO(Integer statusCode, String msg) {
        this.statusCode = statusCode;
        this.msg = msg;
    }

    public ResponseDataDTO(Integer statusCode, T data) {
        this.statusCode = statusCode;
        this.data = data;
    }


}
