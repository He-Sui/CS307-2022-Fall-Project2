package com.proj.sustc.service.implement;

import com.proj.sustc.entity.Login_in;
import com.proj.sustc.mapper.Login_inMapper;
import com.proj.sustc.mapper.StaffMapper;
import com.proj.sustc.service.ILogin_inService;
import com.proj.sustc.utils.CookieUtil;
import com.proj.sustc.utils.MD5Utils;
import com.proj.sustc.utils.UUIDUtil;
import com.proj.sustc.vo.LoginVO;
import com.proj.sustc.vo.RespBean;
import com.proj.sustc.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class Login_inServiceImpl implements ILogin_inService {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private Login_inMapper login_inMapper;
    @Autowired
    private StaffMapper staffMapper;

    @Override
    public void insert(Login_in login_in) {
        login_inMapper.insert(login_in);
    }

    @Override
    public RespBean doTest(HttpServletResponse response, HttpServletRequest request, LoginVO loginVO) {
        String username= loginVO.getUsername();
        String inputPass= loginVO.getPassword();
        Login_in login_in=login_inMapper.selectByUsername(username);



        if(login_in==null){
            return RespBean.login_error(RespBeanEnum.LOGIN_ERROR);
        }
        String dbPass=MD5Utils.secondPass(inputPass,login_in.getSalt());
        if(!dbPass.equals(login_in.getPassword())){
            return RespBean.login_error(RespBeanEnum.LOGIN_ERROR);
        }

        //信息储存到redis里面，首先要注入
        String login_in_user= UUIDUtil.uuid();
        //使得login_in和相应的key对应 cookie里存相应的key
        redisTemplate.opsForValue().set("login:"+login_in_user,login_in);
        CookieUtil.setCookie(request,response,"LOGIN_IN_USER",login_in_user);

        return RespBean.login_success();


    }
    @Override
    public Login_in getLogin_inByCookie(HttpServletResponse response,HttpServletRequest request,String login_in_user){
        if(login_in_user==null){
            return null;
        }
        Login_in login_in=(Login_in) redisTemplate.opsForValue().get("login:"+login_in_user);

        return login_in;
    }
}
