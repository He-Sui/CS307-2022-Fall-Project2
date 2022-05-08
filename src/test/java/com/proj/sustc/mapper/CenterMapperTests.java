package com.proj.sustc.mapper;

import com.proj.sustc.entity.Center;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CenterMapperTests {
    @Autowired
    private CenterMapper centerMapper;

    @Test
    public void insert(){
        Center center = new Center();
//        center.setName("America");
//        centerMapper.insert(center);
        center.setName("Eastern China");
        centerMapper.insert(center);
        center.setName("Asia");
        centerMapper.insert(center);
//        centerMapper.insert(new Center("America"));
//        centerMapper.insert(new Center("Eastern China"));
//        centerMapper.insert(new Center("Asia"));
//        centerMapper.insert(new Center("Southern China"));
//        centerMapper.insert(new Center("Northern China"));
//        centerMapper.insert(new Center("Europe"));
//        centerMapper.insert(new Center("Southwestern China"));
//        centerMapper.insert(new Center("Hong Kong, Macao and Taiwan regions of China"));
    }

    @Test
    public void find() {
        System.out.println(centerMapper.findByCenterName("America"));
        System.out.println(centerMapper.findByCenterName("Eastern China"));
        System.out.println(centerMapper.findByCenterName("a"));
    }
}
