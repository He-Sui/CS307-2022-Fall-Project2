package com.proj.sustc.service.implement;

import com.proj.sustc.entity.*;
import com.proj.sustc.mapper.*;
import com.proj.sustc.service.IOrderService;
import com.proj.sustc.service.exception.DeleteException;
import com.proj.sustc.service.exception.InsertException;
import com.proj.sustc.service.exception.PlaceOrderException;
import com.proj.sustc.service.exception.UpdateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private StaffMapper staffMapper;
    @Autowired
    private EnterpriseMapper enterpriseMapper;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private StockMapper stockMapper;

    @Override
    @Transactional
    public void placeOrder(String contractNumber, String enterpriseName, String productModel, Integer quantity, String contractManager, Date contractDate, Date estimatedDate, Date lodgementDate, String salesmanNumber, String contractType) {
        if (contractNumber == null || enterpriseName == null || productModel == null || quantity == null || contractManager == null || contractDate == null || estimatedDate == null || salesmanNumber == null || contractType == null)
            throw new PlaceOrderException("Order information should not be null");
        Staff salesman = staffMapper.selectByNumber(salesmanNumber);
        Staff manager = staffMapper.selectByNumber(contractManager);
        Enterprise enterprise = enterpriseMapper.findEnterpriseByName(enterpriseName);
        Model model = modelMapper.selectModelByModelNumber(productModel);
        if (salesman == null)
            throw new PlaceOrderException("Salesman does not exist");
        if (manager == null)
            throw new PlaceOrderException("Contracts Manager does not exist");
        if (enterprise == null)
            throw new PlaceOrderException("Enterprise does not exist");
        if (model == null)
            throw new PlaceOrderException("Model does not exist");
        if (!manager.getSupplyCenter().equals(enterprise.getSupplyCenter()))
            throw new PlaceOrderException("Supply center mismatch on Contract Manager and Enterprise");
        if (!salesman.getSupplyCenter().equals(enterprise.getSupplyCenter()))
            throw new PlaceOrderException("Supply Center mismatch on Salesman and Enterprise");
        if (!manager.getType().equals("Contracts Manager"))
            throw new PlaceOrderException("Staff type mismatch Contract Manager");
        if (!salesman.getType().equals("Salesman"))
            throw new PlaceOrderException("Staff type mismatch Salesman");
        Stock stock = stockMapper.selectStock(salesman.getSupplyCenter(), productModel);
        if (stock == null || stock.getQuantity() < quantity)
            throw new PlaceOrderException("Quantity in this order larger than the stock");
        stock.setQuantity(stock.getQuantity() - quantity);
        if (stock.getQuantity() == 0) {
            if (stockMapper.deleteStock(stock.getSupplyCenter(), stock.getProductModel()) != 1)
                throw new DeleteException("Unknown error when delete stock");
        } else if (stockMapper.updateStock(stock) != 1)
            throw new UpdateException("Unknown error when update stock");
        if (orderMapper.selectContractByNumber(contractNumber) == null) {
            Contract contract = new Contract();
            contract.setEnterpriseName(enterpriseName);
            contract.setContractType(contractType);
            contract.setContractManager(contractManager);
            contract.setDate(contractDate);
            contract.setNumber(contractNumber);
            if (orderMapper.insertContract(contract) != 1)
                throw new InsertException("Unknown error when insert contract");
        }
        Orders orders = new Orders();
        orders.setProductModel(productModel);
        orders.setContractNumber(contractNumber);
        orders.setSalesmanNumber(salesmanNumber);
        orders.setQuantity(quantity);
        orders.setEstimatedDeliveryDate(estimatedDate);
        orders.setLodgementDate(lodgementDate);
        if (orderMapper.insertOrders(orders) != 1)
            throw new InsertException("Unknown error when insert order");
    }

    @Override
    @Transactional
    public void updateOrder(Orders orders) {
        if (orders == null || orders.getContractNumber() == null || orders.getProductModel() == null || orders.getSalesmanNumber() == null || orders.getQuantity() == null || orders.getEstimatedDeliveryDate() == null)
            throw new UpdateException("Order information should not be null");
        Orders originOrder = orderMapper.selectOrders(orders.getContractNumber(), orders.getProductModel(), orders.getSalesmanNumber());
        if (originOrder == null)
            throw new UpdateException("Can not find order by contract number, product model and salesman number");
        Staff salesman = staffMapper.selectByNumber(orders.getSalesmanNumber());
        Stock stock = stockMapper.selectStock(salesman.getSupplyCenter(), orders.getProductModel());
        if (stock == null && Objects.equals(orders.getQuantity(), originOrder.getQuantity()))
            return;
        if (stock == null && orders.getQuantity() < originOrder.getQuantity()) {
            stock = new Stock();
            stock.setSupplyCenter(salesman.getSupplyCenter());
            stock.setProductModel(orders.getProductModel());
            stock.setQuantity(originOrder.getQuantity() - orders.getQuantity());
            if (stockMapper.insertStock(stock) != 1)
                throw new InsertException("Unknown error when insert stock");
        } else {
            if (stock == null || stock.getQuantity() < orders.getQuantity() - originOrder.getQuantity())
                throw new UpdateException("Insufficient stock");
            stock.setQuantity(stock.getQuantity() + originOrder.getQuantity() - orders.getQuantity());
            if (stock.getQuantity() == 0) {
                if (stockMapper.deleteStock(stock.getSupplyCenter(), stock.getProductModel()) != 1)
                    throw new DeleteException("Unknown error when delete stock");
            } else if (stockMapper.updateStock(stock) != 1)
                throw new UpdateException("Unknown error when update stock");
        }
        if (orders.getQuantity() == 0) {
            if (orderMapper.deleteOrder(orders.getContractNumber(), orders.getProductModel(), orders.getSalesmanNumber()) != 1)
                throw new DeleteException("Unknown error when delete orders");
        } else if (orderMapper.updateOrder(orders) != 1)
            throw new UpdateException("Unknown error when update orders");
    }

    @Override
    public void deleteOrder(String contract, String salesman, Integer seq) {
        if (contract == null || salesman == null || seq == null)
            throw new DeleteOrderException("Information should not be null");
        Orders orders = orderMapper.selectOrdersByContractSalesmanSeq(contract, salesman, seq);
        if (orders == null)
            throw new DeleteOrderException("Order does not exist");
        orders.setQuantity(0);
        updateOrder(orders);
    }

    @Override
    public Integer getContractCount() {
        return orderMapper.getContractCount();
    }

    @Override
    public Integer getOrderCount() {
        return orderMapper.getOrderCount();
    }

    @Override
    public String getFavoriteProductModel() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-50s%-10s\n", "product model", "sales volume"));
        List<Map<String, Object>> list = orderMapper.getFavoriteProductModel();
        for (Map<String, Object> res : list)
            sb.append(String.format("%-50s%-10s\n", res.get("product model"), res.get("sales volume")));
        return sb.toString();
    }

    @Override
    public String getContractInfo(String contractNumber) {
        StringBuilder sb = new StringBuilder();
        Map<String, Object> info = orderMapper.selectContractInfo(contractNumber);
        if (info == null)
            info = new HashMap<>();
        sb.append(String.format("contract number: %s\n", contractNumber));
        sb.append(String.format("enterprise: %s\n", info.get("enterprise")));
        sb.append(String.format("manager: %s\n", info.get("manager")));
        sb.append(String.format("supply center: %s\n", info.get("supply center")));
        sb.append(String.format("%-50s%-20s%-15s%-15s%-25s%-20s\n", "product model", "salesman", "quantity", "unit price", "estimate delivery date", "lodgement date"));
        List<Map<String, Object>> list = orderMapper.selectOrderInfo(contractNumber);
        for (Map<String, Object> res : list)
            sb.append(String.format("%-50s%-20s%-15s%-15s%-25s%-20s\n", res.get("product model"), res.get("salesman"), res.get("quantity"), res.get("unit price"), res.get("estimate delivery date"), res.get("lodgement date")));
        return sb.toString();
    }
}
