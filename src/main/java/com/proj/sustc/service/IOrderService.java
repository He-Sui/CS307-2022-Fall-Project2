package com.proj.sustc.service;

import com.proj.sustc.entity.Orders;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IOrderService {
    /**
     * Place an order which sells specific products to an enterprise
     * @param contractNumber The ID of the contract
     * @param enterprise The Name of the client enterprise
     * @param productModel The model of the product which is selled to the client enterprise
     * @param quantity The number of the product which is selled to the client enterprise
     * @param contractManager The ID of the contract manager
     * @param contractDate The date that the contract signed
     * @param estimatedDate Estimated delivery date of the product
     * @param lodgementDate Actual delivery date of the product
     * @param salesmanNumber The ID of the salesman
     * @param contractType Current type of the contract type
     */
    void placeOrder(String contractNumber, String enterprise, String productModel, Integer quantity, String contractManager, Date contractDate, Date estimatedDate, Date lodgementDate, String salesmanNumber, String contractType);


    /**
     * Update orders according to the contract number, product model and salesman number
     * @param orders information of orders to be updated
     */
    void updateOrder(Orders orders);


    /**
     * Delete orders by contract, salesman and seq
     * @param contract The contract number
     * @param salesman The salesman number
     * @param seq The sequence number of order, belonging to the salesman in the given contract, that
     * should be deleted. Specifically, the sequence number is calculated by sort the record in the
     * "order" table with the same contract and salesman by the value of
     * estimate_delivery_date. If several records exist with the same estimated delivery date, sort
     * these records by the lexicographical order of product_model (a-z, A-Z).
     */
    void deleteOrder(String contract, String salesman, Integer seq);


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
     * @return model_name and quantity
     */
    String getFavoriteProductModel();

    /**
     * Find a contract with a contract number and return the content of the contract.
     * @param contractNumber contract number
     * @return contract_number, contract_manager_name, enterprise_name, supply_center
     * All orders in contract including:
     * Product_model, salesman_name, quantity, unit_price, estimate_delivery_date, lodgement_date.
     */
    String getContractInfo(String contractNumber);

    /**
     * Update contract type
     */
    void updateContractType();
}
