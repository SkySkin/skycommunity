package com.skyskin.community.dto;

import lombok.Data;

/**
 * @author Rock
 * @createDate 2019/11/05 21:27
 * @see com.skyskin.community.dto
 *
 * 该类用于进行评论的数据传输
 */
@Data
public class CommentDTO {
    //父类ID
    private Long parentId;
    //内容
    private  String content;
    //类型
    private Integer type;
}
