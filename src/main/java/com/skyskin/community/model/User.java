package com.skyskin.community.model;

import lombok.Data;

/**
 * @author Rock
 * @createDate 2019/09/06 22:20
 * @see com.skyskin.community.model
 */

@Data
public class User {
    private Integer id;
    private String name;
    private String accountId;
    //描述
    private String bio;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatarUrl;
}
