package com.proj.sustc.service;

import com.proj.sustc.entity.Center;

public interface ICenterService {
    /**
     * insert center
     * @param center information to insert
     */
    void insert(Center center);


    /**
     * select by center name
     * @param name center name
     * @return center
     */
    Center select(String name);
}
