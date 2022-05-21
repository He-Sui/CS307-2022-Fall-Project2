package com.proj.sustc.object;

public class ProductModelCount {

    public String model;

    public String product;

    public int count;

    public int unitPrice;

    public ProductModelCount(){
        model=null;
        count=0;
    }
  public int getUnitPrice(){
        return unitPrice;
  }
  public void setUnitPrice(int unitPrice){
        this.unitPrice=unitPrice;
  }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getProduct(){
        return product;
    }

    public void setProduct(String product){
        this.product=product;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
