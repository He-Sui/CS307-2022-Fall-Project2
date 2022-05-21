package com.proj.sustc.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@TableName("Role")
public class Role implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String role;
    private String desc_name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDesc_name() {
        return desc_name;
    }

    public void setDesc_name(String desc_name) {
        this.desc_name = desc_name;
    }
}
