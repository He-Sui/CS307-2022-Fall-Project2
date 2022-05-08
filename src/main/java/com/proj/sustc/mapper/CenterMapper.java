package com.proj.sustc.mapper;

import com.proj.sustc.entity.Center;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CenterMapper {
    /**
     * Find Supply Center according to the center name.
     * @param name Name of Center
     * @return The information of this center, else return null
     */
    Center findByCenterName(String name);

    /**
     * Insert center
     * @param center The information of center
     * @return Number of affected rows
     */
    Integer insert(Center center);
}
