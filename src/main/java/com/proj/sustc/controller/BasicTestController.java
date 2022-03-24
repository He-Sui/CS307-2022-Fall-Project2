package com.proj.sustc.controller;

import com.proj.sustc.vo.RespBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/Basic")
public class BasicTestController {
    @RequestMapping("/GotoTestI")
    @ResponseBody
    public RespBean GotoTestI(){
        return RespBean.operation_success();
    }

    @RequestMapping("/GotoTestN")
    @ResponseBody
    public RespBean GotoTestN(){
        return RespBean.operation_success();
    }

    @RequestMapping("/GotoTestO")
    @ResponseBody
    public RespBean GotoTestO(){
        return RespBean.operation_success();
    }

    @RequestMapping("/GotoTestP")
    @ResponseBody
    public RespBean GotoTestP(){
        return RespBean.operation_success();
    }


    @RequestMapping("/GotoGetFavoriteProduct")
    @ResponseBody
    public RespBean GotoGetFavoriteProduct(){
        return RespBean.operation_success();
    }

}
