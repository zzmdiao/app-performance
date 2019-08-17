package com.iqianjin.appperformance.parallelTest;

import com.iqianjin.appperformance.parallel.ParllelIosDriver;
import com.iqianjin.appperformance.parallel.pageCase.*;
import com.iqianjin.appperformance.util.AlertUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;

import static org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT;

@Execution(CONCURRENT)
@Slf4j
public class ParallelIosTest {
    public final static int num = 2;

    @BeforeAll
    static void beforeAll() {
        ParllelIosDriver.getInstance().createIosDriver();
        ParBase.getInstance().setPlatName("ios");
        log.info("*********【ios】测试开始*********");
        ParLogin.getInstance().login("zzm003", "test123");
    }

    @AfterAll
    static void afterAll() {
        try {
            log.info("【ios】等待100s");
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        SetEnv.getInstance().stopIosMonitor();
        if (ParllelIosDriver.getInstance().appiumDriverios != null ) {
            log.info("关闭appiumDriverios");
            ParllelIosDriver.getInstance().appiumDriverios.quit();
        }
        log.info("*********【ios】测试结束*********");
        AlertUtils.alertQYWX(" [ios]");
    }

    @Test
    @DisplayName("切换四个tab--ios")
    public void switchTabAndroid() {
        ParSwitchTab.getInstance().changeTab(num);
    }

    @Test
    @DisplayName("浏览我的资产--ios")
    public void browseAsserts() {
        ParMyAsserts.getInstance().browseAsserts(num);
    }

    @Test
    @DisplayName("出借记录相关--ios")
    public void aybProductRecordTest() {
        ParLendRecord.getInstance().isRenewed();
        ParLendRecord.getInstance().aybProductRecord(num);
        ParLendRecord.getInstance().yjbProductRecord(num);
        ParLendRecord.getInstance().zcbProductRecord(num);
        ParLendRecord.getInstance().sanbiaoRecord(num);
        ParBase.getInstance().parGoBack();
    }

    @Test
    @DisplayName("浏览资金流水--ios")
    void fundTab() {
        ParFundFlow.getInstance().fundTab(num);
    }

}
