package com.proj.sustc.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Objects;

@Component
public class Center implements Serializable {
    private Integer id;
    private String name;

    @Override
    public String toString() {
        return "Center{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Center center = (Center) o;
        return Objects.equals(id, center.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
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
}
