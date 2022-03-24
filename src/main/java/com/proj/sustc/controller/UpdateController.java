package com.proj.sustc.controller;


import com.proj.sustc.entity.Login_in;
import com.proj.sustc.service.ILogin_inService;
import com.proj.sustc.service.IStaffService;
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
@RequestMapping("/Update")
public class UpdateController {

    @Autowired
    private IStaffService iStaffService;

    @Autowired
    private ILogin_inService iLogin_inService;

    @RequestMapping("/GotoChangeStaff")
    @ResponseBody
    public RespBean GotoChangeStaff() {
        return RespBean.operation_success(RespBeanEnum.OPERATION_SUCCESS);
    }

    @RequestMapping("/doUpdateStaff")
    @ResponseBody
    public RespBean doUpdateStaff(HttpServletResponse response, HttpServletRequest request, @RequestParam("number") String number,
                                  @RequestParam("type") String type) {
        return iStaffService.doUpdateStaff(request, response, number, type);
    }

    @RequestMapping("/GotoUpdatePawssword")
    @ResponseBody
    public RespBean GotoUpdatePawssword() {
        return RespBean.operation_success(RespBeanEnum.OPERATION_SUCCESS);
    }

    @RequestMapping("/GotoUpdateModelPrice")
    @ResponseBody
    public RespBean GotoUpdateModelPrice() {
        return RespBean.operation_success();
    }


    @RequestMapping("/UpdatePassword")
    @ResponseBody
    public RespBean UpdatePassword(HttpServletResponse response, HttpServletRequest request, @RequestParam("NewPassword") String password,
                                   @RequestParam("ConfirmNewPassword") String conPassword, @CookieValue("LOGIN_IN_USER") String user) {
        if (!conPassword.equals(password)) {
            return RespBean.not_match();
        } else {
            Login_in login_in = iLogin_inService.getLogin_inByCookie(response, request, user);
            return iLogin_inService.UpdatePassword(request, response, password, user, login_in);
        }
    }

    @RequestMapping("/doUpdateStaffMobilePhone")
    @ResponseBody
    public RespBean doUpdateStaffMobilePhone(HttpServletResponse response, HttpServletRequest request, @RequestParam("number") String number,
                                             @RequestParam("phone") String phone, @CookieValue("LOGIN_IN_USER") String login_user) {

        Login_in user = iLogin_inService.getLogin_inByCookie(response, request, login_user);
        if (!user.getType().equals("CEO")) {
            if (!user.getUsername().equals(number)) {
                return RespBean.operation_error();
            }
        }
        return iStaffService.UpdateMobilePhone(number, phone);

    }


}
