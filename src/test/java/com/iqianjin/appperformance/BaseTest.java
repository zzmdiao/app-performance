package com.iqianjin.appperformance;

import com.iqianjin.appperformance.base.DriverManger;
import com.iqianjin.appperformance.cases.*;
import com.iqianjin.appperformance.util.CommandUtil;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BaseTest {

    private static Logger logger = LoggerFactory.getLogger(BaseTest.class);
    //android 打包时需要开发同学打开webview调试模式：WebView.setWebContentsDebuggingEnabled(true);
    //ios 本地安装 brew install ios-webkit-debug-proxy，手机设置-safari-高级-web检查器打开
    public final static  int num = 20;

    @BeforeAll
    static void beforeAll(){
        logger.info("开启appium");
        DriverManger.getInstance().createDriver();
        CommandUtil.androidMonitoring();
        logger.info("*********测试开始*********");
        Login.getInstance().login("zzm003", "test123");
    }

    @AfterAll
    static void afterAll(){
        CommandUtil.androidMonitoring();
        //将android结果pull到项目当前目录
        CommandUtil.getAndroidImportFile();
        if (DriverManger.getInstance().appiumDriver !=null && DriverManger.getInstance().getPlatform().equalsIgnoreCase("android")){
            logger.info("关闭appium");
            DriverManger.getInstance().appiumDriver.quit();
        }
        logger.info("*********测试结束*********");
    }


//    @Test
//    @Order(1)
//    @DisplayName("购买爱盈宝and月进宝")
//    public void aybInvestProductTest() {
//        InvestProduct.getInstance().buyAYB(num, "1000");
//        InvestProduct.getInstance().buyYJB(num, "1000");
//    }
//
//    @Test
//    @Order(2)
//    @DisplayName("切换四个tab")
//    public void switchTab() {
//        SwitchTab.getInstance().changeTab(num);
//    }
//
//    @Test
//    @Order(3)
//    @DisplayName("出借记录")
//    public void aybProductRecordTest() {
//        LendRecord.getInstance().aybProductRecord(num);
//        LendRecord.getInstance().yjbProductRecord(num);
//        LendRecord.getInstance().zcbProductRecord(num);
//        LendRecord.getInstance().sanbiaoRecord(num);
//        BaseCase.getInstance().goBack();
//    }
//
//    @Test
//    @Order(4)
//    @DisplayName("浏览资金流水")
//    void fundTab() {
//        FundFlow.getInstance().fundTab(num);
//    }
}
