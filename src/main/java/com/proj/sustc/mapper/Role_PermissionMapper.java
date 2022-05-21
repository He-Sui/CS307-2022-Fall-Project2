package com.proj.sustc.mapper;

import com.proj.sustc.entity.Role_Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface Role_PermissionMapper {
    Role_Permission SelectByRoleIdAndPermission(@Param("role_id") Integer role_id,@Param("permission_id") Integer permission);
}
