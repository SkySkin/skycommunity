package com.skyskin.community.enums;

/**
 * 定义该通知的状态
 * @author Rock
 * @createDate 2019/11/16 21:02
 * @see com.skyskin.community.enums
 */
public enum  NotificationStatusEnum {
    UNREAD(0),
    READ(1)
    ;
    private int status;

    public int getStatus() {
        return status;
    }

    NotificationStatusEnum(int status) {
        this.status = status;
    }
}
