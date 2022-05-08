package com.proj.sustc.entity;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
@Component
public class Orders implements Serializable {
    private Integer id;
    private String productModel;
    private String contractNumber;
    private String salesmanNumber;
    private Integer quantity;
    private Date estimatedDeliveryDate;
    private Date lodgementDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Orders orders = (Orders) o;
        return Objects.equals(id, orders.id) && Objects.equals(productModel, orders.productModel) && Objects.equals(contractNumber, orders.contractNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productModel, contractNumber, salesmanNumber, quantity, estimatedDeliveryDate, lodgementDate);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getSalesmanNumber() {
        return salesmanNumber;
    }

    public void setSalesmanNumber(String salesmanNumber) {
        this.salesmanNumber = salesmanNumber;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Date getEstimatedDeliveryDate() {
        return estimatedDeliveryDate;
    }

    public void setEstimatedDeliveryDate(Date estimatedDeliveryDate) {
        this.estimatedDeliveryDate = estimatedDeliveryDate;
    }

    public Date getLodgementDate() {
        return lodgementDate;
    }

    public void setLodgementDate(Date lodgementDate) {
        this.lodgementDate = lodgementDate;
    }
}
