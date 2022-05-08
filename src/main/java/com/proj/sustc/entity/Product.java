package com.proj.sustc.entity;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Objects;
@Component
public class Product implements Serializable {
    private Integer id;
    private String number;
    private String name;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, name);
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
}
