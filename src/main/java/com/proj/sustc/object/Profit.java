package com.proj.sustc.object;

public class Profit {
    public String areas;
    public long profits;
    public long expense;
    public long income;

    public long getExpense() {
        return expense;
    }

    public void setExpense(long expense) {
        this.expense = expense;
    }

    public long getIncome() {
        return income;
    }

    public void setIncome(long income) {
        this.income = income;
    }

    public Profit(){
        areas=null;
        profits=0;
    }

    public String getAreas() {
        return areas;
    }

    public void setAreas(String areas) {
        this.areas = areas;
    }

    public long getProfits() {
        return profits;
    }

    public void setProfits(long profits) {
        this.profits = profits;
    }
}
