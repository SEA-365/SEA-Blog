package com.sea.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 通知公告信息值对象 - 前端新增通知公告时，需要封装的信息
 * 注意：实体类的字段名称与数据库中的字段必须对应，
 *  例如：comment_reply_id <====> commentRelyId 进行对应；
 * @author: sea
 * @date: 2023/12/25 18:21
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "公告信息对象")
public class NoticeVO {
    @ApiModelProperty(name = "id", value = "公告id", dataType = "Integer")
    private Long id;//公告id

    @NotNull(message = "公告标题不能为空")
    @ApiModelProperty(name = "noticeTitle", value = "公告标题", dataType = "String")
    private String noticeTitle;//公告标题

    @NotNull(message = "公告类型不能为空")
    @ApiModelProperty(name = "noticeType", value = "公告类型，默认0, 0-公告, 1-通知, 2-提醒", dataType = "Integer")
    private Integer noticeType;//公告类型

    @NotNull(message = "公告状态不能为空")
    @ApiModelProperty(name = "noticeStatus", value = "公告状态，默认0, 0-正常, 1-关闭", dataType = "Integer")
    private Integer noticeStatus;//公告状态

    @ApiModelProperty(name = "noticeContent", value = "公告内容", dataType = "String")
    private String noticeContent;//公告内容

    @NotNull(message = "公告创建者不能为空")
    @ApiModelProperty(name = "noticeStatus", value = "公告创建者", dataType = "String")
    private String creator;//创建者

}
