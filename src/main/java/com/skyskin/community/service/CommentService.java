package com.skyskin.community.service;

import com.skyskin.community.exception.CustomizeErrorCodeImpl;
import com.skyskin.community.exception.CustomizeException;
import com.skyskin.community.model.Comment;
import org.springframework.stereotype.Service;

/**
 * @author Rock
 * @createDate 2019/11/05 22:15
 * @see com.skyskin.community.service
 */
@Service
public class CommentService {
    public void insert(Comment comment) {
        //如果没有父类ID则抛出错误，还没选择问题或者评论进行回复
        if (comment.getParentId()==null || comment.getParentId()==0) {
            //该错误会被 CustomizeExceptionHandler 捕获 进行页面的返回，而非使用json返回，这里需要优化
            //注解的为源代码
            //throw new CustomizeException(CustomizeErrorCodeImpl.TARGET_NOT_FOUND);
            //代码的改进在 CustomizeExceptionHandler 改进的mark为1000
            throw new CustomizeException(CustomizeErrorCodeImpl.TARGET_NOT_FOUND);

        }

    }
}
