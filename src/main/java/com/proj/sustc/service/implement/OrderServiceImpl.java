package com.proj.sustc.service.implement;

import com.proj.sustc.entity.*;
import com.proj.sustc.mapper.*;
import com.proj.sustc.object.ProductModelCount;
import com.proj.sustc.object.Profit;
import com.proj.sustc.object.StaffSell;
import com.proj.sustc.service.ILogin_inService;
import com.proj.sustc.service.IOrderService;
import com.proj.sustc.service.exception.*;
import com.proj.sustc.utils.CookieUtil;
import com.proj.sustc.utils.UUIDUtil;
import com.proj.sustc.vo.RespBean;
import com.proj.sustc.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private ILogin_inService iLogin_inService;

    @Autowired
    private Role_login_inMapper role_login_inMapper;

    @Autowired
    private Role_PermissionMapper role_permissionMapper;

    private static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    @Override
    @Transactional
    public void placeOrder(String contractNumber, String enterpriseName, String productModel, Integer quantity, String contractManager, Date contractDate, Date estimatedDate, Date lodgementDate, String salesmanNumber) {
        if (contractNumber == null || enterpriseName == null || productModel == null || quantity == null || contractManager == null || contractDate == null || estimatedDate == null || salesmanNumber == null)
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
    public void  updateOrder(Orders orders) {
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
        sb.append(String.format("%-60s%-20s%-15s%-15s%-25s%-20s\n", "product model", "salesman", "quantity", "unit price", "estimate delivery date", "lodgement date"));
        List<Map<String, Object>> list = orderMapper.selectOrderInfo(contractNumber);
        for (Map<String, Object> res : list)
            sb.append(String.format("%-60s%-20s%-15s%-15s%-25s%-20s\n", res.get("product model"), res.get("salesman"), res.get("quantity"), res.get("unit price"), res.get("estimate delivery date"), res.get("lodgement date")));
        return sb.toString();
    }

    @Override
    @Scheduled(fixedRate = 10000)
    public void updateContractType() {
        orderMapper.updateContractType();
    }

    @Override
    public RespBean FindSellMostMoney(HttpServletResponse response, HttpServletRequest request){
        List<Orders> ordersList= orderMapper.SelectAllOrders();

        //找到全部产品的价格表
        List<Model> modelArrayList=modelMapper.SelectAllModel();
        //建立hashmap好快速查找相应的价格
        HashMap<String,Long> model_price=new HashMap<>();

        for (Model model : modelArrayList) {
            if (!model_price.containsKey(model.getModel())) {
                model_price.put(model.getModel(), Long.valueOf(model.getUnitPrice()));
            }
        }
        //查找所有的员工好让工号和姓名对上
        List<Staff> staffList=staffMapper.selectAllStaff();
        //建立名字工号对应哈希表
        HashMap<String,String> name_number=new HashMap<>();
        for(Staff staff: staffList){
            if(!name_number.containsKey(staff.getNumber())){
                name_number.put(staff.getNumber(),staff.getName());
            }
        }
        //建立set去重
        HashSet<String> set=new HashSet<>();
        //开始把所有order里的员工信息导入
        for(Orders order:ordersList){
            set.add(order.getSalesmanNumber());
        }
        //set转换为数组
        String[] orders_salesman=set.toArray(new String[set.size()]);
        //建立hashmap根据员工的工号快速找到相应的员工的索引
        HashMap<String,Integer> find_index=new HashMap<>();
        //建立相应的销售员工订单数组
        StaffSell[] staffSells=new StaffSell[orders_salesman.length];
        for(int i=0;i<orders_salesman.length;i++){
            staffSells[i]=new StaffSell();
            staffSells[i].setNumber(orders_salesman[i]);
            staffSells[i].setName(name_number.get(orders_salesman[i]));
            staffSells[i].setCount(0);
            staffSells[i].setSales(0);

            find_index.put(orders_salesman[i],i);
        }

        StaffSell maxStaffSell=new StaffSell();
        //开始遍历订单表计算每个员工的销售额
        for(Orders orders:ordersList){
            //找到员工所对应的index
            int index= find_index.get(orders.getSalesmanNumber());
            String model=orders.getProductModel();
            long price=model_price.get(model);
            int quantity=orders.getQuantity();

            staffSells[index].count+=quantity;
            staffSells[index].sales+=(quantity*price);

            if(staffSells[index].sales> maxStaffSell.sales){
                maxStaffSell=staffSells[index];
            }
        }
        //开始把这里找到的销售额最好的员工信息存储起来
        String maxStaff= UUIDUtil.uuid();
        redisTemplate.opsForValue().set(maxStaff,maxStaffSell);
        //cookie中拿到maxStaff这个序列，通过Cookiename "MAX_STAFF_SELL_SALES"
        CookieUtil.setCookie(request,response,"MAX_STAFF_SELL_SALES",maxStaff);

        return RespBean.operation_success();

    }

    //寻找销售数量最多的员工
    public RespBean FindSellMostProduct(HttpServletResponse response, HttpServletRequest request){

        List<Orders> ordersList= orderMapper.SelectAllOrders();

        //找到全部产品的价格表
        List<Model> modelArrayList=modelMapper.SelectAllModel();
        //建立hashmap好快速查找相应的价格
        HashMap<String,Long> model_price=new HashMap<>();

        for (Model model : modelArrayList) {
            if (!model_price.containsKey(model.getModel())) {
                model_price.put(model.getModel(), Long.valueOf(model.getUnitPrice()));
            }
        }
        //查找所有的员工好让工号和姓名对上
        List<Staff> staffList=staffMapper.selectAllStaff();
        //建立名字工号对应哈希表
        HashMap<String,String> name_number=new HashMap<>();
        for(Staff staff: staffList){
            if(!name_number.containsKey(staff.getNumber())){
                name_number.put(staff.getNumber(),staff.getName());
            }
        }
        //建立set去重
        HashSet<String> set=new HashSet<>();
        //开始把所有order里的员工信息导入
        for(Orders order:ordersList){
            set.add(order.getSalesmanNumber());
        }
        //set转换为数组
        String[] orders_salesman=set.toArray(new String[set.size()]);
        //建立hashmap根据员工的工号快速找到相应的员工的索引
        HashMap<String,Integer> find_index=new HashMap<>();
        //建立相应的销售员工订单数组
        StaffSell[] staffSells=new StaffSell[orders_salesman.length];
        for(int i=0;i<orders_salesman.length;i++){
            staffSells[i]=new StaffSell();
            staffSells[i].setNumber(orders_salesman[i]);
            staffSells[i].setName(name_number.get(orders_salesman[i]));
            staffSells[i].setCount(0);
            staffSells[i].setSales(0);

            find_index.put(orders_salesman[i],i);
        }

        StaffSell maxStaffSell=new StaffSell();
        //开始遍历订单表计算每个员工的销售额
        for(Orders orders:ordersList){
            //找到员工所对应的index
            int index= find_index.get(orders.getSalesmanNumber());
            String model=orders.getProductModel();
            long price=model_price.get(model);
            int quantity=orders.getQuantity();

            staffSells[index].count+=quantity;
            staffSells[index].sales+=(quantity*price);

            if(staffSells[index].count>= maxStaffSell.count){
                if(staffSells[index].count== maxStaffSell.count){
                    if(staffSells[index].sales> maxStaffSell.sales){
                        maxStaffSell=staffSells[index];
                    }
                }
                else
                    maxStaffSell=staffSells[index];
            }
        }
        //开始把这里找到的销售额最好的员工信息存储起来
        String maxStaff_product= UUIDUtil.uuid();
        redisTemplate.opsForValue().set(maxStaff_product,maxStaffSell);
        //cookie中拿到maxStaff这个序列，通过Cookiename "MAX_STAFF_SELL_SALES"
        CookieUtil.setCookie(request,response,"MAX_STAFF_SELL_PRODUCT",maxStaff_product);
        return RespBean.operation_success();




    }

    //寻找最畅销的商品
    @Override
    public RespBean FindBestSellProduct(HttpServletRequest request, HttpServletResponse response){
        //拿到目前所有的订单
        List<Orders> ordersList= orderMapper.SelectAllOrders();
        //set放入被卖的商品
        HashSet<ProductModelCount> modelCounts=new HashSet<>();
        for (Orders orders : ordersList) {
            ProductModelCount productModelCount = new ProductModelCount();
            productModelCount.model = orders.getProductModel();
            modelCounts.add(productModelCount);
        }
        //hashmap找到model编号对应的索引
        HashMap<String,Integer> find_index=new HashMap<>();
        //set转为集合
        ProductModelCount[] productModelCounts=modelCounts.toArray(new ProductModelCount[modelCounts.size()]);
        for(int i=0;i< productModelCounts.length;i++){
            ProductModelCount productModelCount=productModelCounts[i];
            find_index.put(productModelCount.model,i);
        }
        //开始统计个数
        ProductModelCount max=new ProductModelCount();

        for(Orders orders : ordersList){
            int index= find_index.get(orders.getProductModel());
            productModelCounts[index].count+=orders.getQuantity();
            if(productModelCounts[index].count>max.count){
                max=productModelCounts[index];
            }
        }
        Model model=modelMapper.selectModelByModelNumber(max.model);
        max.unitPrice=model.getUnitPrice();
        max.product=model.getProductNumber();
        //开始存信息
        String max_product=UUIDUtil.uuid();
        //用redis存信息
        redisTemplate.opsForValue().set(max_product,max);
        CookieUtil.setCookie(request,response,"MAX_PRODUCT",max_product);
        return RespBean.operation_success();
    }


    @Override
    public  RespBean FinancialReport(HttpServletResponse response, HttpServletRequest request, String login_in_user){
        //首先，根据login_in_user拿到用户信息
        Login_in user=(Login_in)iLogin_inService.getLogin_inByCookie(response,request,login_in_user);
        //开始对user进行权限管理
        //首先根据user的type类型找到对应的role
        Role_login_in role_login_in=role_login_inMapper.SelectRoleIdByLogin_inType(user.getType());
        //然后根据对应的role和要进行的操作编号在role_permission里找
        Role_Permission role_permission=role_permissionMapper.SelectByRoleIdAndPermission(role_login_in.getRole_id(),5);
        //开始判断是否有这个权限
        if(role_permission==null){
            return RespBean.operation_error(RespBeanEnum.OPERATION_ERROR);
        }
        //开始计算财务报告信息
        //开始拿到所有的orders表以及设计一个model对应的价格表，计算出卖了多少钱
        List<Orders> ordersList= orderMapper.SelectAllOrders();
        long sales=0; long cost=0;
        List<Model> modelArrayList=modelMapper.SelectAllModel();
        //建立hashmap好快速查找相应的价格
        HashMap<String,Long> model_price=new HashMap<>();

        for (Model model : modelArrayList) {
            if (!model_price.containsKey(model.getModel())) {
                model_price.put(model.getModel(), Long.valueOf(model.getUnitPrice()));
            }
        }
        //开始计算所有的销售额
        for(Orders orders:ordersList){
            sales+=orders.getQuantity()*model_price.get(orders.getProductModel());
        }
        //开始计算所有的成本
        List<StockInRecord> stockInRecordList= stockMapper.SelectAllStockRecord();
        for(StockInRecord stockInRecord:stockInRecordList){
            cost+= (long) stockInRecord.getPurchasePrice() *stockInRecord.getQuantity();
        }
        long prof=sales-cost;
        //开始new对象
        Profit profit=new Profit();
        profit.areas="All areas";
        profit.profits=prof;
        profit.expense=cost;
        profit.income=sales;

        //储存起来
        String AllAreasProfit=UUIDUtil.uuid();
        redisTemplate.opsForValue().set(AllAreasProfit,profit);
        CookieUtil.setCookie(request,response,"All_Areas_Profit",AllAreasProfit);

        //可以操作，有这个权限
        return RespBean.operation_success(RespBeanEnum.OPERATION_SUCCESS);
    }

    @Override
    public RespBean FindProfit(HttpServletResponse response, HttpServletRequest request, String login_in_user, String area,String start,String end){
        //首先，根据login_in_user拿到用户信息
        Login_in user=(Login_in)iLogin_inService.getLogin_inByCookie(response,request,login_in_user);
        //开始对user进行权限管理
        //首先根据user的type类型找到对应的role
        Role_login_in role_login_in=role_login_inMapper.SelectRoleIdByLogin_inType(user.getType());
        //首先开始判断类型
        if(area.equals("Company")){
            //开始去权限表查找其是否有这个权限
            Role_Permission role_permission=role_permissionMapper.SelectByRoleIdAndPermission(role_login_in.getRole_id(),5);
            if(role_permission==null){
                return RespBean.operation_error(RespBeanEnum.OPERATION_ERROR);
            }else {
                Profit profit=findProfit( area, orderMapper, modelMapper, staffMapper, stockMapper);
                //用Cookie开始存
                String profitStr=UUIDUtil.uuid();
                redisTemplate.opsForValue().set(profitStr,profit);
                CookieUtil.setCookie(request,response,"PROFIT_STR",profitStr);
                return RespBean.operation_success(RespBeanEnum.OPERATION_SUCCESS);
            }
        }else {
            Role_Permission role_permission=role_permissionMapper.SelectByRoleIdAndPermission(role_login_in.getRole_id(),6);
            if(role_permission==null){
                return RespBean.operation_error(RespBeanEnum.OPERATION_ERROR);
            }else {
                //取staff里找到这个人是哪个供应中心的
                String number = user.getUsername();
                if (user.getType().equals("CEO")) {
                    Profit profit = findProfit(area, orderMapper, modelMapper, staffMapper, stockMapper);
                    //用Cookie开始存
                    String profitStr = UUIDUtil.uuid();
                    redisTemplate.opsForValue().set(profitStr, profit);
                    CookieUtil.setCookie(request, response, "PROFIT_STR", profitStr);
                    return RespBean.operation_success(RespBeanEnum.OPERATION_SUCCESS);
                } else {
                    Staff staff = staffMapper.selectByNumber(number);
                    if (staff.getSupplyCenter().equals(area)) {
                        Profit profit = findProfit(area, orderMapper, modelMapper, staffMapper, stockMapper);
                        //用Cookie开始存
                        String profitStr = UUIDUtil.uuid();
                        redisTemplate.opsForValue().set(profitStr, profit);
                        CookieUtil.setCookie(request, response, "PROFIT_STR", profitStr);
                        return RespBean.operation_success(RespBeanEnum.OPERATION_SUCCESS);
                    } else
                        return RespBean.operation_error(RespBeanEnum.OPERATION_ERROR);
                }
            }
        }
    }

    @Override
    public RespBean FindProfitBySql(HttpServletResponse response, HttpServletRequest request, String login_in_user, String area, String start, String end){
        //首先，根据login_in_user拿到用户信息
        Login_in user=(Login_in)iLogin_inService.getLogin_inByCookie(response,request,login_in_user);
        //开始对user进行权限管理
        //首先根据user的type类型找到对应的role
        Role_login_in role_login_in=role_login_inMapper.SelectRoleIdByLogin_inType(user.getType());
        //首先开始判断类型

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date date3= null; Date date4=null;
        try {
            date3 = sdf.parse(start);
            date4=sdf.parse(end);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        java.sql.Date date1 = new java.sql.Date(date3.getTime());
        java.sql.Date date2 = new java.sql.Date(date4.getTime());



        if(area.equals("Company")){
            //开始去权限表查找其是否有这个权限
            Role_Permission role_permission=role_permissionMapper.SelectByRoleIdAndPermission(role_login_in.getRole_id(),5);
            if(role_permission==null){
                return RespBean.operation_error(RespBeanEnum.OPERATION_ERROR);
            }else {

                List<ProfitSql> profitSqlList= orderMapper.SelectProfit(date1,date2);
                ProfitSql profit=new ProfitSql();
                profit.setSupply_center(area);
                Integer income=0; Integer expense=0; Integer pro=0;
                for(ProfitSql profitSql:profitSqlList){
                    income+=profitSql.getIncome();
                    expense+=profitSql.getExpense();
                    pro+=profitSql.getProfit();
                }
                profit.setProfit(pro);profit.setIncome(income);
                profit.setExpense(expense);
                String profitStr=UUIDUtil.uuid();
                //用Cookie开始存
                redisTemplate.opsForValue().set(profitStr,profit);
                CookieUtil.setCookie(request,response,"PROFIT_STR",profitStr);
                return RespBean.operation_success(RespBeanEnum.OPERATION_SUCCESS);
            }
        }else {
            Role_Permission role_permission=role_permissionMapper.SelectByRoleIdAndPermission(role_login_in.getRole_id(),6);
            if(role_permission==null){
                return RespBean.operation_error(RespBeanEnum.OPERATION_ERROR);
            }else {
                //取staff里找到这个人是哪个供应中心的
                String number = user.getUsername();
                if (user.getType().equals("CEO")) {

                    List<ProfitSql> profitSqlList= orderMapper.SelectProfit(date1,date2);
                    ProfitSql profit=new ProfitSql();
                    profit.setSupply_center(area);
                    Integer income=0; Integer expense=0; Integer pro=0;
                    for(ProfitSql profitSql:profitSqlList){
                        if(profitSql.getSupply_center().equals(area)){
                            income+=profitSql.getIncome();
                            expense+=profitSql.getExpense();
                            pro+=profitSql.getProfit();}
                    }
                    profit.setProfit(pro);profit.setIncome(income);
                    profit.setExpense(expense);
                    //用Cookie开始存
                    String profitStr = UUIDUtil.uuid();
                    redisTemplate.opsForValue().set(profitStr, profit);
                    CookieUtil.setCookie(request, response, "PROFIT_STR", profitStr);
                    return RespBean.operation_success(RespBeanEnum.OPERATION_SUCCESS);
                } else {
                    Staff staff = staffMapper.selectByNumber(number);
                    if (staff.getSupplyCenter().equals(area)) {
                        List<ProfitSql> profitSqlList= orderMapper.SelectProfit(date1,date2);
                        ProfitSql profit=new ProfitSql();
                        profit.setSupply_center(area);
                        Integer income=0; Integer expense=0; Integer pro=0;
                        for(ProfitSql profitSql:profitSqlList){
                            if(profitSql.getSupply_center().equals(area)){
                                income+=profitSql.getIncome();
                                expense+=profitSql.getExpense();
                                pro+=profitSql.getProfit();}
                        }
                        profit.setProfit(pro);profit.setIncome(income);
                        profit.setExpense(expense);
                        //用Cookie开始存
                        String profitStr = UUIDUtil.uuid();
                        redisTemplate.opsForValue().set(profitStr, profit);
                        CookieUtil.setCookie(request, response, "PROFIT_STR", profitStr);
                        return RespBean.operation_success(RespBeanEnum.OPERATION_SUCCESS);
                    } else
                        return RespBean.operation_error(RespBeanEnum.OPERATION_ERROR);
                }
            }
        }

    }

    @Override
    public RespBean doSelectOrder(HttpServletResponse response, HttpServletRequest request, String model, String area){
        //开始在orderMapper包下写相应的查询方法
        List<Orders> ordersList= orderMapper.SelectOrderByModelAndArea(area,model);
        //开始用cookie存
        String orders=UUIDUtil.uuid();
        redisTemplate.opsForValue().set(orders,ordersList);
        CookieUtil.setCookie(request,response,"ORDERS",orders);
        //返回操作信息
        return RespBean.operation_success(RespBeanEnum.OPERATION_SUCCESS);
    }


    @Override
    public StaffSell getStaffSellByCookie(HttpServletResponse response,HttpServletRequest request,String maxStaff){
        if(maxStaff==null){
            return null;
        }
        StaffSell staffSell=(StaffSell) redisTemplate.opsForValue().get(maxStaff);
        return staffSell;
    }

    @Override
    public ProductModelCount getProductModelCountByCookie(HttpServletResponse response,HttpServletRequest request,String max_product){
        if(max_product==null){
            return null;
        }
        ProductModelCount productModelCount=(ProductModelCount) redisTemplate.opsForValue().get(max_product);
        return productModelCount;
    }

    @Override
    public Profit getProfitByCookie(HttpServletResponse response,HttpServletRequest request,String AllAreasProfit){
        if(AllAreasProfit==null){
            return null;
        }
        Profit profit=(Profit) redisTemplate.opsForValue().get(AllAreasProfit);
        return profit;
    }

    @Override
    public  ProfitSql getProfitsqlByCookie(HttpServletResponse response, HttpServletRequest request, String profitStr){
        if(profitStr==null){
            return null;
        }
        ProfitSql profitSql=(ProfitSql)  redisTemplate.opsForValue().get(profitStr);
        return profitSql;
    }

    @Override
    public  List<Orders> getListOrdersByCookie(HttpServletResponse response,HttpServletRequest request,String orders){
        if(orders==null){
            return null;
        }
        List<Orders> ordersList=(List<Orders>) redisTemplate.opsForValue().get(orders);
        return ordersList;
    }

    //计算各个分部以及总公司的利润值;
    public static Profit findProfit(String area,OrderMapper orderMapper,ModelMapper modelMapper,StaffMapper staffMapper,StockMapper stockMapper){
        //开始拿到所有的orders表以及设计一个model对应的价格表，计算出卖了多少钱
        List<Orders> ordersList= orderMapper.SelectAllOrders();
        long sales=0; long cost=0;
        List<Model> modelArrayList=modelMapper.SelectAllModel();
        //建立hashmap好快速查找相应的价格
        HashMap<String,Long> model_price=new HashMap<>();

        for (Model model : modelArrayList) {
            if (!model_price.containsKey(model.getModel())) {
                model_price.put(model.getModel(), Long.valueOf(model.getUnitPrice()));
            }
        }
        Profit Company=new Profit();
        Company.areas="Company";
        //找到每个商品的进价
        List<Staff> staffList= staffMapper.selectAllStaff();
        //建立hashmap，通过员工编号找到对应的地区
        HashMap<String,String> NumberToArea=new HashMap<>();
        for(Staff staff: staffList){
            NumberToArea.put(staff.getNumber(), staff.getSupplyCenter());
        }
        //开始计算整个公司的利润
        for(Orders orders:ordersList){
            //首先拿到商品的价格和数量
            long quantity=orders.getQuantity();
            long price=model_price.get(orders.getProductModel());
            //找到对应仓库的进货
            String supply_center=NumberToArea.get(orders.getSalesmanNumber());
            List<StockInRecord> stockInRecordList=stockMapper.SelectModelPurchasePriceBySupplyCenterAndModel(supply_center,orders.getProductModel());
            Integer purchase=0;
            Date date=orders.getEstimatedDeliveryDate();
            for(StockInRecord stockInRecord:stockInRecordList){
                if(stockInRecord.getDate().before(date)){
                    date=stockInRecord.getDate();
                    purchase=stockInRecord.getPurchasePrice();
                    break;
                }
            }
            Company.profits+=quantity*price-quantity*purchase;
        }
        //现在开始计算地区的利润 首先建立一个hashmap存所有地区的利润对象，并用area为键进行对应
        HashMap<String,Profit> profitHashMap=new HashMap<>();
        profitHashMap.put("Company",Company);
        //
        Profit America=new Profit();
        America.areas="America";
        profitHashMap.put(America.areas,America);
        //
        Profit EasternChina=new Profit();
        EasternChina.areas="Eastern China";
        profitHashMap.put(EasternChina.areas, EasternChina);
        //
        Profit Asia=new Profit();
        Asia.areas="Asia";
        profitHashMap.put(Asia.areas,Asia);
        //
        Profit SouthernChina=new Profit();
        SouthernChina.areas="Southern China";
        profitHashMap.put(SouthernChina.areas,SouthernChina);
        //
        Profit NorthernChina=new Profit();
        NorthernChina.areas="Northern China";
        profitHashMap.put(NorthernChina.areas,NorthernChina);
        //
        Profit Europe=new Profit();
        Europe.areas="Europe";
        profitHashMap.put(Europe.areas,Europe);
        //
        Profit SouthwesternChina=new Profit();
        SouthwesternChina.areas="Southwestern China";
        profitHashMap.put(SouthwesternChina.areas,SouthwesternChina);
        //
        Profit  RegionsOfChina=new Profit();
        RegionsOfChina.areas="Hong Kong, Macao and Taiwan regions of China";
        profitHashMap.put(RegionsOfChina.areas,RegionsOfChina);
        //全部都放进去了，现在开始计算profit
        for(Orders orders:ordersList){
            //拿到对应的供应中心
            String supply=NumberToArea.get(orders.getSalesmanNumber());
            //拿到对应的Profit
            Profit profit=profitHashMap.get(supply);
            //开始计算
            //首先拿到商品的价格和数量
            long quantity=orders.getQuantity();
            long price=model_price.get(orders.getProductModel());
            Integer purchase=0;
            List<StockInRecord> stockInRecordList=stockMapper.SelectModelPurchasePriceBySupplyCenterAndModel(supply,orders.getProductModel());
            Date date=orders.getEstimatedDeliveryDate();
            for(StockInRecord stockInRecord:stockInRecordList){
                if(stockInRecord.getDate().before(date)){
                    date=stockInRecord.getDate();
                    purchase=stockInRecord.getPurchasePrice();
                }
            }
            profit.profits+=quantity*price-quantity*purchase;
            profitHashMap.put(profit.areas,profit);
        }
        return profitHashMap.get(area);
    }
}
