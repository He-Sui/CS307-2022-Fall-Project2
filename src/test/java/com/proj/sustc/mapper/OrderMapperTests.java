package com.proj.sustc.mapper;

import com.proj.sustc.entity.Contract;
import com.proj.sustc.entity.Orders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderMapperTests {

    @Autowired
    private OrderMapper orderMapper;


    @Test
    public void insertContract(){
        Contract contract = new Contract();
        contract.setContractManager("12112115");
        contract.setContractType("Finished");
        contract.setNumber("CSE0000101");
        contract.setDate(new Date(2022,1,1));
        contract.setEnterpriseName("ENI");
        orderMapper.insertContract(contract);
    }
    @Test
    public void insertOrders(){
        Orders orders = new Orders();
        orders.setContractNumber("CSE0000101");
        orders.setQuantity(1);
        orders.setProductModel("ElectricKettleR3");
        orders.setEstimatedDeliveryDate(new Date("2022/1/6"));
        orders.setLodgementDate(new Date("2022/1/6"));
        orders.setSalesmanNumber("11610829");
        orderMapper.insertOrders(orders);
    }

    @Test
    public void select(){
        System.out.println(orderMapper.selectContractByNumber("CSE0000101"));
    }

    @Test
    public void getContractCountTests(){
        System.out.println(orderMapper.getContractCount());
    }

    @Test
    public void getFavoriteProductModel(){
        System.out.println(orderMapper.getFavoriteProductModel());
    }
}
