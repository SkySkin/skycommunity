package com.skyskin.community.mapper;

import com.skyskin.community.model.Question;

public interface QuestionExtMapper {

    /**
     * 用于更新阅读数
     */
    int incView(Question record);

    /**
     *用于更新评论数
     */
    int incCommentCount(Question record);



}