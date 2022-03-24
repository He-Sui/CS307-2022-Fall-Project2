package com.proj.sustc.service;

import com.proj.sustc.entity.Staff;
import com.proj.sustc.vo.RespBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface IStaffService {
/**
     * Insert staff
     * @param staff information of staff
     */

     void insert(Staff staff);

/**
     * Select staff by staff Number
     * @param number staff number
     * @return staff information find by number
     */
     Staff selectByNumber(String number);

    /**
     * Return the numbers of people for all types of staffs
     * @return two columns: staff_type, count
     */
    List<Map<String, Object>> getAllStaffCount();

    RespBean DeleteStaff(HttpServletRequest request, HttpServletResponse response, String number,String login_user);

    RespBean doUpdateStaff(HttpServletRequest request, HttpServletResponse response, String number, String type);

    RespBean UpdateMobilePhone(String number, String phone);

    RespBean SelectStaff(String name, String number, HttpServletRequest request, HttpServletResponse response);

    List<Staff> getStaffByCookie(String staff);
}

