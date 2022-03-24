package com.proj.sustc.mapper;

import com.proj.sustc.entity.Staff;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface StaffMapper {
    /**
     * Insert staff into table staff
     * @param staff information of staff
     * @return affected rows
     */
    Integer insert(Staff staff);

    /**
     * Select information by Staff Number
     * @param number Staff Number
     * @return The Staff information find by number
     */
    Staff selectByNumber(String number);


    List<Map<String, Object>> selectStaffTypeCount();

    List<Staff> selectAllStaff();

    void DeleteStaffByNumber(String number);

    void UpdateType(@Param("type")String type,@Param("number") String number);

    void UpdatePhoneNUmber(@Param("phone_number")String phone_number,@Param("number")String number);

    List<Staff> SelectStaffByNameAndNumber(@Param("number")String number,@Param("name")String name);

}
