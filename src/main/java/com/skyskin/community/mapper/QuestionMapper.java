package com.skyskin.community.mapper;

import com.skyskin.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Rock
 * @createDate 2019/09/07 21:39
 * @see com.skyskin.community.mapper
 */

@Mapper
public interface QuestionMapper {

    @Insert("insert into QUESTION (title,description,gmt_create,gmt_modified,creator,tag) values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);


}
