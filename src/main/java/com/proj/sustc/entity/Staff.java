package com.proj.sustc.entity;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Objects;
@Component
public class Staff implements Serializable {
    private Integer id;
    private String number;
    private String name;
    private String phoneNumber;
    private String gender;
    private Integer age;
    private String type;
    private String supplyCenter;

    @Override
    public String toString() {
        return "Staff{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", type='" + type + '\'' +
                ", supplyCenter='" + supplyCenter + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Staff staff = (Staff) o;
        return Objects.equals(id, staff.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, name, phoneNumber, gender, age, type, supplyCenter);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSupplyCenter() {
        return supplyCenter;
    }

    public void setSupplyCenter(String supplyCenter) {
        this.supplyCenter = supplyCenter;
    }
}
