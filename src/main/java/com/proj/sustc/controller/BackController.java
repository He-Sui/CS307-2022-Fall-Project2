package com.proj.sustc.controller;


import com.proj.sustc.vo.RespBean;
import com.proj.sustc.vo.RespBeanEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/Back")
public class BackController {

    @RequestMapping("/toBack1")
    @ResponseBody
    public RespBean doBack1(){
        System.out.println(-1);
        return  RespBean.operation_success(RespBeanEnum.OPERATION_SUCCESS);
    }

    @RequestMapping("/toBack2")
    @ResponseBody
    public RespBean doBack2(){
        System.out.println(-1);
        return  RespBean.operation_success(RespBeanEnum.OPERATION_SUCCESS);
    }
}
