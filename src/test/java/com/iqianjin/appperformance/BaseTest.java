package com.iqianjin.appperformance;

import com.iqianjin.appperformance.base.DriverManger;
import com.iqianjin.appperformance.config.GlobalConfig;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= {AppPerformanceApplication.class})
public class BaseTest {
    private static Logger logger = LoggerFactory.getLogger(BaseTest.class);
    @Autowired
    GlobalConfig globalConfig;
    @Autowired
    DriverManger driverManger;

    @BeforeEach
    public  void beforeSuite(){
        logger.info("*********测试开始*********");
    }
    @Test
    public void test(){
        System.out.println(globalConfig.getAndroidCapabilities());
    }

    @AfterEach
    public  void afterSuite() {
        logger.info("*********测试结束*********");
        driverManger.appiumDriver.quit();
    }

}
