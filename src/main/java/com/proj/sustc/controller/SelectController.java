package com.proj.sustc.controller;


import com.proj.sustc.service.*;
import com.proj.sustc.vo.RespBean;
import com.proj.sustc.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/Select")
public class SelectController {

    //注入IStaff用来查找销售最好的员工
    @Autowired
    private IOrderService iOrderService;

    @Autowired
    private IStockService iStockService;

    @Autowired
    private IStaffService iStaffService;


    @RequestMapping("/toSelect")
    @ResponseBody
    public RespBean goSelect(){
        System.out.println(2);
        return RespBean.operation_success(RespBeanEnum.OPERATION_SUCCESS);
    }

    @RequestMapping("/GoNext")
    @ResponseBody
    public RespBean GoNext(){
        return RespBean.operation_success(RespBeanEnum.OPERATION_SUCCESS);
    }

    @RequestMapping("/gotoProfit")
    @ResponseBody
    public RespBean gotoProfit(){
        return RespBean.operation_success(RespBeanEnum.OPERATION_SUCCESS);
    }

    @RequestMapping("/gotoOrder")
    @ResponseBody
    public RespBean gotoOrder(){
        return RespBean.operation_success(RespBeanEnum.OPERATION_SUCCESS);
    }

    @RequestMapping("/gotoFindStockRecord")
    @ResponseBody
    public RespBean gotoFindStockRecord(){
        return RespBean.operation_success(RespBeanEnum.OPERATION_SUCCESS);
    }


    @RequestMapping("/FindSellMostMoney")
    @ResponseBody
    public RespBean FindSellMostMoney(HttpServletResponse response, HttpServletRequest request){
        System.out.println("/FindSellMostMoney");
        return iOrderService.FindSellMostMoney(response,request);
    }
    @RequestMapping("/FindSellMostProduct")
    @ResponseBody
    public RespBean FindSellMostProduct(HttpServletResponse response, HttpServletRequest request){
        System.out.println("/FindSellMostProduct");
        return iOrderService.FindSellMostProduct( response,  request);
    }

    @RequestMapping("/FindBestSellProduct")
    @ResponseBody
    public RespBean FindBestSellProduct(HttpServletResponse response, HttpServletRequest request){
        System.out.println("/FindSellMostProduct");
        return iOrderService.FindBestSellProduct(request,response);
    }

    @RequestMapping("/FinancialReport")
    @ResponseBody
    public RespBean FinancialReport(HttpServletResponse response, HttpServletRequest request, @CookieValue("LOGIN_IN_USER") String login_in_user){
        return iOrderService.FinancialReport(response,request,login_in_user);
    }


    @RequestMapping("/FindProfit")
    @ResponseBody
    public RespBean FindProfit(HttpServletResponse response, HttpServletRequest request, @RequestParam("Area") String area, @CookieValue("LOGIN_IN_USER")String login_in_user
    , @RequestParam("start") String start, @RequestParam("end") String end ){
        return iOrderService.FindProfitBySql(response,request,login_in_user,area,start,end);
    }

   @RequestMapping("/SelectOrder")
   @ResponseBody
   public RespBean doSelectOrder(HttpServletResponse response, HttpServletRequest request, @RequestParam("model") String model, @RequestParam("contract") String contract,
                           @RequestParam("salesman")String salesman    ){
        return iOrderService.doSelectOrder(response,request,model,contract,salesman);
   }

   @RequestMapping("/SelectModel")
   @ResponseBody
    public RespBean SelectModel(HttpServletResponse response,HttpServletRequest request,@RequestParam("StockModel")String StockModel,
                         @RequestParam("SupplyCenter") String SupplyCenter  ){
        return iStockService.doSelectModel(request,response,StockModel,SupplyCenter);
   }

    @RequestMapping("/GOtoSelectStaff")
    @ResponseBody
    public RespBean GOtoSelectStaff(){
        return RespBean.operation_success();
   }

   @RequestMapping("/SelectStaff")
   @ResponseBody
   public RespBean SelectStaff(@RequestParam("number")String number,@RequestParam("name")String name,HttpServletRequest request,
                          HttpServletResponse response ){
        return iStaffService.SelectStaff(name,number,request,response);
   }


}
