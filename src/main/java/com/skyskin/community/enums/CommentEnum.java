package com.skyskin.community.enums;

/**
 * @author Rock
 * @createDate 2019/11/05 22:12
 * @see com.skyskin.community.enums
 * 该枚举类用来定义评论的类型，是二级评论还是一级
 */
public enum  CommentEnum {
    QUESTION(1, ""),
    COMMENT(2, ""),
    SUCCESS(200, "请求成功")
    ;
    private Integer type;
    private String Message;

    public Integer getType() {
        return type;
    }

    public String getMessage() {
        return Message;
    }

    CommentEnum(Integer type, String message) {
        this.type = type;
        this.Message=message;
    }
}
