package com.proj.sustc.service;

import com.proj.sustc.entity.Enterprise;
import com.proj.sustc.service.exception.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

@SpringBootTest
@RunWith(SpringRunner.class)
public class EnterpriseTests {
    @Autowired
    IEnterpriseService iEnterpriseService;

    @Test
    public void insert() {
        Enterprise enterprise = new Enterprise();
        enterprise.setName("State Power Corporation");
        enterprise.setCountry("China");
        enterprise.setCity("Beijing");
        enterprise.setSupplyCenter("Northern China");
        enterprise.setIndustry("Petrochemical");
        iEnterpriseService.insert(enterprise);
    }

    @Test
    public void insertAllEnterprise() {
        try {
            BufferedReader bf = new BufferedReader(new FileReader("src/test/data/enterprise.csv"));
            bf.readLine();
            String line;
            String[] info;
            ArrayList<Enterprise> enterprises = new ArrayList<>();
            while ((line = bf.readLine()) != null) {
                info = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                Enterprise enterprise = new Enterprise();
                enterprise.setId(Integer.valueOf(info[0]));
                enterprise.setName(info[1]);
                enterprise.setCountry(info[2]);
                if (!info[3].equals(""))
                    enterprise.setCity(info[3]);
                enterprise.setSupplyCenter(info[4]);
                if (info[4].equals("\"Hong Kong, Macao and Taiwan regions of China\""))
                    enterprise.setSupplyCenter("Hong Kong, Macao and Taiwan regions of China");
                else
                    enterprise.setSupplyCenter(info[4]);
                enterprise.setIndustry(info[5]);
                enterprises.add(enterprise);
            }
            enterprises.sort(new Comparator<Enterprise>() {
                @Override
                public int compare(Enterprise o1, Enterprise o2) {
                    return Integer.compare(o1.getId(), o2.getId());
                }
            });
            for (Enterprise enterprise : enterprises)
                try {
                    iEnterpriseService.insert(enterprise);
                } catch (ServiceException e) {
                    System.err.println(e.getMessage());
                }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
