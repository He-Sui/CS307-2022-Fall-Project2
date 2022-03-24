package com.proj.sustc.controller;


import com.proj.sustc.entity.Login_in;
import com.proj.sustc.entity.Role_Permission;
import com.proj.sustc.entity.Role_login_in;
import com.proj.sustc.mapper.Role_PermissionMapper;
import com.proj.sustc.mapper.Role_login_inMapper;
import com.proj.sustc.service.ILogin_inService;
import com.proj.sustc.service.IStaffService;
import com.proj.sustc.service.IStockService;
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
@RequestMapping("/Delete")
public class DeleteController {
    @Autowired
    private IStockService iStockService;

    @Autowired
    private IStaffService iStaffServicel;

    @Autowired
    private ILogin_inService iLogin_inService;

    @Autowired
    private Role_login_inMapper role_login_inMapper;

    @Autowired
    private Role_PermissionMapper role_permissionMapper;


    @RequestMapping("/GotoDelistedProducts")
    @ResponseBody
    public RespBean GotoDelistedProducts(){

        return RespBean.operation_success(RespBeanEnum.OPERATION_SUCCESS);
    }
    
    //这个删除没写完
    @RequestMapping("/DeleteModel")
    @ResponseBody
    public RespBean goDeleteModel(HttpServletRequest request, HttpServletResponse response, @RequestParam("StockModel")String model){

        return iStockService.DeleteModel(request,response,model);
    }

    @RequestMapping("/GotoDeleteStaff")
    @ResponseBody
    public RespBean GotoDeleteStaff(HttpServletRequest request,HttpServletResponse response,@CookieValue("LOGIN_IN_USER")String login_user){
        //简单设置一个权限
        //先看看它是什么类型，有无这个权限
        Login_in user = (Login_in) iLogin_inService.getLogin_inByCookie(response, request, login_user);
        //找到对应的role
        Role_login_in role_login_in = role_login_inMapper.SelectRoleIdByLogin_inType(user.getType());
        //查看是否有这个权限
        Role_Permission role_permission = role_permissionMapper.SelectByRoleIdAndPermission(role_login_in.getRole_id(), 2);
        if(role_permission==null){
         return RespBean.operation_error();
        }
        return RespBean.operation_success(RespBeanEnum.OPERATION_SUCCESS);
    }

    @RequestMapping("/DeleteStaff")
    @ResponseBody
    public RespBean DeleteStaff(HttpServletRequest request, HttpServletResponse response, @RequestParam("StaffNumber")String number,
                                @CookieValue("LOGIN_IN_USER")String login_user        ){
        return iStaffServicel.DeleteStaff(request,response,number,login_user);

    }

}
