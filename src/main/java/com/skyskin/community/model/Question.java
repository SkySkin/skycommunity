package com.skyskin.community.model;

import lombok.Data;
import lombok.ToString;

/**
 * @author Rock
 * @createDate 2019/09/07 21:46
 * @see com.skyskin.community.model
 */
@Data
public class Question {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer viewCount;
    private Integer likeCount;
    private Integer commentCount;

}
