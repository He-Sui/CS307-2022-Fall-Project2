package com.proj.sustc.object;

public class StaffSell {
   public String number;
   public String name;
    public long sales;
    public long count;

    public StaffSell(){
        number=null;
        name=null;
        sales=0;
        count=0;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSales() {
        return sales;
    }

    public void setSales(long sales) {
        this.sales = sales;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
