package com.proj.sustc.controller;


import com.proj.sustc.vo.RespBean;
import com.proj.sustc.vo.RespBeanEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/Delete")
public class DeleteController {
    @RequestMapping("/GotoDelistedProducts")
    @ResponseBody
    public RespBean GotoDelistedProducts(){
        return RespBean.operation_success(RespBeanEnum.OPERATION_SUCCESS);
    }
    
    //这个删除没写完

    @RequestMapping("/DeleteModel")
    @ResponseBody
    public RespBean goDeleteModel(){
        return RespBean.operation_success(RespBeanEnum.OPERATION_SUCCESS);
    }

}
