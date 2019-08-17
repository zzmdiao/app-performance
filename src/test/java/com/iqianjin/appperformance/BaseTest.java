package com.iqianjin.appperformance;

import com.iqianjin.appperformance.base.DriverManger;
import com.iqianjin.appperformance.cases.*;
import com.iqianjin.appperformance.util.CommandUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static com.iqianjin.appperformance.util.AlertUtils.alertQYWX;

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BaseTest {

    //    private static Logger logger = LoggerFactory.getLogger(BaseTest.class);
    //android 打包时需要开发同学打开webview调试模式：WebView.setWebContentsDebuggingEnabled(true);
    //ios 本地安装 brew install ios-webkit-debug-proxy，手机设置-safari-高级-web检查器打开
    public final static int num = 10;

    @Value("${online.username}")
    private String onlineUsername;
    @Value("${online.password}")
    private String onlinePassword;

    @Value("${test.username}")
    private String testUsername;
    @Value("${test.password}")
    private String testPassword;
    @Value("${test.envir}")
    private String testEnvir;

    @BeforeAll
    static void beforeAll() {
        log.info("开启appium");
        DriverManger.getInstance().createDriver();
//        CommandUtil.androidMonitoring();
//        GetIosSet.getInstance().startIosMonitor();
        log.info("*********测试开始*********");
    }

    @AfterAll
    static void afterAll() {
        try {
            log.info("等待100s");
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        CommandUtil.androidMonitoring();
        //将android结果pull到项目当前目录
        CommandUtil.getAndroidImportFile();
        GetIosSet.getInstance().stopIosMonitor();
        if (DriverManger.getInstance().appiumDriver != null ) {
            log.info("关闭appium");
            DriverManger.getInstance().appiumDriver.quit();
        }
        log.info("*********测试结束*********");
        alertQYWX("");
    }


    @Test
    @Order(1)
    @DisplayName("切换四个tab")
    public void switchTab() {
        Login.getInstance().login(testUsername, testPassword);
        SwitchTab.getInstance().changeTab(num);
    }

    @Test
    @Order(2)
    @DisplayName("出借记录相关")
    public void aybProductRecordTest() {
        LendRecord.getInstance().isRenewed();
        LendRecord.getInstance().aybProductRecord(num);
        LendRecord.getInstance().yjbProductRecord(num);
        LendRecord.getInstance().zcbProductRecord(num);
        LendRecord.getInstance().sanbiaoRecord(num);
        BaseCase.getInstance().goBack();
    }

    @Test
    @Order(3)
    @DisplayName("浏览资金流水")
    void fundTab() {
        FundFlow.getInstance().fundTab(num);
    }

    @Test
    @Order(4)
    @DisplayName("浏览我的资产、加息劵、红包")
    void browseAsserts() {
        MyAsserts.getInstance().browseAsserts(num);
    }

    @Test
    @Order(5)
    @DisplayName("购买爱盈宝and月进宝")
    public void aybInvestProductTest() {
        GetIosSet.getInstance().switchEnvironment(testEnvir);
        Login.getInstance().loginAgain(testUsername, testPassword);
        InvestProduct.getInstance().buyAYB(num, "1000");
        InvestProduct.getInstance().buyYJB(num, "1000");
    }
}
