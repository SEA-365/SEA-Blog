package com.sea.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;//分类id
    private String categoryName;//分类名称
    @JsonFormat(timezone = "GMT+8",pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;//分类创建时间
    @JsonFormat(timezone = "GMT+8",pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;//分类修改时间
}
