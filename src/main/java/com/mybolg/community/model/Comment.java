package com.mybolg.community.model;

import lombok.Data;

@Data
public class Comment {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtUpdate;
    private Long likeCount;
    private String content;
}
