/*package com.proj.sustc.service;


import com.proj.sustc.entity.Center;
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
public class CenterTests {
    @Autowired
    private ICenterService iCenterService;

    @Test
    public void insert() {
        try {
            Center center = new Center();
            center.setName("Southern China");
            iCenterService.insert(center);
        } catch (ServiceException e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void insertAllSupplyCenter() {
        try {
            BufferedReader bf = new BufferedReader(new FileReader("src/test/data/center.csv"));
            Center center = new Center();
            bf.readLine();
            String line;
            String[] info;
            while ((line = bf.readLine()) != null) {
                info = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                if (info[1].equals("\"Hong Kong, Macao and Taiwan regions of China\""))
                    center.setName("Hong Kong, Macao and Taiwan regions of China");
                else
                    center.setName(info[1]);
                try {
                    iCenterService.insert(center);
                } catch (ServiceException e) {
                    System.err.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}*/


package com.proj.sustc.service;


import com.proj.sustc.entity.Center;
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
public class CenterTests {
    @Autowired
    private ICenterService iCenterService;

    @Test
    public void insert() {
        try {
            Center center = new Center();
            center.setName("Southern China");
            iCenterService.insert(center);
        } catch (ServiceException e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void insertAllSupplyCenter() {
        try {
            BufferedReader bf = new BufferedReader(new FileReader("src/test/data/center.csv"));
            Center center = new Center();
            bf.readLine();
            String line;
            String[] info;
            while ((line = bf.readLine()) != null) {
                info = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                if (info[1].equals("\"Hong Kong, Macao and Taiwan regions of China\""))
                    center.setName("Hong Kong, Macao and Taiwan regions of China");
                else
                    center.setName(info[1]);
                try {
                    iCenterService.insert(center);
                } catch (ServiceException e) {
                    System.err.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
