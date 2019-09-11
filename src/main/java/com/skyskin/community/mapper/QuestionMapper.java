package com.skyskin.community.mapper;

import com.skyskin.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Rock
 * @createDate 2019/09/07 21:39
 * @see com.skyskin.community.mapper
 */
@Mapper
public interface QuestionMapper {

    @Insert("insert into QUESTION (title,description,gmt_create,gmt_modified,creator,tag) values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);


    @Select("select * from QUESTION limit #{offset},#{size}")
    List<Question> getList(@Param(value = "offset") Integer offset, @Param(value = "size")Integer size);

    @Select("select count(1) from QUESTION")
    Integer count();

    @Select("select * from QUESTION where creator=#{creator}  limit #{offset},#{size}")
    List<Question> getListByCreator(@Param(value = "creator") Integer creator, @Param(value = "offset")Integer offset, @Param(value = "size")Integer size);

    @Select("select count(1) from QUESTION where creator=#{creator}")
    Integer countByCreator(Integer creator);
}
