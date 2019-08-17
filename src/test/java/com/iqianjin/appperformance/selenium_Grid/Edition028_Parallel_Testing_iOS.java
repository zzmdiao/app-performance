package com.iqianjin.appperformance.selenium_Grid;

import io.appium.java_client.ios.IOSDriver;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

public class Edition028_Parallel_Testing_iOS extends Edition028_Parallel_Testing_Base{

//    protected String APP = "https://github.com/cloudgrey-io/the-app/releases/download/v1.5.0/TheApp-v1.5.0.app.zip";
    protected String APP = "/Users/finup/IdeaProjects/app-performanceTest/src/test/java/com/iqianjin/appperformance/selenium_Grid/TheApp-v1.5.0.app.zip";

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
        actualTest(driver);
    }
}
