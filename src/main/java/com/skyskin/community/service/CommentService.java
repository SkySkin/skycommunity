package com.skyskin.community.service;

import com.skyskin.community.enums.CommentEnum;
import com.skyskin.community.exception.CustomizeErrorCodeImpl;
import com.skyskin.community.exception.CustomizeException;
import com.skyskin.community.mapper.CommentMapper;
import com.skyskin.community.mapper.QuestionExtMapper;
import com.skyskin.community.mapper.QuestionMapper;
import com.skyskin.community.model.Comment;
import com.skyskin.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


    /**
     * 进行对于问题的评论或进行对于评论的评论
     * @param comment
     */
    @Transactional
    public void insert(Comment comment) {
        //如果没有父类ID则抛出错误，还没选择问题或者评论进行回复
        if (comment.getParentId()==null || comment.getParentId()==0) {
            //该错误会被 CustomizeExceptionHandler 捕获 进行页面的返回，而非使用json返回，这里需要优化
            //注解的为源代码
            //throw new CustomizeException(CustomizeErrorCodeImpl.TARGET_NOT_FOUND);
            //代码的改进在 CustomizeExceptionHandler 改进的mark为1000
            throw new CustomizeException(CustomizeErrorCodeImpl.TARGET_NOT_FOUND);

        }
        //当评论类型不存在时
        if (comment.getType()==null || !CommentEnum.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCodeImpl.TARGET_NOT_FOUND);

        }
        if (comment.getType() == CommentEnum.COMMENT.getType()) {
            //回复评论
            Comment dbcomment = commentMapper.selectByPrimaryKey(comment.getParentId());
            //评论没找到就进行异常抛出
            if (dbcomment==null) {
                throw new CustomizeException(CustomizeErrorCodeImpl.COMMENT_NOT_FOUND);
            }
            commentMapper.insertSelective(comment);

        }else {
            //回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            //问题不存在，直接抛出异常
            if (question==null) {
                throw new CustomizeException(CustomizeErrorCodeImpl.QUESTION_NOT_FOUND);
            }
            commentMapper.insertSelective(comment);
            //评论数更新
            question.setCommentCount(1);
            questionExtMapper.incCommentCount(question);
        }

    }
}
