package com.skyskin.community.mapper;

import com.skyskin.community.model.Comment;
import com.skyskin.community.model.CommentExample;
import com.skyskin.community.model.Question;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CommentExtMapper {

    /**
     * 用于更新评论下面的子评论数
     * @param record
     * @return
     */
    int incCommentCount(Comment record);

}