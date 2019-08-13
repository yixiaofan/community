package com.mybolg.community.mapper;

import com.mybolg.community.exception.CustomizeErrorCode;
import com.mybolg.community.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper {

    @Insert("insert into comment (parent_id,content,type,gmt_create,gmt_update,commentator,like_count) values (#{parentId},#{content},#{type},#{gmtCreate},#{gmtUpdate},#{commentator},#{likeCount})")
    void insert(Comment comment);

    @Select("select * from comment where id = #{id}")
    Comment selectById(@Param("id") Long id);

    @Select("select * from comment where parent_id = #{id} and type = #{type} order by gmt_update desc")
    List<Comment> selectByIdAndType(@Param("id") Long id,@Param("type") Integer type);
}
