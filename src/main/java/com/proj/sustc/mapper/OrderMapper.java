package com.proj.sustc.mapper;

import com.proj.sustc.entity.Contract;
import com.proj.sustc.entity.Orders;
import com.proj.sustc.entity.ProfitSql;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {
    /**
     * insert order
     * @param orders the information of order
     * @return number of affeccted rows
     */
    Integer insertOrders(Orders orders);

    /**
     * insert contract
     * @param contract the information of contract
     * @return number of affected rows
     */
    Integer insertContract(Contract contract);


    /**
     * select contract by contract number
     * @param contractNumber contract number
     * @return contract information
     */
    Contract selectContractByNumber(String contractNumber);


    /**
     * Select orders by contract number, product model and salesman number
     * @param contractNumber contract number
     * @param productModel product model
     * @param salesmanNumber salesman number
     * @return orders
     */
    Orders selectOrders(String contractNumber, String productModel, String salesmanNumber);


    /**
     * Delete orders by contract number, product model and salesman number
     * @param contractNumber contract number
     * @param productModel product model
     * @param salesmanNumber salesman number
     * @return the number of affected rows
     */
    Integer deleteOrder(String contractNumber, String productModel, String salesmanNumber);

    /**
     * Select orders by contract number, salesman number and seq
     * @param contractNumber contract number
     * @param salesmanNumber salesman number
     * @param seq seq order by estimate delivery date and product model
     * @return information of order
     */
    Orders selectOrdersByContractSalesmanSeq(String contractNumber, String salesmanNumber, Integer seq);


    /**
     * Return the total number of existing contracts
     * @return count of the existing contracts
     */
    Integer getContractCount();


    /**
     * Return the total number of existing orders
     * @return count of the existing orders
     */
    Integer getOrderCount();


    /**
     * Find the models with the highest sold quantity, and the number of sales.
     * @return model_name, quantity
     */
    List<Map<String, Object>> getFavoriteProductModel();


    /**
     * Find a contract with a contract number and return the content of the contract.
     * @param contractNumber contract number
     * @return enterprise name, name, manager name, supply center
     */
    Map<String, Object> selectContractInfo(String contractNumber);

    /**
     * Find a contract with a contract number and return the content of related orders
     * @param contractNumber contract number
     * @return product model, salesman, quantity, unit price, estimated delivery date, lodgement date
     */
    List<Map<String, Object>> selectOrderInfo(String contractNumber);

    /**
     * Update order by contract number, product model and salesman number
     * @param orders information of order
     * @return The number of affected rows
     */
    Integer updateOrder(Orders orders);

    /**
     * Update the type of the contract
     * @return The number of affected rows
     */
    Integer updateContractType();

    /**
     * Find all the orders
     * @param
     * @return Object of Orders
     */
    List<Orders> SelectAllOrders();

    List<ProfitSql> SelectProfit(@Param("start") Date start, @Param("end")Date end);

    List<Orders> SelectOrderByModelAndArea(@Param("area")String area,@Param("model")String model);
}
