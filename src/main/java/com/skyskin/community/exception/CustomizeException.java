package com.skyskin.community.exception;

/**
 *
 *
 * @author Rock
 * @createDate 2019/11/05 15:46
 * @see com.skyskin.community.exception
 */
public class CustomizeException extends  RuntimeException {
    private String message;
    private Integer code;

    public CustomizeException(CustomizeErrorCode errorCode) {

        this.message = errorCode.getMessage();
        this.code=errorCode.getCode();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
