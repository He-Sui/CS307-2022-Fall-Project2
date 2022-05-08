package com.proj.sustc.service;

import com.proj.sustc.entity.Enterprise;
import com.proj.sustc.entity.Model;
import com.proj.sustc.entity.Product;
import com.proj.sustc.service.exception.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ModelTests {
    @Autowired
    private IModelService iModelService;

    @Test
    public void insertAllProductAndModel() {
        try {
            BufferedReader bf = new BufferedReader(new FileReader("src/test/data/model.csv"));
            bf.readLine();
            String line;
            String[] info;
            Model model = new Model();
            Product product = new Product();
            Set<String> productNumber = new HashSet<>();
            while ((line = bf.readLine()) != null) {
                info = line.split(",");
                if (!productNumber.contains(info[1])) {
                    product.setNumber(info[1]);
                    product.setName(info[3]);
                    productNumber.add(info[1]);
                    try {
                        iModelService.insertProduct(product);
                    } catch (ServiceException e) {
                        System.err.println(e.getMessage());
                    }
                }
                model.setModel(info[2]);
                model.setProductNumber(info[1]);
                model.setUnitPrice(Integer.valueOf(info[4]));
                try {
                    iModelService.insertModel(model);
                } catch (ServiceException e) {
                    System.err.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
