package com.sea.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
/**
 * 逻辑删除/取消删除 操作需要的值对象
 * @author: sea
 * @date: 2023/10/20 19:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "逻辑删除操作需要的值对象")
public class DeleteVO {
    @NotNull
    @ApiModelProperty(name = "ids", value = "要修改状态的记录id", dataType = "List<Long>")
    private List<Long> ids;//要修改状态的记录id

    @NotNull
    @ApiModelProperty(name = "isDelete", value = "删除/取消删除", dataType = "Integer")
    private Integer isDelete;//删除/取消删除
}
