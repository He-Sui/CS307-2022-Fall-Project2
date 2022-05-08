package com.proj.sustc.service.implement;

import com.proj.sustc.entity.Enterprise;
import com.proj.sustc.mapper.CenterMapper;
import com.proj.sustc.mapper.EnterpriseMapper;
import com.proj.sustc.service.IEnterpriseService;
import com.proj.sustc.service.exception.InsertException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnterpriseServiceImpl implements IEnterpriseService {

    @Autowired
    private EnterpriseMapper enterpriseMapper;
    @Autowired
    private CenterMapper centerMapper;

    @Override
    public void insert(Enterprise enterprise) {
        if (enterprise == null || enterprise.getName() == null || enterprise.getCountry() == null || enterprise.getSupplyCenter() == null || enterprise.getIndustry() == null)
            throw new InsertException("Enterprise information should not be null");
        if (centerMapper.findByCenterName(enterprise.getSupplyCenter()) == null)
            throw new InsertException("Invalid Supply Center [" + enterprise.getSupplyCenter() + "]");
        if (enterpriseMapper.findEnterpriseByName(enterprise.getName()) != null)
            throw new InsertException("Duplicated enterprise name [" + enterprise.getName() + "]");
        if (enterpriseMapper.insert(enterprise) != 1)
            throw new InsertException("Unknown error when insert Enterprise");
    }

    @Override
    public Enterprise selectByName(String name) {
        return enterpriseMapper.findEnterpriseByName(name);
    }
}
