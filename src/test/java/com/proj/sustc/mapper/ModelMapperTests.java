package com.proj.sustc.mapper;

import com.proj.sustc.entity.Model;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ModelMapperTests {
    @Autowired
    private ModelMapper modelMapper;

    @Test
    public void insert(){
        Model model = new Model();
        model.setModel("LaptopC6");
        model.setUnitPrice(340);
        model.setProductNumber("L8N0649");
        modelMapper.insertModel(model);
    }


}
