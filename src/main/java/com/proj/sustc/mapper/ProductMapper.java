package com.proj.sustc.mapper;

import com.proj.sustc.entity.Product;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper {
    Product SelectProductByNumber(String number);
}
