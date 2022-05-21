package com.proj.sustc.service;

import com.proj.sustc.entity.Login_in;
import com.proj.sustc.vo.LoginVO;
import com.proj.sustc.vo.RespBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ILogin_inService  {

    void insert(Login_in login_in);

    RespBean doTest(HttpServletResponse response, HttpServletRequest request, LoginVO loginVO);
    Login_in getLogin_inByCookie(HttpServletResponse response,HttpServletRequest request,String login_in_user);
}
