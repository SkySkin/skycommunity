package com.skyskin.community.exception;

/**
 * 该类进行错误的信息定制化的一些封装,
 * @author Rock
 * @createDate 2019/11/05 16:07
 * @see com.skyskin.community.exception
 */
public enum  CustomizeErrorCodeImpl implements CustomizeErrorCode {
    RUSULT_NOT_FOUND(1001, "你已经进入了找不到结果的二次元空间 (●´∀｀●),要不换一个๑乛◡乛๑"),
    TARGET_NOT_FOUND(1002, "你还没选择任何问题或评论进行回复呢 (●´∀｀●),要不选一个๑乛◡乛๑"),
    NTO_LOGIN_ERROR(1003,"未登录不能进行评论哦，请先登陆๑乛◡乛๑"),
    IS5XX_ERROR(2001, "你个小坏蛋,把我服务器搞炸了  (/= _ =)/~┴┴"),
    TYPE_NOT_FOUND(2002, "评论类型错误或不存在  (/= _ =)/~┴┴"),
    COMMENT_NOT_FOUND(2003, "该评论找不到了,不然换个试试  (●´∀｀●)"),
    QUESTION_NOT_FOUND(2004, "该问题找不到了,不然换个试试  (●´∀｀●)"),
    COMMENT_CONTENT_ISEMPTY(2005, "你怎么能回复空的呢  (●´∀｀●)"),
    ;

    private String message;
    private Integer code;

    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    CustomizeErrorCodeImpl(Integer code, String message) {
        this.code=code;
        this.message = message;
    }
}
