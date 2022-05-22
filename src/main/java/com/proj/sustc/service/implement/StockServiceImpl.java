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
import com.proj.sustc.utils.CookieUtil;
import com.proj.sustc.utils.UUIDUtil;
import com.proj.sustc.vo.RespBean;
import com.proj.sustc.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    @Autowired
    private RedisTemplate redisTemplate;

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
    public List<Map<String, Object>> getProductByNumber(String productNumber) {
        return stockMapper.findProductStockByNumber(productNumber);
    }

    @Override
    public RespBean doSelectModel(HttpServletRequest request, HttpServletResponse response, String stockModel, String SupplyCenter) {
        List<Stock> stockList = stockMapper.SelectModelByModelAndSupplyCenter(stockModel, SupplyCenter);
        //开始判断Supply是否为“”，空的话还要返回一个总和
        if (SupplyCenter.equals("")) {
            Stock AllAreas = new Stock();
            AllAreas.setProductModel(stockModel);
            AllAreas.setSupplyCenter("Company");
            AllAreas.setQuantity(0);
            Integer quantity = 0;
            AllAreas.setId(0);
            for (Stock stock : stockList) {
                quantity += stock.getQuantity();
            }
            AllAreas.setQuantity(quantity);
            stockList.add(AllAreas);
        }
        //开始用Cookie存
        String StockModel = UUIDUtil.uuid();
        redisTemplate.opsForValue().set(StockModel, stockList);
        CookieUtil.setCookie(request, response, "STOCK_MODEL", StockModel);
        return RespBean.operation_success(RespBeanEnum.OPERATION_SUCCESS);
    }

    @Override
    public List<Stock> getStockListByCookie(HttpServletRequest request, HttpServletResponse response, String stockModel) {
        if (stockModel == null) {
            return null;
        }
        List<Stock> stockList = (List<Stock>) redisTemplate.opsForValue().get(stockModel);
        return stockList;
    }

}
