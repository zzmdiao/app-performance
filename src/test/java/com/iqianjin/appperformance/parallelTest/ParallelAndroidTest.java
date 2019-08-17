package com.iqianjin.appperformance.parallelTest;

import com.iqianjin.appperformance.parallel.ParllelAndroidDriver;
import com.iqianjin.appperformance.parallel.config.SetEnv;
import com.iqianjin.appperformance.parallel.pageCase.*;
import com.iqianjin.appperformance.util.AlertUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


@Slf4j
public class ParallelAndroidTest {
    public final static int num = 2;

    @BeforeAll
    static void beforeAll() {
        ParllelAndroidDriver.getInstance();
        log.info("*********【android】测试开始*********");
        ParBase.getInstance().setPlatName("android");
//        SetEnv.getInstance().androidMonitoring();
        ParLogin.getInstance().login("zzm001", "test123");

    }

    @AfterAll
    static void afterAll() {
        try {
            log.info("【android】等待100s");
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        SetEnv.getInstance().androidMonitoring();
        SetEnv.getInstance().getAndroidImportFile();
        if (ParllelAndroidDriver.getInstance().appiumDriverAndroid != null) {
            log.info("关闭appiumDriverAndroid");
            ParllelAndroidDriver.getInstance().appiumDriverAndroid.quit();
        }
        log.info("*********【android】测试结束*********");
        AlertUtils.alertQYWX(" [android]");
    }

    @Test
    @DisplayName("切换四个tab--android")
    public void switchTabAndroid() {
        ParSwitchTab.getInstance().changeTab(num);
    }

    @Test
    @DisplayName("浏览我的资产--android")
    public void browseAsserts() {
        ParMyAsserts.getInstance().browseAsserts(num);
    }

    @Test
    @DisplayName("出借记录相关--android")
    public void aybProductRecordTest() {
        ParLendRecord.getInstance().isRenewed();
        ParLendRecord.getInstance().aybProductRecord(num);
        ParLendRecord.getInstance().yjbProductRecord(num);
        ParLendRecord.getInstance().zcbProductRecord(num);
        ParLendRecord.getInstance().sanbiaoRecord(num);
        ParBase.getInstance().parGoBack();
    }

    @Test
    @DisplayName("浏览资金流水--android")
    void fundTab() {
        ParFundFlow.getInstance().fundTab(num);
    }

}
