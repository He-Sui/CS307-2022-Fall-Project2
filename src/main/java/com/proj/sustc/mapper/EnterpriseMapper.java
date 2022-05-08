package com.proj.sustc.mapper;

import com.proj.sustc.entity.Enterprise;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface EnterpriseMapper {

    /**
     * Insert Enterprise into table enterprise
     * @param enterprise The information of enterprise
     * @return Number of affected rows
     */
    Integer insert(Enterprise enterprise);

    /**
     * Select enterprise by name
     * @param name enterprise name
     * @return information of enterprise
     */
    Enterprise findEnterpriseByName(String name);
}
