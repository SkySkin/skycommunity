package com.skyskin.community.dto;

import com.skyskin.community.model.User;
import lombok.Data;

/**
 * @author Rock
 * @createDate 2019/09/09 16:46
 * @see com.skyskin.community.dto
 */

//数据和数据进行传输WW
@Data
public class QuestionDTO {
    private Long id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer viewCount;
    private Integer likeCount;
    private Integer commentCount;
    private User user;
}
