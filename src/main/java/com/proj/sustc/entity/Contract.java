package com.proj.sustc.entity;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Component
public class Contract implements Serializable {
    private Integer id;
    private String number;
    private String enterpriseName;
    private Date date;
    private String contractType;
    private String contractManager;

    @Override
    public String toString() {
        return "Contract{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", enterpriseName='" + enterpriseName + '\'' +
                ", date=" + date +
                ", contractType='" + contractType + '\'' +
                ", contractManager='" + contractManager + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contract contract = (Contract) o;
        return Objects.equals(id, contract.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, enterpriseName, date, contractType, contractManager);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getContractManager() {
        return contractManager;
    }

    public void setContractManager(String contractManager) {
        this.contractManager = contractManager;
    }
}
