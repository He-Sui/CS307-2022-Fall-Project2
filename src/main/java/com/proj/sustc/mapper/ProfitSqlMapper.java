package com.proj.sustc.mapper;

import com.proj.sustc.entity.ProfitSql;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface ProfitSqlMapper {

   List<ProfitSql> SelectProfit(@Param("start")Date start,@Param("end")Date end);
}
