package com.proj.sustc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.proj.sustc.entity.Login_in;
import com.proj.sustc.entity.Orders;
import com.proj.sustc.entity.ProfitSql;
import com.proj.sustc.entity.Stock;
import com.proj.sustc.mapper.StaffMapper;
import com.proj.sustc.object.ProductModelCount;
import com.proj.sustc.object.Profit;
import com.proj.sustc.object.StaffSell;
import com.proj.sustc.service.ILogin_inService;
import com.proj.sustc.service.IOrderService;
import com.proj.sustc.service.IStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
        //原先
       // return "working";
       // 改动搬移
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
        JSONArray jo=new JSONArray();
        jo=(JSONArray)JSONArray.toJSON(ordersList);
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

}
