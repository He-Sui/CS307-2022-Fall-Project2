package com.proj.sustc.entity;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Objects;

@Component
public class Enterprise implements Serializable {
    private Integer id;
    private String name;
    private String country;
    private String city;
    private String supplyCenter;
    private String industry;

//    public Enterprise(String name, String country, String city, String supplyCenter, String industry) {
//        this.name = name;
//        this.country = country;
//        this.city = city;
//        this.supplyCenter = supplyCenter;
//        this.industry = industry;
//    }
//
//    public Enterprise(String name, String country, String supplyCenter, String industry) {
//        this(name, country, null, supplyCenter, industry);
//    }

    @Override
    public String toString() {
        return "Enterprise{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", supplyCenter='" + supplyCenter + '\'' +
                ", industry='" + industry + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Enterprise that = (Enterprise) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, country, city, supplyCenter, industry);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSupplyCenter() {
        return supplyCenter;
    }

    public void setSupplyCenter(String supplyCenter) {
        this.supplyCenter = supplyCenter;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }
}
