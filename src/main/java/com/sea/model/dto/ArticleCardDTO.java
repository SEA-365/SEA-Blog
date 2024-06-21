package com.sea.model.dto;

import com.sea.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleCardDTO {

    private Long id;

    private String articleCover;

    private String title;

    private String content;

    private Integer isTop;

//    private Integer isFeatured;

    private String categoryName;

    private List<Tag> tags;

    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
