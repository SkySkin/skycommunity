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

    public CustomizeException(CustomizeErrorCode errorCode) {
        this.message = errorCode.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
