package com.sea.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 通知公告表
 * @author: sea
 * @date: 2023/12/25 17:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_notice")
public class Notice {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;//公告id
    private String noticeTitle;//公告标题
    private Integer noticeType;//公告类型
    private Integer noticeStatus;//公告状态
    private String noticeContent;//公告内容
    private String creator;//创建者
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间

}
