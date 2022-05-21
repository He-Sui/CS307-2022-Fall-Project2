package com.proj.sustc.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.proj.sustc.entity.Login_in;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface Login_inMapper extends BaseMapper<Login_in> {

 Login_in insertLogin_in(Login_in login_in);
 Login_in selectByUsername(String username);


}
