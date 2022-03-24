package com.proj.sustc.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.proj.sustc.entity.Login_in;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface Login_inMapper extends BaseMapper<Login_in> {

 Login_in insertLogin_in(Login_in login_in);
 Login_in selectByUsername(String username);

void UpdateUserType(@Param("type")String type,@Param("username") String username);

void DeleteUser(@Param("username")String username);

void UpdatePassword(@Param("password") String password,@Param("username") String username);
}
