package com.proj.sustc.service;

import com.proj.sustc.entity.Staff;

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
    String getAllStaffCount();
}
