package com.proj.sustc.mapper;

import com.proj.sustc.entity.Model;
import com.proj.sustc.entity.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ModelMapper {
    /**
     * insert model
     * @param model information of model
     * @return Number of affected rows
     */
    Integer insertModel(Model model);

    /**
     * Select by model number
     * @param model model number
     * @return information of model
     */
    Model selectModelByModelNumber(String model);


    /**
     * Insert product
     * @param product Information of product
     * @return Number of affected rows
     */
    Integer insertProduct(Product product);

    /**
     * select by product number
     * @param number product number
     * @return information of Product
     */
    Product selectProductByNumber(String number);

}
