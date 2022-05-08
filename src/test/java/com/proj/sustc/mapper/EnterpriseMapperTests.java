package com.proj.sustc.mapper;

import com.proj.sustc.entity.Enterprise;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class EnterpriseMapperTests {
    @Autowired
    private EnterpriseMapper enterpriseMapper;

    @Test
    public void insert(){
        Enterprise enterprise = new Enterprise();
        enterprise.setName("Sinopec");
        enterprise.setCountry("China");
        enterprise.setCity("Beijing");
        enterprise.setSupplyCenter("Northern China");
        enterprise.setIndustry("Petrochemical");
        enterpriseMapper.insert(enterprise);
    }

    @Test
    public void select(){
        System.out.println(enterpriseMapper.findEnterpriseByName("Sinopec"));
    }
}
