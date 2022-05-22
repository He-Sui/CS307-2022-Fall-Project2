package com.proj.sustc.service;

import com.proj.sustc.entity.Orders;
import com.proj.sustc.service.exception.ServiceException;
import com.proj.sustc.service.implement.OrderServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderTests {
    @Autowired
    private OrderServiceImpl orderService;

    @Test
    public void placeOrderTest() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            BufferedReader bf = new BufferedReader(new FileReader("src/test/data/task2_test_data_publish.csv"));
            bf.readLine();
            String line;
            String[] info;
            while ((line = bf.readLine()) != null) {
                info = line.split(",");
                try {
                    if (info[7].equals(""))
                        orderService.placeOrder(info[0], info[1], info[2], Integer.valueOf(info[3]), info[4], simpleDateFormat.parse(info[5]), simpleDateFormat.parse(info[6]), null, info[8]);
                    else {
                        orderService.placeOrder(info[0], info[1], info[2], Integer.valueOf(info[3]), info[4], simpleDateFormat.parse(info[5]), simpleDateFormat.parse(info[6]), simpleDateFormat.parse(info[7]), info[8]);
                    }
                } catch (ServiceException e) {
                    System.err.println(e.getMessage());
                }

            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void updateOrderTest() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            BufferedReader bf = new BufferedReader(new FileReader("src/test/data/task34_update_test_data_publish.tsv"));
            bf.readLine();
            String line;
            String[] info;
            Orders orders = new Orders();
            while ((line = bf.readLine()) != null) {
                info = line.split("\t");
                orders.setContractNumber(info[0]);
                orders.setProductModel(info[1]);
                orders.setSalesmanNumber(info[2]);
                orders.setQuantity(Integer.valueOf(info[3]));
                orders.setEstimatedDeliveryDate(simpleDateFormat.parse(info[4]));
                if (!info[5].equals(""))
                    orders.setLodgementDate(simpleDateFormat.parse(info[5]));
                else
                    orders.setLodgementDate(null);
                try {
                    orderService.updateOrder(orders);
                } catch (ServiceException e) {
                    System.err.println(e.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void deleteOrderTest() {
        try {
            BufferedReader bf = new BufferedReader(new FileReader("src/test/data/task34_delete_test_data_publish.tsv"));
            bf.readLine();
            String line;
            String[] info;
            while ((line = bf.readLine()) != null) {
                info = line.split("\t");
                try {
                    orderService.deleteOrder(info[0], info[1], Integer.valueOf(info[2]));
                } catch (ServiceException e) {
                    System.err.println(e.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void getContractCountTest() {
        System.out.println(orderService.getContractCount());
    }


    @Test
    public void getOrderCountTest() {
        System.out.println(orderService.getOrderCount());
    }

    @Test
    public void getFavoriteProductModelTest() {
        System.out.println(orderService.getFavoriteProductModel());
    }

    @Test
    public void getContractInfoTest() {
        System.out.println(orderService.getContractInfo("CSE0000106"));
        System.out.println(orderService.getContractInfo("CSE0000209"));
        System.out.println(orderService.getContractInfo("CSE0000306"));
    }
}
