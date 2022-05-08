package com.proj.sustc.entity;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Objects;
@Component
public class Model implements Serializable {
    private Integer id;
    private String model;
    private Integer unitPrice;
    private String productNumber;

    @Override
    public String toString() {
        return "Model{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", unitPrice=" + unitPrice +
                ", productNumber='" + productNumber + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Model model1 = (Model) o;
        return Objects.equals(id, model1.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, model, unitPrice, productNumber);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }
}
