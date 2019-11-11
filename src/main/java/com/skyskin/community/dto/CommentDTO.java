package com.skyskin.community.dto;

import com.skyskin.community.model.User;
import lombok.Data;

/**
 * @author Rock 用于接受数据库的数据
 * @createDate 2019/11/08 22:28
 * @see com.skyskin.community.dto
 */
@Data
public class CommentDTO {

    private Long id;

    private Long parentId;

    private Integer type;

    private String content;

    private Long commentator;

    private Long gmtModified;

    private Long gmtCreate;

    private Long likeCount;

    private User user;


}
