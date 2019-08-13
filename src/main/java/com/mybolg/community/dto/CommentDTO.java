package com.mybolg.community.dto;

import com.mybolg.community.model.User;
import lombok.Data;

@Data
public class CommentDTO {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtUpdate;
    private Long likeCount;
    private String content;
    private User user;
}
