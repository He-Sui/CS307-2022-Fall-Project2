package com.proj.sustc.service;

import com.proj.sustc.entity.StockInRecord;
import org.springframework.stereotype.Service;

public interface IStockService {
    /**
     * Increase the product inventory
     * @param stockInRecord stock in record
     */
    void stockIn(StockInRecord stockInRecord);


    /**
     * The number of product_models that are in stock but have never been ordered(sold).
     * @return count of the never-sold productss
     */
    Integer getNeverSoldProductCount();

    /**
     * For each supply center, calculate the average quantity of the remaining product models. The
     * results should be ordered by the name of the supply centers and rounded to one decimal place.
     * @return supply center and average
     */
    String getAvgStockByCenter();

    /**
     * Find a product according to the product number and return the current inventory capacity of each
     * product model in each supply center.
     * @param productNumber product number
     * @return supply center, product model and quantity
     */
    String getProductByNumber(String productNumber);
}
