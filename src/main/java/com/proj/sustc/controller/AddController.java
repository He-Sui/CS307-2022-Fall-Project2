package com.proj.sustc.controller;


import com.proj.sustc.entity.StockInRecord;
import com.proj.sustc.service.IModelService;
import com.proj.sustc.service.IOrderService;
import com.proj.sustc.service.IStockService;
import com.proj.sustc.service.exception.ServiceException;
import com.proj.sustc.vo.RespBean;
import com.proj.sustc.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/Add")
public class AddController {

    @Autowired
    private IModelService iModelService;
    @Autowired
    private IOrderService iOrderService;
    @Autowired
    private IStockService iStockService;



    @RequestMapping("/GotoAddProducts")
    @ResponseBody
    public RespBean GotoAddProducts(HttpServletRequest request, HttpServletResponse response){
        return RespBean.operation_success(RespBeanEnum.OPERATION_SUCCESS);
    }
    @RequestMapping("/AddModel")
    @ResponseBody
    public RespBean AddModel(HttpServletRequest request, HttpServletResponse response, @RequestParam("AddModel") String model,
                     @RequestParam("Product") String product, @RequestParam("ChoosePrice") Integer price ){

        return iModelService.AddModel(request,response,model,product,price);
    }

    @RequestMapping("/GotoAddStock")
    @ResponseBody
    public RespBean GotoAddStock(){
        return RespBean.operation_success(RespBeanEnum.OPERATION_SUCCESS);
    }

    @RequestMapping("/AddStockModel")
    @ResponseBody
    public RespBean AddStockModel(HttpServletRequest request,HttpServletResponse response,@RequestParam("SelectSupplyCenter")
              String SelectSupplyCenter,@RequestParam("ModelpurchasePrice") Integer ModelpurchasePrice,@RequestParam("ModelStock") String ModelStock,
           @RequestParam("ModelQuantity") Integer ModelQuantity,
              @RequestParam("supply_number")String supply_number, @RequestParam("date") String date  ){

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date strTo=null;
        try {
            strTo= sdf.parse(date);
        }catch (ParseException e){
            e.printStackTrace();
        }

        java.sql.Date date1=new java.sql.Date(strTo.getTime());
        StockInRecord stockInRecord=new StockInRecord();
        stockInRecord.setDate(date1);
        stockInRecord.setPurchasePrice(ModelpurchasePrice);
        stockInRecord.setProductModel(ModelStock);
        stockInRecord.setQuantity(ModelQuantity);
        stockInRecord.setSupplyStaff(supply_number);
        stockInRecord.setSupplyCenter(SelectSupplyCenter);
        try {
            iStockService.stockIn(stockInRecord);
        }catch (ServiceException e){
            return RespBean.stock_error();
        }
        return RespBean.operation_success();

    }

    @RequestMapping("/GotoGenerateOrder")
    @ResponseBody
    public RespBean GotoGenerateOrder(){
        return RespBean.operation_success();
    }

    @RequestMapping("/AddOrder")
    @ResponseBody
    public RespBean AddOrder(HttpServletResponse response,HttpServletRequest request, @RequestParam("contractNumber")String contractNumber,
      @RequestParam("enterpriseName") String enterpriseName, @RequestParam("productModel") String productModel,@RequestParam("quantity")
       Integer quantity,@RequestParam("contractManager") String contractManager, @RequestParam("salesmanNumber") String salesmanNumber,
        @RequestParam("contractDate") String contractDate,@RequestParam("estimatedDate") String estimatedDate,
    @RequestParam("lodgementDate") String lodgementDate ){


        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date strTo=null; Date strTo1=null; Date strTo2=null;
        try {
            strTo= sdf.parse(contractDate); strTo1= sdf.parse(estimatedDate); strTo2=sdf.parse(lodgementDate);
        }catch (ParseException e){
            e.printStackTrace();
        }

        java.sql.Date contractDate1=new java.sql.Date(strTo.getTime());
        java.sql.Date estimatedDate1=new java.sql.Date(strTo1.getTime());
        java.sql.Date lodgementDate1=new java.sql.Date(strTo2.getTime());

        try {
            iOrderService.placeOrder(contractNumber,enterpriseName,productModel,quantity,contractManager,contractDate1,estimatedDate1
        ,lodgementDate1,salesmanNumber);}
        catch (ServiceException e){
            return RespBean.generate_error();
        }

        return RespBean.operation_success();

    }


}
