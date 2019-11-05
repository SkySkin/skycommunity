package com.skyskin.community.exception;

/**
 * 该类进行错误的信息定制化的一些封装,
 * @author Rock
 * @createDate 2019/11/05 16:07
 * @see com.skyskin.community.exception
 */
public enum  CustomizeErrorCodeImpl implements CustomizeErrorCode {
    QUESTION_NOT_FOUND("你已经进入了找不到结果的二次元空间 (●´∀｀●),要不换一个๑乛◡乛๑"),
    IS5XX_ERROR("你个小坏蛋,把我服务器搞炸了  (/= _ =)/~┴┴");
    ;

    private String message;

    public String getMessage() {
        return message;
    }

    CustomizeErrorCodeImpl(String message) {
        this.message = message;
    }
}
