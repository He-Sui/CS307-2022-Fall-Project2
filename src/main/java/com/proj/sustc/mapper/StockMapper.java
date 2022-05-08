package com.proj.sustc.mapper;

import com.proj.sustc.entity.StockInRecord;
import com.proj.sustc.entity.Stock;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface StockMapper {
    /**
     * insert in stock record
     * @param inStockRecord in stock record
     * @return The number of affected rows
     */
    Integer insertRecord(StockInRecord inStockRecord);

    /**
     * insert stock
     * @param stock information of stock
     * @return The number of affected rows
     */
    Integer insertStock(Stock stock);


    /**
     * update stock record
     * @param stock new stock record
     * @return The number of affected rows
     */
    Integer updateStock(Stock stock);

    /**
     * delete stock
     * @param supplyCenter
     * @param productModel
     * @return The number of affected rows
     */
    Integer deleteStock(String supplyCenter, String productModel);

    /**
     * select stock in information
     * @param supplyCenter
     * @param productModel
     * @return stock information
     */
    Stock selectStock(String supplyCenter, String productModel);


    /**
     * The number of product_models that are in stock but never been ordered(sold)
     * @return count of the nuver-sold products
     */
    Integer getNeverSoldProductCount();


    /**
     * For each supply center, calculate the average quantity of the remaining product models. The
     * results should be ordered by the name of the supply centers and rounded to one decimal place.
     * @return supply center and average
     */
    List<Map<String, Object>> getAvgStockByCenter();

    /**
     * Find a product according to the product number and return the current inventory capacity of each
     * product model in each supply center.
     * @param productNumber product number
     * @return supply center, product model and quantity
     */
    List<Map<String, Object>> findProductStockByNumber(String productNumber);
}
