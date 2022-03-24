/*
package com.proj.sustc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootTest
class SustcApplicationTests {
    @Autowired
    private DataSource dataSource;
    @Test
    void contextLoads() {
    }

    @Test
    void getConnection() throws SQLException {
        System.out.println(dataSource.getConnection());
    }
}
*/

package com.proj.sustc;

import com.proj.sustc.entity.Staff;
import com.proj.sustc.service.IStaffService;
import net.sourceforge.groboutils.junit.v1.MultiThreadedTestRunner;
import net.sourceforge.groboutils.junit.v1.TestRunnable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootTest
class SustcApplicationTests implements Runnable {
    @Autowired
    DataSource dataSource;
    @Autowired
    IStaffService iStaffService;

    @Test
    void contextLoads() {
    }

    @Override
    public void run() {
        Staff staff = iStaffService.selectByNumber("11311024");
        try {
            System.out.println(dataSource.getConnection());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getConnection() throws SQLException {

    }

    @Test
    public void methodATest() throws Throwable {
        // 构造一个Runner
        TestRunnable runner = new TestRunnable() {
            @Override
            public void runTest() throws Throwable {
                System.out.println(iStaffService.selectByNumber("11311024"));
            }
        };
        // 并发线程数量
        int runnerCount = 5;
        TestRunnable[] trs = new TestRunnable[runnerCount];
        for (int i = 0; i < runnerCount; i++) {
            trs[i] = runner;
        }
        // 用于执行多线程测试用例的Runner，将前面定义的单个Runner组成的数组传入
        MultiThreadedTestRunner mttr = new MultiThreadedTestRunner(trs);
        // 并发执行测试内容
        mttr.runTestRunnables();
    }
}
