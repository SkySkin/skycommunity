package com.skyskin.community.controller;

import com.skyskin.community.dto.ResultDTO;
import com.skyskin.community.exception.CustomizeErrorCodeImpl;
import com.skyskin.community.exception.CustomizeException;
import com.skyskin.community.model.Notification;
import com.skyskin.community.model.User;
import com.skyskin.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Rock
 * @createDate 2019/12/02 22:17
 * @see com.skyskin.community.controller
 */
@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;
    /**
     *
     * @param id 网页上面传递来的id
     * @return
     */

    @RequestMapping(value = "/notification/{id}",method = RequestMethod.GET)
    public String modifiedStatus(@PathVariable(name = "id")Long id, HttpServletRequest request){
        //判断当前用户是否登陆
        User user = (User) request.getSession().getAttribute("user");
        if (user==null) {
            throw new CustomizeException(CustomizeErrorCodeImpl.NOT_LOGIN);
        }
        Long id1 = user.getId();
        // 修改当前通知的状态
        //1.根据id得到这个通知的状态
        Notification notification=notificationService.getNotificationById(id);
        if (notification!=null&&id1.equals(notification.getReceiver())){
            if (notification.getStatus()==0) {
                //如果得到的通知现在的状态是未读，则进行状态修改
                notification.setStatus(1);
                notificationService.updateNotificationById(id,notification);
            }
            //进行跳转

            //得到当前通知要跳转的问题id,进行跳转
            Long outerid = notification.getOuterid();
            return "redirect:/question/"+outerid+"";
        }
        return "redirect:/";
    }
}
