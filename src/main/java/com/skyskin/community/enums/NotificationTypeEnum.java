package com.skyskin.community.enums;

/**
 * 用来定义通知的类型,枚举类
 * @author Rock
 * @createDate 2019/11/16 20:45
 * @see com.skyskin.community.enums
 */


public enum NotificationTypeEnum {
    REPLY_QUESTION(1,"回复了问题"),
    REPLY_COMMENT(2,"回复了评论")
    ;
    private int type;//状态
    private String name;//类型名称prof

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    NotificationTypeEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public static String nameOfType(int type){
        for (NotificationTypeEnum notificationTypeEnum : NotificationTypeEnum.values()) {
            if (notificationTypeEnum.getType()==type) {
                return notificationTypeEnum.getName();
            }
        }
        return "";

    }
}
