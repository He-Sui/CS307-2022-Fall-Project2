package com.proj.sustc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.proj.sustc.entity.*;
import com.proj.sustc.object.ProductModelCount;
import com.proj.sustc.object.Profit;
import com.proj.sustc.object.StaffSell;
import com.proj.sustc.mapper.StaffMapper;
import com.proj.sustc.service.ILogin_inService;

import com.proj.sustc.service.IOrderService;
import com.proj.sustc.service.IStaffService;
import com.proj.sustc.service.IStockService;
import com.proj.sustc.utils.CookieUtil;
import com.proj.sustc.utils.UUIDUtil;
import com.proj.sustc.vo.RespBean;
import com.proj.sustc.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/Decision")
public class DecisionController {
    @Autowired
    private StaffMapper staffMapper;
    @Autowired
    private ILogin_inService iLogin_inService;
    @Autowired
    private IOrderService iOrderService;
    @Autowired
    private IStockService iStockService;

    @Autowired
    private IStaffService iStaffService;

    @Autowired
    private RedisTemplate redisTemplate;


    @RequestMapping("/toDecision")
    public String toDecision(HttpServletResponse response, HttpServletRequest request, Model model, @CookieValue("LOGIN_IN_USER") String login_in_user) {
        //
        if (StringUtils.isEmpty(login_in_user)) {
            return "login";
            //返回登录界面
        }
        Login_in login_in = iLogin_inService.getLogin_inByCookie(response, request, login_in_user);
        if (login_in == null) {
            return "login";
        }
        model.addAttribute("login_in_user", login_in);
        Integer ContractCount=iOrderService.getContractCount();
        Integer OrderCount=iOrderService.getOrderCount();
        Integer NeverProductCount=iStockService.getNeverSoldProductCount();

        model.addAttribute("ContractCount",ContractCount);
        model.addAttribute("OrderCount",OrderCount);
        model.addAttribute("NeverProductCount",NeverProductCount);

       return "Operation";

    }

    //返回 工作界面返回到登陆界面
   @RequestMapping("/doBack1")
   public String doBack1(){
        return "login";
   }

    @RequestMapping("/doBack2")
    public String doBack2(){
        return "working";
    }

   @RequestMapping("/doSelect")
   public String doSelect(){
        return "select";
   }

   @RequestMapping("/doGoNext")
   public String doGoNext(){
        return "Next";
   }
   @RequestMapping("/doGotoProfit")
   public String doGotoProfit(){
        return "SelectProfit";
   }

    @RequestMapping("/doGotoOrder")
    public String doGotoOrder(){
        return "SearchOrder";
    }
    @RequestMapping("dogotoFindStockRecord")
    public String dogotoFindStockRecord(){
        return "SearchStockModel";
    }

    //下架产品中转界面
    @RequestMapping("/doGotoDelistedProducts")
    public String doGotoDelistedProducts(){
        return "SearchDeleteModel";
    }

    @RequestMapping("/doGotoAddProducts")
    public String doGotoAddProducts(){
        return "AddProduct";
    }

    @RequestMapping("/doGotoDeleteStaff")
    public String doGotoDeleteStaff(){
        return "DeleteStaff";
    }

    @RequestMapping("/doGotoChangeStaff")
    public String doGotoChangeStaff(){
        return "ChangeStaff";
    }

    @RequestMapping("/doGotoUpdatePawssword")
    public String doGotoUpdatePawssword(){
        return "ChangePassword";
    }

    @RequestMapping("/doGotoAddStock")
    public String doGotoAddStock(){
        return "AddStockModel";
    }

    @RequestMapping("/doGotoTestI")
    public String doGotoTestI(HttpServletResponse response,HttpServletRequest request,Model model){
        List<Map<String,Object>> list=iStaffService.getAllStaffCount();
        ArrayList<TestI> testIArrayList=new ArrayList<>();
        for(int i=0;i<list.size();i++){
            TestI testI=new TestI();
            testI.count=String.valueOf(list.get(i).get("count"));
            testI.type= (String) list.get(i).get("type");
            testIArrayList.add(testI);
        }
        JSONArray jo=new JSONArray();
        jo= (JSONArray) JSONArray.toJSON(testIArrayList);
        model.addAttribute("TestI",jo);
        return "TestI";
    }


    @RequestMapping("/GotoTestN")
    public String GotoTestN(HttpServletResponse response,HttpServletRequest request,Model model){

        List<Map<String,Object>> list= iStockService.getAvgStockByCenter();
        ArrayList<TestN> testNArrayList=new ArrayList<>();
        for(int i=0;i<list.size();i++){
            TestN testN=new TestN();
            testN.area=(String) list.get(i).get("supply_center");
            testN.average=String.valueOf(list.get(i).get("average"));
            testNArrayList.add(testN);
        }

        JSONArray jo=new JSONArray();
        jo= (JSONArray) JSONArray.toJSON(testNArrayList);
        model.addAttribute("TestN",jo);
        return "TestN";

    }

    @RequestMapping("/doGotoTestO")
    public String doGotoTestO(){
        return "SelectTestO";
    }

     @RequestMapping("/doGotoTestP")
     public String doGotoTestP(){
        return "SelectTestP";
     }


    @RequestMapping("/TestONumber")
    @ResponseBody
    public RespBean TestONumber(HttpServletRequest request,HttpServletResponse response,Model model, @RequestParam("number") String number){
        List<Map<String,Object>> list= iStockService.getProductByNumber(number);
        //拿到想要的链表
        List<TestO> testOList=new ArrayList<>();
        for(int i=0;i<list.size();i++){
            String area= (String) list.get(i).get("supply center");
            String model1= (String) list.get(i).get("product model");
            Integer quantity= (Integer) list.get(i).get("quantity");
            TestO testO=new TestO();
            testO.quantity=quantity; testO.area=area;
            testO.model=model1;
            testOList.add(testO);
        }
        //开始存起来
        String TestOmodel= UUIDUtil.uuid();
        redisTemplate.opsForValue().set(TestOmodel,testOList);
        CookieUtil.setCookie(request,response,"TESTOMODEL",TestOmodel);
        return RespBean.operation_success(RespBeanEnum.OPERATION_SUCCESS);
    }

    @RequestMapping("/doTestONumber")
    public String doTestONumber(HttpServletRequest request,HttpServletResponse response,@CookieValue("TESTOMODEL") String TestOmodel,
            Model model      ){

        //开始拿出来
        List<TestO> testOList= iStockService.getTestObyCookie(response,request,TestOmodel);
        JSONArray jo=new JSONArray();
        jo=(JSONArray) JSONArray.toJSON(testOList);
        model.addAttribute("TestO",jo);
        return "TestO";

    }

    //开始 TestP
    @RequestMapping("TestPNumber")
    @ResponseBody
    public RespBean TestPNumber(HttpServletResponse response,HttpServletRequest request,@RequestParam("number")String number){
        List<Map<String,Object>> list=iOrderService.getContractInfo(number);
        TestP1 testP1=new TestP1();
        List<TestP2> testP2s=new ArrayList<>();
        for(int i=0;i<list.size();i++){
            if(i==0){
                testP1.supply_center= (String) list.get(0).get("supply center");
               testP1.manager= (String) list.get(0).get("manager");
               testP1.contract_number=number;
               testP1.enterprise= (String) list.get(0).get("enterprise");

            }else {
               TestP2 testP2=new TestP2();
               Date ld= (Date) list.get(i).get("lodgement date");
               testP2.setLd(String.valueOf(ld));
                Date edd= (Date) list.get(i).get("estimate delivery date");
               testP2.setEdd(String.valueOf(edd));
               testP2.setQuantity((Integer) list.get(i).get("quantity"));
               testP2.setSalesman((String) list.get(i).get("salesman"));
               testP2.setModel((String) list.get(i).get("product model"));
               testP2.setUnit_price((Integer) list.get(i).get("unit price"));
               testP2s.add(testP2);
            }
        }
          //开始存起来
          String p1=UUIDUtil.uuid();
          String p2=UUIDUtil.uuid();
          redisTemplate.opsForValue().set(p1,testP1);
          redisTemplate.opsForValue().set(p2,testP2s);
          CookieUtil.setCookie(request,response,"P1",p1);
          CookieUtil.setCookie(request,response,"P2",p2);

        return RespBean.operation_success();

}


  @RequestMapping("/doTestPNumber")

  public String doTestONumber(Model model,@CookieValue("P1")String p1,@CookieValue("P2")String p2,HttpServletRequest request,
           HttpServletResponse response    ){
        //开始拿到数据
      TestP1 testP1=iOrderService.getTestP1ByCookie(response,request,p1);
      List<TestP2> testP2s=iOrderService.getTestP2ByCookie(response,request,p2);
      JSONArray ja=new JSONArray();
      ja=(JSONArray) JSONArray.toJSON(testP2s);
      JSONObject jo=new JSONObject();
      jo=(JSONObject)JSON.toJSON(testP1);

      model.addAttribute("P1",jo);
      model.addAttribute("P2",ja);

      return "TestP";

  }

  @RequestMapping("/doGotoGetFavoriteProduct")
  public String doGotoGetFavoriteProduct(Model model){
     List<Map<String,Object>> list=iOrderService.getFavoriteProductModel();
     List<FavoriateProduct> favoriateProductList=new ArrayList<>();

     for(int i=0;i<list.size();i++){
     FavoriateProduct favoriateProduct=new FavoriateProduct();
     favoriateProduct.volume= String.valueOf( list.get(0).get("sales volume"));
     favoriateProduct.model= String.valueOf( list.get(0).get("product model"));
     favoriateProductList.add(favoriateProduct);
     }
     JSONArray ja=new JSONArray();
     ja=(JSONArray)JSONArray.toJSON(favoriateProductList);
     model.addAttribute("FavoriateProduct",ja);
     return "FavoriteProduct";
  }


   @RequestMapping("/doGOtoSelectStaff")
   public String doGOtoSelectStaff(){
        return "SelectStaff";
   }

   @RequestMapping("/doSelectStaff")
   public String doSelectStaff(Model model,@CookieValue("STAFF")String staff){
        //拿出list
       List<Staff> list= iStaffService.getStaffByCookie(staff);
       //开始用json处理
       JSONArray ja=new JSONArray();
       ja=(JSONArray) JSONArray.toJSON(list);
       model.addAttribute("Staff",ja);
       return "Staff";

   }






   //将找到的销售额最大的员工返回到前端界面
   @RequestMapping("/doSelectSellMostMoney")
   public String doSelectSellMostMoney(HttpServletResponse response,HttpServletRequest request,Model model,@CookieValue("MAX_STAFF_SELL_SALES") String maxStaff){
        //拿到销售额最大的员工对象
       StaffSell maxStaffSell=iOrderService.getStaffSellByCookie(response,request,maxStaff);
       JSONObject jo=new JSONObject();
       //这里用json处理了
       jo= (JSONObject) JSON.toJSON(maxStaffSell);
       System.out.println(jo.toString());

       model.addAttribute("Max_sales",jo);
       return "SelectSellMostMoney";
   }

   //寻找销售数量最大的员工
   @RequestMapping("/doFindSellMostProduct")
   public String doFindSellMostProduct(HttpServletResponse response,HttpServletRequest request,Model model,@CookieValue("MAX_STAFF_SELL_PRODUCT")String maxStaff_product){
        StaffSell maxStaffSellProduct=iOrderService.getStaffSellByCookie(response,request,maxStaff_product);
        JSONObject jo=new JSONObject();
        jo=(JSONObject) JSON.toJSON(maxStaffSellProduct) ;
        model.addAttribute("Max_sell_product",jo);
        return "FindSellMostProduct";
   }

   //寻找最畅销的商品
    @RequestMapping("/doBestSellProduct")
    public String doBestSellProduct(HttpServletResponse response,HttpServletRequest request,Model model,@CookieValue("MAX_PRODUCT")String max_product){
        ProductModelCount productModelCount=iOrderService.getProductModelCountByCookie(response,request,max_product);
        JSONObject jo=new JSONObject();
        jo=(JSONObject) JSON.toJSON(productModelCount);
        model.addAttribute("Best_Product",jo);
        return "BestSellProduct";
    }

    @RequestMapping("/FinancialReport")
    public String FinancialReport(HttpServletResponse response,HttpServletRequest request,Model model,@CookieValue("All_Areas_Profit")String AllAreasProfit){
        Profit profit=(Profit) iOrderService.getProfitByCookie(response,request,AllAreasProfit);
        JSONObject jo=new JSONObject();
        jo=(JSONObject) JSON.toJSON(profit);
        model.addAttribute("Profit_Areas",jo);
        return "FinancialReport";
    }


    @RequestMapping("/doFindProfit")
    public String doFindProfit(HttpServletResponse response,HttpServletRequest request,Model model,@CookieValue("PROFIT_STR")String profitStr){
        ProfitSql profit=(ProfitSql) iOrderService.getProfitsqlByCookie(response,request,profitStr);
        JSONObject jo=new JSONObject();
        jo=(JSONObject) JSON.toJSON(profit);
        model.addAttribute("Profit",jo);
        return "Profit";
    }
    @RequestMapping("/doSelectOrder")
    public String doSelectOrder(HttpServletResponse response,HttpServletRequest request,Model model,@CookieValue("ORDERS")String orders){
        List<Orders> ordersList=(List<Orders>) iOrderService.getListOrdersByCookie(response,request,orders);
        List<OrdersDisplay> list=new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(int i=0;i<ordersList.size();i++){
           OrdersDisplay ordersDisplay=new OrdersDisplay();
           Orders orders1= ordersList.get(i);
           ordersDisplay.lodgementDate=sdf.format(orders1.getLodgementDate());
           ordersDisplay.estimatedDeliveryDate=sdf.format(orders1.getEstimatedDeliveryDate());
           ordersDisplay.contractNumber=orders1.getContractNumber();
           ordersDisplay.salesmanNumber=orders1.getSalesmanNumber();
           ordersDisplay.productModel=orders1.getProductModel();
           ordersDisplay.quantity=orders1.getQuantity();
           list.add(ordersDisplay);
        }
        JSONArray jo=new JSONArray();
        jo=(JSONArray)JSONArray.toJSON(list);
        System.out.println(jo.toString());

        model.addAttribute("ordersList",jo);

        return "SelectOrder";
    }

    @RequestMapping("/doSelectStockModel")
    public String doSelectModel(HttpServletResponse response,HttpServletRequest request,Model model,@CookieValue("STOCK_MODEL")String stockModel) {
        List<Stock> stockList=iStockService.getStockListByCookie(request,response,stockModel);
        JSONArray jo=new JSONArray();
        jo=(JSONArray) JSONArray.toJSON(stockList);
        System.out.println(jo);
        model.addAttribute("stockList",jo);
        return "StockModel";
    }

    @RequestMapping("/doGotoGenerateOrder")
    public String doGotoGenerateOrder(){
        return "GenerateOrder";
    }


}
