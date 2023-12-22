package com.sea.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * 文章标签表
 * @author: sea
 * @date: 2023/10/19 11:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_tag")
public class Tag {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;//标签id
    private String tagName;//标签名称
    private LocalDateTime createTime;//标签创建时间
    private LocalDateTime updateTime;//标签修改时间
}
