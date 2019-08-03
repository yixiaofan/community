package com.mybolg.community.mapper;

import com.mybolg.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question (title,description,gmt_create,gmt_update,creator,tag) values (#{title},#{description},#{gmtCreate},#{gmtUpdate},#{creator},#{tag})")
    void create(Question question);

    @Select("select * from question")
    List<Question> list();
}
