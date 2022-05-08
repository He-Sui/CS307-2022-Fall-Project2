package com.proj.sustc.service.implement;

import com.proj.sustc.entity.Staff;
import com.proj.sustc.entity.Stock;
import com.proj.sustc.entity.StockInRecord;
import com.proj.sustc.mapper.CenterMapper;
import com.proj.sustc.mapper.ModelMapper;
import com.proj.sustc.mapper.StaffMapper;
import com.proj.sustc.mapper.StockMapper;
import com.proj.sustc.service.IStockService;
import com.proj.sustc.service.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class StockServiceImpl implements IStockService {
    @Autowired
    private CenterMapper centerMapper;
    @Autowired
    private StaffMapper staffMapper;
    @Autowired
    private StockMapper stockMapper;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public void stockIn(StockInRecord stockInRecord) {
        if (stockInRecord == null || stockInRecord.getSupplyCenter() == null || stockInRecord.getProductModel() == null || stockInRecord.getSupplyStaff() == null || stockInRecord.getDate() == null || stockInRecord.getPurchasePrice() == null || stockInRecord.getQuantity() == null)
            throw new StockInException("Contains empty information");
        if (centerMapper.findByCenterName(stockInRecord.getSupplyCenter()) == null)
            throw new StockInException("Supply Center does not exist");
        Staff staff = staffMapper.selectByNumber(stockInRecord.getSupplyStaff());
        if (staff == null)
            throw new StockInException("Supply Staff does not exist");
        if (!staff.getSupplyCenter().equals(stockInRecord.getSupplyCenter()))
            throw new StockInException("Supply Center mismatch with Supply Staff");
        if (!staff.getType().equals("Supply Staff"))
            throw new StockInException("Staff type mismatch");
        if (modelMapper.selectModelByModelNumber(stockInRecord.getProductModel()) == null)
            throw new StockInException("Model does not exist");
        if (stockMapper.insertRecord(stockInRecord) != 1)
            throw new InsertException("Unknown error when insert Stock in Record");
        Stock stock = stockMapper.selectStock(stockInRecord.getSupplyCenter(), stockInRecord.getProductModel());
        if (stock == null) {
            stock = new Stock();
            stock.setSupplyCenter(stockInRecord.getSupplyCenter());
            stock.setProductModel(stockInRecord.getProductModel());
            stock.setQuantity(stockInRecord.getQuantity());
            if (stockMapper.insertStock(stock) != 1)
                throw new InsertException("Unknown error when insert Stock");
        } else {
            stock.setQuantity(stock.getQuantity() + stockInRecord.getQuantity());
            if (stockMapper.updateStock(stock) != 1)
                throw new UpdateException("Unknown error when update Stock");
        }
    }

    @Override
    public Integer getNeverSoldProductCount() {
        return stockMapper.getNeverSoldProductCount();
    }

    @Override
    public String getAvgStockByCenter() {
        List<Map<String, Object>> list = stockMapper.getAvgStockByCenter();
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-50s%-10s\n", "supply center", "average"));
        for (Map<String, Object> res : list)
            sb.append(String.format("%-50s%-10s\n", res.get("supply_center"), res.get("average")));
        return sb.toString();
    }

    @Override
    public String getProductByNumber(String productNumber) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-50s%-50s%-10s\n", "supply center", "product model", "quantity"));
        List<Map<String, Object>> list = stockMapper.findProductStockByNumber(productNumber);
        for (Map<String, Object> res : list)
            sb.append(String.format("%-50s%-50s%-10s\n", res.get("supply center"), res.get("product model"), res.get("quantity")));
        return sb.toString();
    }


}
