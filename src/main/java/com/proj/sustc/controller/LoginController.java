package com.proj.sustc.controller;

import com.proj.sustc.service.ILogin_inService;
import com.proj.sustc.vo.LoginVO;
import com.proj.sustc.vo.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/Login")
public class LoginController {

    //注入Login_inMapper
    @Autowired
    private ILogin_inService iLogin_inService;

    @RequestMapping("/toLogin")
    public String toLogin(){
        //跳转到login html页面
        return "login";
    }

    @RequestMapping("/doLogin")
    @ResponseBody
    public RespBean doTransit(HttpServletResponse response, HttpServletRequest request, LoginVO loginVO){
        return iLogin_inService.doTest(response,request,loginVO);
    }


}
