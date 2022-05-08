package com.proj.sustc.service.implement;

import com.proj.sustc.entity.Staff;
import com.proj.sustc.mapper.CenterMapper;
import com.proj.sustc.mapper.StaffMapper;
import com.proj.sustc.service.IStaffService;
import com.proj.sustc.service.exception.InsertException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StaffServiceImpl implements IStaffService {

    @Autowired
    private StaffMapper staffMapper;

    @Autowired
    private CenterMapper centerMapper;

    @Override
    public void insert(Staff staff) {
        if (staff == null || staff.getName() == null || staff.getAge() == null || staff.getGender() == null || staff.getNumber() == null || staff.getSupplyCenter() == null || staff.getPhoneNumber() == null || staff.getType() == null)
            throw new InsertException("Staff information should not be null");
        if (staff.getNumber().length() != 8)
            throw new InsertException("Invalid staff number");
        if (!(staff.getGender().equals("Male") || staff.getGender().equals("Female")))
            throw new InsertException("Invalid gender");
        if (staff.getPhoneNumber().length() != 11)
            throw new InsertException("Invalid phone number");
        if (!(staff.getType().equals("Director") || staff.getType().equals("Supply Staff") || staff.getType().equals("Salesman") || staff.getType().equals("Contracts Manager")))
            throw new InsertException("Invalid type");
        if (centerMapper.findByCenterName(staff.getSupplyCenter()) == null)
            throw new InsertException("Invalid Supply Center [" + staff.getSupplyCenter() + "]");
        if (staffMapper.selectByNumber(staff.getNumber()) != null)
            throw new InsertException("Duplicated Staff Number [" + staff.getNumber() + "]");
        if (staffMapper.insert(staff) != 1)
            throw new InsertException("Unknown error when insert Staff");

    }

    @Override
    public Staff selectByNumber(String number) {
        return staffMapper.selectByNumber(number);
    }

    @Override
    public String getAllStaffCount() {
        List<Map<String, Object>> list = staffMapper.selectStaffTypeCount();
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-20s%-10s\n", "type", "count"));
        for (Map<String, Object> res : list) {
            sb.append(String.format("%-20s%-10s", res.get("type"), res.get("count")));
            sb.append("\n");
        }
        return sb.toString();
    }
}
