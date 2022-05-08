package com.proj.sustc.service;

import com.proj.sustc.entity.StockInRecord;

public interface IInStockRecordService {
    /**
     * insert in stock record
     * @param inStockRecord information of record
     */
    void insert(StockInRecord inStockRecord);
}
