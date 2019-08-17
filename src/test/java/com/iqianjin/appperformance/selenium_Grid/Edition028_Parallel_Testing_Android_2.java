package com.iqianjin.appperformance.selenium_Grid;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

@Execution(ExecutionMode.CONCURRENT)
public class Edition028_Parallel_Testing_Android_2 extends Edition028_Parallel_Testing_Base{
    protected String APP = "/Users/finup/IdeaProjects/app-performanceTest/src/test/java/com/iqianjin/appperformance/selenium_Grid/TheApp-v1.5.0.app.zip";
    @BeforeAll
    static void before(){
        System.out.println("################################");
    }
    @AfterAll
    static void after(){
        System.out.println("111################################");
    }
    @Test
    public void testLogin_Android() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "Android Emulator");
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("udid", "192.168.56.104:5555");
        capabilities.setCapability("systemPort", "8202");
        capabilities.setCapability("appPackage", "io.cloudgrey.the_app");
        capabilities.setCapability("appActivity", ".MainActivity");

        AndroidDriver driver = new AndroidDriver(new URL("http://localhost:4723/wd/hub"), capabilities);
        actualTest(driver);
    }

    @Test
    public void testLogin_iOS() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("platformVersion", "12.4");
        capabilities.setCapability("deviceName", "iPhone X");
        capabilities.setCapability("udid", "58CB20DB-9119-4ECF-86B6-5987B8D0DD45");
        capabilities.setCapability("wdaLocalPort", 8100);
        capabilities.setCapability("app", APP);

        IOSDriver driver = new IOSDriver<>(new URL("http://localhost:4723/wd/hub"), capabilities);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        actualTest(driver);
    }
}
