package com.proj.sustc.entity;

public class TestO {
    public String area;
    public String model;
    public Integer quantity;

    public TestO(){
        area=null;
        model=null;
        quantity=0;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
