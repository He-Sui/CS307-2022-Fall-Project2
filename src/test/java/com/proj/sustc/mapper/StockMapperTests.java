package com.proj.sustc.mapper;

import com.proj.sustc.entity.StockInRecord;
import com.proj.sustc.entity.Stock;
import com.proj.sustc.entity.StockInRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class StockMapperTests {
    @Autowired
    private StockMapper stockMapper;

    @Test
    public void insertRecord(){
        StockInRecord inStockRecord = new StockInRecord();
        inStockRecord.setSupplyCenter("Asia");
        inStockRecord.setProductModel("Repeater97");
        inStockRecord.setSupplyStaff("11210906");
        inStockRecord.setDate(new Date(2008,10,27));
        inStockRecord.setPurchasePrice(430);
        inStockRecord.setQuantity(801);
        stockMapper.insertRecord(inStockRecord);
    }

    @Test
    public void insertStock(){
        Stock stock = new Stock();
        stock.setQuantity(801);
        stock.setProductModel("Repeater97");
        stock.setSupplyCenter("Asia");
        stockMapper.insertStock(stock);
    }

    @Test
    public void updateStock(){
        Stock stock = new Stock();
        stock.setQuantity(100);
        stock.setProductModel("Repeater97");
        stock.setSupplyCenter("Asia");
        stockMapper.updateStock(stock);
    }

    @Test
    public void deleteStock(){
        stockMapper.deleteStock("Asia", "Repeater97");
    }
}
