package com.mybolg.community.mapper;

import com.mybolg.community.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question (title,description,gmt_create,gmt_update,creator,tag) values (#{title},#{description},#{gmtCreate},#{gmtUpdate},#{creator},#{tag})")
    void create(Question question);

    @Select("select * from question")
    List<Question> list();

    @Select("select * from question where creator = #{userId}")
    List<Question> listByUserId(@Param("userId") Long userId);

    @Select("select * from question where id = #{id}")
    Question getById(@Param("id") Long id);

    @Update("update question set title = #{title}, description = #{description}, gmt_update = #{gmtUpdate}, tag = #{tag} where id = #{id}")
    void update(Question question);

    @Update("update question set view_count = view_count+1 where id = #{id}")
    void incViewCount(@Param("id") Long id);

    @Update("update question set comment_count = comment_count+1 where id = #{id}")
    void incCommentCount(@Param("id") Long id);
}
