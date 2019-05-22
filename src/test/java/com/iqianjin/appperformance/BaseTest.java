package com.iqianjin.appperformance;

import com.iqianjin.appperformance.base.DriverManger;
import com.iqianjin.appperformance.config.GlobalConfig;
import com.iqianjin.appperformance.util.CommandUtil;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes= {AppPerformanceApplication.class})
public class BaseTest {
    private static Logger logger = LoggerFactory.getLogger(BaseTest.class);

    @BeforeAll
    static void beforeAll(){

    }
    @BeforeEach
    void beforeEach(){
        logger.info("*********测试开始*********");
        CommandUtil.androidMonitoring();
    }

    @AfterEach
    void afterEach() {
        CommandUtil.androidMonitoring();
    }
    @AfterAll
    static void afterAll(){
        logger.info("*********测试结束*********");
        //将android结果pull到项目当前目录
        CommandUtil.getAndroidImportFile();
    }
}
