package com.proj.sustc.service;

import com.proj.sustc.entity.StockInRecord;
import com.proj.sustc.service.exception.ServiceException;
import com.proj.sustc.service.implement.StockServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class StockServiceTests {
    @Autowired
    private StockServiceImpl stockService;

    @Test
    public void in_stoke_test() {
        try {
            BufferedReader bf = new BufferedReader(new FileReader("src/test/data/task1_in_stoke_test_data_publish.csv"));
            bf.readLine();
            String line;
            String[] info;
            StockInRecord stockInRecord = new StockInRecord();
            while ((line = bf.readLine()) != null) {
                info = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                if (info[1].equals("\"Hong Kong, Macao and Taiwan regions of China\""))
                    stockInRecord.setSupplyCenter("Hong Kong, Macao and Taiwan regions of China");
                else
                    stockInRecord.setSupplyCenter(info[1]);
                stockInRecord.setProductModel(info[2]);
                stockInRecord.setSupplyStaff(info[3]);
                stockInRecord.setDate(new Date(info[4]));
                stockInRecord.setPurchasePrice(Integer.valueOf(info[5]));
                stockInRecord.setQuantity(Integer.valueOf(info[6]));
                try {
                    stockService.stockIn(stockInRecord);
                } catch (ServiceException e) {
                    System.err.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getNeverSoldProductCountTest() {
        System.out.println(stockService.getNeverSoldProductCount());
    }

    @Test
    public void getAvgStockByCenter() {
        System.out.println(stockService.getAvgStockByCenter());
    }

    @Test
    public void getProductByNumberTest(){
        System.out.println(stockService.getProductByNumber("A50L172"));
    }
}
