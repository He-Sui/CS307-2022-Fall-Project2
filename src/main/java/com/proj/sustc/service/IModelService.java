package com.proj.sustc.service;


import com.proj.sustc.entity.Model;
import com.proj.sustc.entity.Product;
import com.proj.sustc.vo.RespBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IModelService {
    /**
     * insert model
     * @param model information of model
     */
    void insertModel(Model model);

    /**
     * select by model number
     * @param number model number
     * @return information of model
     */
    Model selectModelByModelNumber(String number);

    /**
     * insert product
     * @param product information of product
     */
    void insertProduct(Product product);


    /**
     * select by product number
     * @param number product number
     * @return information of product
     */
    Product selectProductByProductNumber(String number);

    RespBean AddModel(HttpServletRequest request, HttpServletResponse response, String model, String product, Integer price);
}
