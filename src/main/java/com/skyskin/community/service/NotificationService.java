package com.skyskin.community.service;

import com.skyskin.community.dto.NotificationDTO;
import com.skyskin.community.dto.PageInfoDTO;
import com.skyskin.community.dto.QuestionDTO;
import com.skyskin.community.enums.NotificationStatusEnum;
import com.skyskin.community.enums.NotificationTypeEnum;
import com.skyskin.community.mapper.NotificationMapper;
import com.skyskin.community.mapper.UserMapper;
import com.skyskin.community.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Rock
 * @createDate 2019/12/02 17:32
 * @see com.skyskin.community.service
 */

@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private UserMapper userMapper;


    /**
     * 该方法进行对于通知的分页展示
     * @param userId
     * @param page
     * @param size
     * @return
     */
    public PageInfoDTO list(Long userId, Integer page, Integer size) {

        //得到条目总数
//        Integer totalCount = notificationMapper.countByCreator(userId);
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(userId);
        notificationExample.setOrderByClause("STATUS ASC");
        //得到总条目数
        Integer totalCount = (int) notificationMapper.countByExample(notificationExample);
        //得到实例，以及设置page数据的值
        PageInfoDTO<NotificationDTO> pageInfoDTO = new PageInfoDTO(totalCount, page, size);
        //公式: ((page-1)*5),5
        Integer offset = (pageInfoDTO.getPage() - 1) * size;
//        QuestionExample questionExample1 = new QuestionExample();
//        questionExample1.createCriteria().andCreatorEqualTo(userId);
        //得到改用户的所以的通知（分页）
        List<Notification> Notifications = notificationMapper.selectByExampleWithRowbounds(notificationExample, new RowBounds(offset, size));

        //创建数据传输的DTO的容器
        List<NotificationDTO> NotificationDTOs = new ArrayList<>();

        //如果没有通知则打断
        if (Notifications.size()==0) {
            pageInfoDTO.setDataType(NotificationDTOs);
            return pageInfoDTO;
        }

//        //得到所有的通知里面的人员，去重
//        Set<Long> disUserIds = Notifications.stream().map(notify -> notify.getNotifier()).collect(Collectors.toSet());
//
//        //讲得到的所有通知的人员的id存入
//        ArrayList<Long> UserIds = new ArrayList<>(disUserIds);
//
//        //查询所有的通知的人员的信息
//        UserExample userExample = new UserExample();
//        userExample.createCriteria().andIdIn(UserIds);
//
//        List<User> users = userMapper.selectByExample(userExample);
        //由于在notification中增加了名称和标题，这里不再执行上述内容进行用户查询
        for (Notification notification : Notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationDTO.setString(NotificationTypeEnum.nameOfType(notification.getType()));
            NotificationDTOs.add(notificationDTO);
        }


        pageInfoDTO.setDataType(NotificationDTOs);

        return pageInfoDTO;

    }

    /**
     * 根据id得到通知
     * @param id
     * @return
     */

    public Notification getNotificationById(Long id) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andIdEqualTo(id);
        List<Notification> notifications = notificationMapper.selectByExample(notificationExample);
        if (notifications.size()!=0) {
            return notifications.get(0);
        }
        return null;
    }


    /**
     * 根据id修改通知
     * @param id
     * @param notification
     */
    public int updateNotificationById(Long id, Notification notification) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andIdEqualTo(id);
        return notificationMapper.updateByExample(notification,notificationExample);
    }

    /**
     * 根据id查询最新回复个个数
     * @param id
     * @return
     */
    public Long selectNewNotifactionCount(Long id) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(id).andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        return notificationMapper.countByExample(notificationExample);
    }
}
