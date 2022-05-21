package com.proj.sustc;

import com.proj.sustc.service.IStaffService;
import net.sourceforge.groboutils.junit.v1.MultiThreadedTestRunner;
import net.sourceforge.groboutils.junit.v1.TestRunnable;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

@SpringBootTest
@RunWith(SpringRunner.class)
class HighConcurrencyTests {
    @Autowired
    DataSource dataSource;
    @Autowired
    IStaffService iStaffService;

    @Test
    public void methodATest() throws Throwable {
        // 构造一个Runner
        TestRunnable runner = new TestRunnable() {
            @Override
            public void runTest() throws Throwable {
                System.out.println(dataSource.getConnection());
            }
        };
        // 并发线程数量
        int runnerCount = 100;
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
