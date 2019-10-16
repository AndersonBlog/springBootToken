package com.wu.springBootToken.mapper;

import com.wu.springBootToken.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 描述:
 *
 * @author WUAN4
 * @create 2019-10-16 2:00 PM
 */
@Mapper
public interface UserMapper {

    User findByUserNameAndPasswd(String username, String userpasswd);

    @Select("select * from user where id=#{id}")
    User findUserById(@Param("id") String id);
}
