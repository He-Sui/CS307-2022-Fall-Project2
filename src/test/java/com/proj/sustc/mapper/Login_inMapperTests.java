/*
package com.proj.sustc.mapper;


import com.proj.sustc.entity.Login_in;
import com.proj.sustc.entity.Staff;
import com.proj.sustc.utils.MD5Utils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Login_inMapperTests implements Runnable{


    @Autowired
    private  StaffMapper staffMapper;
    @Autowired
    private  Login_inMapper login_inMapper;

    static List<Staff> list=new ArrayList<>();


//    static int i=0;
//
//    @Override
//    public synchronized void run(){
//       if(i<list.size()) {
//           Login_in login_in = new Login_in();
//           login_in.setSalt("1a2b3c4d");
//           Staff staff = list.get(i);
//               login_in.setPassword(MD5Utils.inputPassToDbPass("123456", "1a2b3c4d"));
//               login_in.setType(staff.getType());
//               login_in.setUsername(staff.getNumber());
//               login_inMapper.insert(login_in);
//               i++;
//               System.out.println(i);
//
//       }
//    }
//    @Test
//    public void insert(){
//        long begin=System.currentTimeMillis();
//        list=staffMapper.selectAllStaff();
//        // 创建线程池对象
//        ExecutorService service = Executors.newFixedThreadPool(7);//包含7个线程对象
//        // 创建Runnable实例对象
//        Login_inMapperTests r = new Login_inMapperTests();
//        // 从线程池中获取线程对象,然后调用MyRunnable中的run()
//        service.submit(r);
//        service.submit(r);
//        service.submit(r);
//        service.submit(r);
//        service.submit(r);
//        service.submit(r);
//        service.submit(r);
//        service.shutdown();
//        long end=System.currentTimeMillis();
//        System.out.println(end-begin);
//
//
//    }


  @Override
    public void run(){}


    @Test
    public void insert(){
        long begin=System.currentTimeMillis();
        List<Staff> staffList= staffMapper.selectAllStaff();
        Login_in login_in=new Login_in();
        login_in.setSalt("1a2b3c4d");

        for(int i=0;i<staffList.size();i++){
            Staff staff=staffList.get(i);
            login_in.setPassword(MD5Utils.inputPassToDbPass("123456","1a2b3c4d"));
            login_in.setType(staff.getType());
            login_in.setUsername(staff.getNumber());

            login_inMapper.insert(login_in);

        }
        long end=System.currentTimeMillis();
        System.out.println(end-begin);
        login_in.setUsername("12010923");
        login_in.setType("CEO");
         login_inMapper.insert(login_in);

    }
}
*/
package com.proj.sustc.mapper;


import com.proj.sustc.entity.Login_in;
import com.proj.sustc.entity.Staff;
import com.proj.sustc.utils.MD5Utils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Login_inMapperTests implements Runnable{


    @Autowired
    private  StaffMapper staffMapper;
    @Autowired
    private  Login_inMapper login_inMapper;

    static List<Staff> list=new ArrayList<>();


    @Override
    public void run(){}


    @Test
    public void insert(){
        long begin=System.currentTimeMillis();
        List<Staff> staffList= staffMapper.selectAllStaff();
        Login_in login_in=new Login_in();
        login_in.setSalt("1a2b3c4d");

        for(int i=0;i<staffList.size();i++){
            Staff staff=staffList.get(i);
            login_in.setPassword(MD5Utils.inputPassToDbPass("123456","1a2b3c4d"));
            login_in.setType(staff.getType());
            login_in.setUsername(staff.getNumber());

            login_inMapper.insert(login_in);

        }
        long end=System.currentTimeMillis();
        System.out.println(end-begin);
        login_in.setUsername("12010923");
        login_in.setType("CEO");
        login_inMapper.insert(login_in);
        login_in.setUsername("12012929");
        login_in.setType("CEO");
        login_inMapper.insert(login_in);
    }
}