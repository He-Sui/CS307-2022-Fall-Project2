package com.proj.sustc.service.implement;

import com.proj.sustc.entity.Center;
import com.proj.sustc.mapper.CenterMapper;
import com.proj.sustc.service.ICenterService;
import com.proj.sustc.service.exception.InsertException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CenterServiceImpl implements ICenterService {
    @Autowired
    CenterMapper centerMapper;

    @Override
    public void insert(Center center) {
        if (center == null || center.getName() == null)
            throw new InsertException("Center name should not be null");
        if (centerMapper.findByCenterName(center.getName()) != null)
            throw new InsertException("Insert duplicated Supply Center name [" + center.getName() + "]");
        if (centerMapper.insert(center) != 1)
            throw new InsertException("Unknown error when insert Supply Center");
    }

    @Override
    public Center select(String name) {
        return centerMapper.findByCenterName(name);
    }
}
