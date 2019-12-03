package com.skyskin.community.dto;

import com.skyskin.community.model.User;
import lombok.Data;

/**
 * @author Rock
 * 通知类用于进行页面数据传输的类
 * @createDate 2019/12/02 17:26
 * @see com.skyskin.community.dto
 */

@Data
public class NotificationDTO {

    private Long id;
    private Long gmtCreate;
    private Integer status;
    //造成通知的人
    private Long notifier;
    //通知的人名字
    private String notifierName;
    //通知的标题
    private String outerTitle;
    //页面显示通知类型
    private String string;
    //评论人头像
    private String notifieravatarurl;
    //通知类型
    private Integer type;

}
