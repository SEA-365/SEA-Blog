package com.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 分类信息值对象 - 前端新增分类时，需要封装的信息
 * @author: sea
 * @date: 2023/10/19 15:02
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "分类信息对象")
public class CategoryVO {
    @ApiModelProperty(name = "id", value = "分类id", dataType = "Long")
    private Long id;//分类id

    @NotBlank(message = "分类名称不能为空！")
    @ApiModelProperty(name = "categoryName", value = "分类名称", dataType = "String")
    private String categoryName;//分类名称
}
