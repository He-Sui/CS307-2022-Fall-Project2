package com.proj.sustc.service;

import com.proj.sustc.entity.Stock;
import com.proj.sustc.entity.StockInRecord;
import com.proj.sustc.entity.TestO;
import com.proj.sustc.vo.RespBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface IStockService {
    /**
     * Increase the product inventory
     *
     * @param stockInRecord stock in record
     */
    void stockIn(StockInRecord stockInRecord);


    /**
     * The number of product_models that are in stock but have never been ordered(sold).
     *
     * @return count of the never-sold productss
     */
    Integer getNeverSoldProductCount();

    /**
     * For each supply center, calculate the average quantity of the remaining product models. The
     * results should be ordered by the name of the supply centers and rounded to one decimal place.
     *
     * @return supply center and average
     */
    List<Map<String, Object>> getAvgStockByCenter();

    /**
     * Find a product according to the product number and return the current inventory capacity of each
     * product model in each supply center.
     *
     * @param productNumber product number
     * @return supply center, product model and quantity
     */
    List<Map<String, Object>> getProductByNumber(String productNumber);

    RespBean doSelectModel(HttpServletRequest request, HttpServletResponse response, String stockModel, String SupplyCenter);

    List<Stock> getStockListByCookie(HttpServletRequest request, HttpServletResponse response, String stockModel);

    RespBean DeleteModel(HttpServletRequest request, HttpServletResponse response, String model);

    List<TestO>  getTestObyCookie(HttpServletResponse response,HttpServletRequest request,String TestO);

}