package com.proj.sustc.mapper;


import com.proj.sustc.entity.Role_login_in;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface Role_login_inMapper {
  Role_login_in SelectRoleIdByLogin_inType(String login_type);
}
