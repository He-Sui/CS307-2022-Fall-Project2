package com.proj.sustc.mapper;

import com.proj.sustc.entity.Staff;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class StaffMapperTests {
    @Autowired
    private StaffMapper staffMapper;

    @Test
    public void insert() {
        Staff staff = new Staff();
        staff.setAge(47);
        staff.setGender("Female");
        staff.setNumber("11311024");
        staff.setSupplyCenter("America");
        staff.setPhoneNumber("15038403217");
        staff.setType("Director");
        staff.setName("Kong Yibo");
        staffMapper.insert(staff);
    }

    @Test
    public void select() {
        System.out.println(staffMapper.selectByNumber("11311024"));
        System.out.println(staffMapper.selectByNumber("12345678"));
    }

    @Test
    public void selectStaffCount() {
        List<Map<String, Object>> m = staffMapper.selectStaffTypeCount();
        System.out.println(m);
        System.out.println(m);
        for (Map<String, Object> a : m)
            for (Object b : a.values())
                System.out.println(b);

    }
}

