package com.proj.sustc.entity;

import java.util.Date;

public class OrdersDisplay {
    public String productModel;
    public String contractNumber;
    public String salesmanNumber;
    public Integer quantity;
    public String estimatedDeliveryDate;
    public String lodgementDate;

    public OrdersDisplay(){
      productModel=null;
      contractNumber=null;
        salesmanNumber=null;
        quantity=0;
        estimatedDeliveryDate=null;
        lodgementDate=null;
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

    public String getEstimatedDeliveryDate() {
        return estimatedDeliveryDate;
    }

    public void setEstimatedDeliveryDate(String estimatedDeliveryDate) {
        this.estimatedDeliveryDate = estimatedDeliveryDate;
    }

    public String getLodgementDate() {
        return lodgementDate;
    }

    public void setLodgementDate(String lodgementDate) {
        this.lodgementDate = lodgementDate;
    }
}
