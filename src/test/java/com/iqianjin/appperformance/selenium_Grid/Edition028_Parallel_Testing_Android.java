package com.iqianjin.appperformance.selenium_Grid;

import io.appium.java_client.android.AndroidDriver;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.openqa.selenium.remote.DesiredCapabilities;

@Execution(ExecutionMode.CONCURRENT)
public class Edition028_Parallel_Testing_Android extends Edition028_Parallel_Testing_Base{
    protected String APP = "/Users/finup/IdeaProjects/app-performanceTest/src/test/java/com/iqianjin/appperformance/selenium_Grid/TheApp-v1.5.0.apk";

    @Test
    public void testLogin_Android() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "mx6");
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("udid", "95AQACQMEBSFB");
        capabilities.setCapability("systemPort", "8203");
        capabilities.setCapability("appPackage", "io.cloudgrey.the_app");
        capabilities.setCapability("appActivity", ".MainActivity");

        AndroidDriver driver = new AndroidDriver(new URL("http://localhost:4723/wd/hub"), capabilities);
        actualTest(driver);
    }

    @Test
    public void testLogin_Android2() throws MalformedURLException {
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

}
