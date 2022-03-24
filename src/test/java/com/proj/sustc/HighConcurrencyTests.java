package com.proj.sustc;

import com.proj.sustc.entity.StockInRecord;
import com.proj.sustc.service.IOrderService;
import com.proj.sustc.service.IStaffService;
import com.proj.sustc.service.IStockService;
import net.sourceforge.groboutils.junit.v1.MultiThreadedTestRunner;
import net.sourceforge.groboutils.junit.v1.TestRunnable;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.util.Date;
import java.util.Random;

@SpringBootTest
@RunWith(SpringRunner.class)
class HighConcurrencyTests {
    @Autowired
    DataSource dataSource;
    @Autowired
    IStaffService iStaffService;
    @Autowired
    IOrderService iOrderService;
    @Autowired
    IStockService iStockService;

    @Test
    public void methodATest() throws Throwable {
        // 构造一个Runner
        TestRunnable runner = new TestRunnable() {
            @Override
            public void runTest() throws Throwable {
                System.out.println(dataSource.getConnection());
            }
        };
        // 并发线程数量
        int runnerCount = 100;
        TestRunnable[] trs = new TestRunnable[runnerCount];
        for (int i = 0; i < runnerCount; i++) {
            trs[i] = runner;
        }
        // 用于执行多线程测试用例的Runner，将前面定义的单个Runner组成的数组传入
        MultiThreadedTestRunner mttr = new MultiThreadedTestRunner(trs);
        // 并发执行测试内容
        mttr.runTestRunnables();
    }

    @Test
    public void placeOrder() {
        StockInRecord stockInRecord = new StockInRecord();
        stockInRecord.setSupplyCenter("Northern China");
        stockInRecord.setPurchasePrice(756);
        stockInRecord.setQuantity(50000);
        stockInRecord.setProductModel("LcdTvV7");
        stockInRecord.setSupplyStaff("11911609");
        stockInRecord.setDate(new Date());

        iStockService.stockIn(stockInRecord);
    }

    @Test
    public void testInsertOrder() throws Throwable {
        TestRunnable runner = new TestRunnable() {
            final Random random = new Random();

            @Override
            public void runTest() throws Throwable {
                boolean check = false;
                int count = 0;
                while (!check && count < 5) {
                    ++count;
                    try {
                        iOrderService.placeOrder("CSE0000304", "Industrial and Commercial Bank of China", "LcdTvV7", random.nextInt(4) + 1, "12210020"
                                , new Date(), new Date(), new Date(), "11812007");
                        check = true;
                    } catch (RuntimeException ignored) {
                    }
                }
            }
        };
        int runnerCount = 30000;
        TestRunnable[] trs = new TestRunnable[runnerCount];
        for (int i = 0; i < runnerCount; i++) {
            trs[i] = runner;
        }
        MultiThreadedTestRunner mttr = new MultiThreadedTestRunner(trs);
        mttr.runTestRunnables();
    }
}
