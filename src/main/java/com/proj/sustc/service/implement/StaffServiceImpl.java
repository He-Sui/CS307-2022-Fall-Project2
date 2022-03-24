package com.proj.sustc.service.implement;

import com.proj.sustc.entity.Login_in;
import com.proj.sustc.entity.Staff;
import com.proj.sustc.mapper.*;
import com.proj.sustc.service.ILogin_inService;
import com.proj.sustc.service.IStaffService;
import com.proj.sustc.service.exception.InsertException;
import com.proj.sustc.utils.CookieUtil;
import com.proj.sustc.utils.Mobile;
import com.proj.sustc.utils.UUIDUtil;
import com.proj.sustc.vo.RespBean;
import com.proj.sustc.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Service
public class StaffServiceImpl implements IStaffService {

    @Autowired
    private StaffMapper staffMapper;

    @Autowired
    private CenterMapper centerMapper;

    @Autowired
    private Login_inMapper login_inMapperl;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private ILogin_inService iLogin_inService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void insert(Staff staff) {
        if (staff == null || staff.getName() == null || staff.getAge() == null || staff.getGender() == null || staff.getNumber() == null || staff.getSupplyCenter() == null || staff.getPhoneNumber() == null || staff.getType() == null)
            throw new InsertException("Staff information should not be null");
        if (staff.getNumber().length() != 8)
            throw new InsertException("Invalid staff number");
        if (!(staff.getGender().equals("Male") || staff.getGender().equals("Female")))
            throw new InsertException("Invalid gender");
        if (staff.getPhoneNumber().length() != 11)
            throw new InsertException("Invalid phone number");
        if (!(staff.getType().equals("Director") || staff.getType().equals("Supply Staff") || staff.getType().equals("Salesman") || staff.getType().equals("Contracts Manager")))
            throw new InsertException("Invalid type");
        if (centerMapper.findByCenterName(staff.getSupplyCenter()) == null)
            throw new InsertException("Invalid Supply Center [" + staff.getSupplyCenter() + "]");
        if (staffMapper.selectByNumber(staff.getNumber()) != null)
            throw new InsertException("Duplicated Staff Number [" + staff.getNumber() + "]");
        if (staffMapper.insert(staff) != 1)
            throw new InsertException("Unknown error when insert Staff");

    }

    @Override
    public Staff selectByNumber(String number) {
        return staffMapper.selectByNumber(number);
    }

    @Override
    public List<Map<String, Object>> getAllStaffCount() {
        return staffMapper.selectStaffTypeCount();
    }


    @Override
    public RespBean DeleteStaff(HttpServletRequest request, HttpServletResponse response, String number, String login_user) {
        //拿到员工信息
        Staff staff = staffMapper.selectByNumber(number);
        Login_in user = (Login_in) iLogin_inService.getLogin_inByCookie(response, request, login_user);
        if (staff == null) {
            return RespBean.not_exist();
        }
        if (user.getType().equals("CEO")) {
            login_inMapperl.DeleteUser(number);
            staffMapper.DeleteStaffByNumber(number);
            orderMapper.UpdateOrderByDeleteSalesman(number);
            stockMapper.UpdateStockInRecordByNumber(number);
            orderMapper.UpdateContractByDeleteStaff(number);
            return RespBean.operation_success(RespBeanEnum.OPERATION_SUCCESS);
        } else {
            Staff director = staffMapper.selectByNumber(user.getUsername());
            if (!director.getSupplyCenter().equals(staff.getSupplyCenter())) {
                return RespBean.operation_error();
            } else {
                login_inMapperl.DeleteUser(number);
                staffMapper.DeleteStaffByNumber(number);
                orderMapper.UpdateOrderByDeleteSalesman(number);
                stockMapper.UpdateStockInRecordByNumber(number);
                orderMapper.UpdateContractByDeleteStaff(number);
                return RespBean.operation_success(RespBeanEnum.OPERATION_SUCCESS);

            }

        }

    }

    @Override
    public RespBean doUpdateStaff(HttpServletRequest request, HttpServletResponse response, String number, String type) {
        Staff staff = staffMapper.selectByNumber(number);
        if (staff == null) {
            return RespBean.operation_error1();
        }
        //开始员工表和用户表都要更新
        staffMapper.UpdateType(type, number);
        login_inMapperl.UpdateUserType(type, number);
        return RespBean.operation_success();

    }

    @Override
    public  RespBean UpdateMobilePhone(String number, String phone){
        Staff staff = staffMapper.selectByNumber(number);
        if(staff == null)
            return RespBean.not_exist();
        boolean isMatch= Mobile.isMobile(phone);
        if(!isMatch){
            return RespBean.phone_error();
        }
        staffMapper.UpdatePhoneNUmber(phone,number);
        return RespBean.operation_success();
    }

    @Override
    public  RespBean SelectStaff(String name, String number, HttpServletRequest request, HttpServletResponse response){
      List<Staff> list= staffMapper.SelectStaffByNameAndNumber(number,name);
      //开始存起来
      String staff= UUIDUtil.uuid();
      redisTemplate.opsForValue().set(staff,list);
        CookieUtil.setCookie(request,response,"STAFF",staff);
    return RespBean.operation_success();

    }

    @Override
    public List<Staff> getStaffByCookie(String staff){
        if (staff==null){
            return null;
        }
       List<Staff> staffList=(List<Staff>) redisTemplate.opsForValue().get(staff);
       return staffList;
    }

}