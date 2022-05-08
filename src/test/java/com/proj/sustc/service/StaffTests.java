package com.proj.sustc.service;

import com.proj.sustc.entity.Staff;
import com.proj.sustc.service.exception.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@SpringBootTest
@RunWith(SpringRunner.class)
public class StaffTests {
    @Autowired
    IStaffService iStaffService;

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
        iStaffService.insert(staff);
    }

    @Test
    public void insertAllStaff() {
        try {
            BufferedReader bf = new BufferedReader(new FileReader("src/test/data/staff.csv"));
            Staff staff = new Staff();
            bf.readLine();
            String line;
            String[] info;
            while ((line = bf.readLine()) != null) {
                info = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                staff.setName(info[1]);
                staff.setAge(Integer.valueOf(info[2]));
                staff.setGender(info[3]);
                staff.setNumber(info[4]);
                if (info[5].equals("\"Hong Kong, Macao and Taiwan regions of China\""))
                    staff.setSupplyCenter("Hong Kong, Macao and Taiwan regions of China");
                else
                    staff.setSupplyCenter(info[5]);
                staff.setPhoneNumber(info[6]);
                staff.setType(info[7]);
                try {
                    iStaffService.insert(staff);
                } catch (ServiceException e) {
                    System.err.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getAllStaffCountTests(){
        System.out.println(iStaffService.getAllStaffCount());
    }
}
