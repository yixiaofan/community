package com.mybolg.community.mapper;

import com.mybolg.community.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Insert("insert into user (name,account_id,token,bio,gmt_create,gmt_update,avatar_url) values (#{name},#{accountId},#{token},#{bio},#{gmtCreate},#{gmtUpdate},#{avatarUrl})")
    void insert(User user);

    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from user where id = #{id}")
    User findById(@Param("id") Long id);

    @Select("select * from user where account_id = #{accountId}")
    User findByAccountId(@Param("accountId") String accountId);

    @Update("update user set name = #{name}, token = #{token}, gmt_update = #{gmtUpdate}, avatar_url = #{avatarUrl} where id = #{id}")
    void update(User dbUser);

    @Select({"<script> select * from user where id in <foreach item='id' index='index' collection='userIds' open='(' separator=',' close=')'> #{id} </foreach> </script>"})
    List<User> selectInId(@Param("userIds") List<Long> userIds);
}
