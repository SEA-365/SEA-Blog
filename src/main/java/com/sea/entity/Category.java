package com.sea.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * 文章分类表
 * @author: sea
 * @date: 2023/10/18 14:56
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_category")
public class Category {
    private Long id;//分类id

    @NotBlank(message = "分类名称不能为空！")
    private String categoryName;//分类名称

    private LocalDateTime createTime;//分类创建时间
    private LocalDateTime updateTime;//分类修改时间
}
