package com.mybolg.community.model;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String name;
    private String accountId;
    private String token;
    private String bio;
    private Long gmtCreate;
    private Long gmtUpdate;
    private String avatarUrl;
}
