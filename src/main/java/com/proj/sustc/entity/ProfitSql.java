package com.proj.sustc.entity;

import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class ProfitSql implements Serializable {
    private static final long serialVersionUID = 1L;
    private String supply_center;
    private Integer income;
    private Integer expense;
    private Integer profit;

    public String getSupply_center() {
        return supply_center;
    }

    public void setSupply_center(String supply_center) {
        this.supply_center = supply_center;
    }

    public Integer getIncome() {
        return income;
    }

    public void setIncome(Integer income) {
        this.income = income;
    }

    public Integer getExpense() {
        return expense;
    }

    public void setExpense(Integer expense) {
        this.expense = expense;
    }

    public Integer getProfit() {
        return profit;
    }

    public void setProfit(Integer profit) {
        this.profit = profit;
    }
}
