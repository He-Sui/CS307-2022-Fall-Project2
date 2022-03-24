package com.proj.sustc.entity;

public class TestP1 {
    public String contract_number;
    public String enterprise;
    public String manager;
    public String supply_center;

    public TestP1(){
        contract_number=null;
        enterprise=null;
        manager=null;
        supply_center=null;
    }

    public String getContract_number() {
        return contract_number;
    }

    public void setContract_number(String contract_number) {
        this.contract_number = contract_number;
    }

    public String getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(String enterprise) {
        this.enterprise = enterprise;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getSupply_center() {
        return supply_center;
    }

    public void setSupply_center(String supply_center) {
        this.supply_center = supply_center;
    }
}
