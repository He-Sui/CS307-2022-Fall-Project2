package com.proj.sustc.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@TableName("Role_login_in")
public class Role_login_in implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String login_type;
    private Integer Role_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin_type() {
        return login_type;
    }

    public void setLogin_type(String login_type) {
        this.login_type = login_type;
    }

    public Integer getRole_id() {
        return Role_id;
    }

    public void setRole_id(Integer role_id) {
        Role_id = role_id;
    }
}
