package com.skyskin.community.service;

import com.skyskin.community.dto.CommentDTO;
import com.skyskin.community.enums.CommentEnum;
import com.skyskin.community.enums.NotificationStatusEnum;
import com.skyskin.community.enums.NotificationTypeEnum;
import com.skyskin.community.exception.CustomizeErrorCodeImpl;
import com.skyskin.community.exception.CustomizeException;
import com.skyskin.community.mapper.*;
import com.skyskin.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Rock
 * @createDate 2019/11/05 22:15
 * @see com.skyskin.community.service
 */
@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CommentExtMapper commentExtMapper;

    @Autowired NotificationMapper notificationMapper;



    /**
     * 进行对于问题的评论或进行对于评论的评论
     *
     * @param comment
     * @param commentator
     */
    @Transactional
    public void insert(Comment comment, User commentator) {
        //如果没有父类ID则抛出错误，还没选择问题或者评论进行回复
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            //该错误会被 CustomizeExceptionHandler 捕获 进行页面的返回，而非使用json返回，这里需要优化
            //注解的为源代码
            //throw new CustomizeException(CustomizeErrorCodeImpl.TARGET_NOT_FOUND);
            //代码的改进在 CustomizeExceptionHandler 改进的mark为1000
            throw new CustomizeException(CustomizeErrorCodeImpl.TARGET_NOT_FOUND);

        }
        //当评论类型不存在时
        if (comment.getType() == null || !CommentEnum.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCodeImpl.TARGET_NOT_FOUND);

        }
        if (comment.getType() == CommentEnum.COMMENT.getType()) {
            //回复评论
            Comment dbcomment = commentMapper.selectByPrimaryKey(comment.getParentId());
            //评论没找到就进行异常抛出
            if (dbcomment == null) {
                throw new CustomizeException(CustomizeErrorCodeImpl.COMMENT_NOT_FOUND);
            }
            Question question = questionMapper.selectByPrimaryKey(dbcomment.getParentId());
            //问题不存在，直接抛出异常
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCodeImpl.QUESTION_NOT_FOUND);
            }
            commentMapper.insertSelective(comment);
            //在对于评论进行完回复后进行评论下面的二级评论数增加
            dbcomment.setCommentCount(1);
            commentExtMapper.incCommentCount(dbcomment);
            // 进行通知增加
            createNotification(dbcomment, dbcomment.getCommentator(), commentator, question.getTitle(),NotificationTypeEnum.REPLY_COMMENT);


        } else {
            //回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            //问题不存在，直接抛出异常
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCodeImpl.QUESTION_NOT_FOUND);
            }
            commentMapper.insertSelective(comment);
            //评论数更新
            question.setCommentCount(1);
            questionExtMapper.incCommentCount(question);
            // 进行通知
            createNotification(comment,question.getCreator(), commentator,question.getTitle(),NotificationTypeEnum.REPLY_QUESTION);

        }

    }

    private void createNotification(Comment comment, Long receiver, User commentator, String outerTitle, NotificationTypeEnum notificationTypeEnum) {
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(notificationTypeEnum.getType());
        notification.setOuterid(comment.getParentId());
        notification.setNotifier(comment.getCommentator());
        notification.setReceiver(receiver);
        notification.setNotifierName(commentator.getName());
        notification.setNotifieravatarurl(commentator.getAvatarUrl());
        notification.setOuterTitle(outerTitle);
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notificationMapper.insertSelective(notification);
    }


    /**
     * 根据对于的评论的ID得到该评论下的二级评论
     * @param id
     * @param type
     * @return
     */
    public List<CommentDTO> listByTargetId(Long id, CommentEnum type) {
        CommentExample example = new CommentExample();
        //设置匹配当前父类ID并且要是类型为 CommentEnum.UNREAD
        example.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(type.getType());
        example.setOrderByClause("GMT_CREATE ASC");
        //得到评论的信息
        List<Comment> comments = commentMapper.selectByExample(example);

        //
        if (comments.size() == 0) {
            return new ArrayList<>();
        }
        //获取去重的人
        Set<Long> commetators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        ArrayList<Long> userIds = new ArrayList<>();
        //把set存到List中
        userIds.addAll(commetators);

        //获取评论人并转换为 Map
        UserExample userExample = new UserExample();
        //设置按照list中的值查询
        userExample.createCriteria()
                .andIdIn(userIds);
        //查询到所以的符合条件的user
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        //转换comment为commentDTO
        List<CommentDTO> commentDTOList = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());

        return commentDTOList;
    }
}
