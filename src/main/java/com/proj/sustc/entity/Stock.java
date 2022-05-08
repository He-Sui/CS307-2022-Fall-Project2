package com.proj.sustc.entity;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Objects;

@Component
public class Stock implements Serializable {
    private Integer id;
    private String supplyCenter;
    private String productModel;
    private Integer quantity;

    @Override
    public String toString() {
        return "Stock{" +
                "id=" + id +
                ", supplyCenter='" + supplyCenter + '\'' +
                ", productModel='" + productModel + '\'' +
                ", quantity=" + quantity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return Objects.equals(supplyCenter, stock.supplyCenter) && Objects.equals(productModel, stock.productModel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, supplyCenter, productModel, quantity);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSupplyCenter() {
        return supplyCenter;
    }

    public void setSupplyCenter(String supplyCenter) {
        this.supplyCenter = supplyCenter;
    }

    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
