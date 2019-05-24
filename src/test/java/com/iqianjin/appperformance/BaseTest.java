package com.iqianjin.appperformance;

import com.iqianjin.appperformance.base.DriverManger;
import com.iqianjin.appperformance.cases.BaseCase;
import com.iqianjin.appperformance.util.CommandUtil;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseTest {

    private static Logger logger = LoggerFactory.getLogger(BaseTest.class);
    //android 打包时需要开发同学打开webview调试模式：WebView.setWebContentsDebuggingEnabled(true);
    //ios 本地安装 brew install ios-webkit-debug-proxy，手机设置-safari-高级-web检查器打开
    @BeforeAll
    static void beforeAll(){
        DriverManger.getInstance().createDriver();
        CommandUtil.androidMonitoring();
        logger.info("*********测试开始*********");
    }

    @AfterAll
    static void afterAll(){
        CommandUtil.androidMonitoring();
        //将android结果pull到项目当前目录
        CommandUtil.getAndroidImportFile();
        DriverManger.getInstance().appiumDriver.quit();
        logger.info("*********测试结束*********");
    }

}
