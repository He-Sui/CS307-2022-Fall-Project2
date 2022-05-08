package com.proj.sustc.service;

import com.proj.sustc.entity.Enterprise;

public interface IEnterpriseService {
    /**
     * Insert enterprise
     * @param enterprise information of enterprise
     */
    void insert(Enterprise enterprise);

    /**
     * Select enterprise by name
     * @param name Name of the enterprise
     * @return enterprise find by name
     */
    Enterprise selectByName(String name);


}
