package com.proj.sustc.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import org.springframework.stereotype.Component;

@Component
public class StockInRecord implements Serializable {
    private Integer id;
    private String supplyCenter;
    private Integer purchasePrice;
    private Integer quantity;
    private String productModel;
    private String supplyStaff;
    private Date date;

    @Override
    public String toString() {
        return "InStockRecord{" +
                "id=" + id +
                ", supplyCenter='" + supplyCenter + '\'' +
                ", purchasePrice=" + purchasePrice +
                ", productModel='" + productModel + '\'' +
                ", supplyStaff='" + supplyStaff + '\'' +
                ", date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockInRecord that = (StockInRecord) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, supplyCenter, purchasePrice, productModel, supplyStaff, date);
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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

    public Integer getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Integer purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }

    public String getSupplyStaff() {
        return supplyStaff;
    }

    public void setSupplyStaff(String supplyStaff) {
        this.supplyStaff = supplyStaff;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
