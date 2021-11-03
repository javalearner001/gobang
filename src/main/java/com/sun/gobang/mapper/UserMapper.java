package com.sun.gobang.mapper;

import com.sun.gobang.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author sunkai
 * @since 2021/3/17 11:19 上午
 */
@Mapper
public interface UserMapper {

    List<User> listUser(User user);


    @Select("select count(*) from user")
    int countUser();

    @Select("select user_id userId,user_name userName,user_address userAddress from user limit #{startIndex},#{pageSize}")
    List<User> listUserByConditon(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);
}
