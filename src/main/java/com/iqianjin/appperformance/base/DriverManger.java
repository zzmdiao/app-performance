package com.iqianjin.appperformance.base;

import com.iqianjin.appperformance.config.GlobalConfig;
import com.iqianjin.appperformance.config.ElementTypeEnum;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class DriverManger {
    private static Logger logger = LoggerFactory.getLogger(DriverManger.class);

    public AppiumDriver appiumDriver;
    private GlobalConfig globalConfig = new GlobalConfig();

    public AppiumDriver createDriver() {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        if ("android".equalsIgnoreCase(GlobalConfig.platformName)) {
            String chromedriverDir = System.getProperty("user.dir") + "/src/main/resources/chromedrivers";
            desiredCapabilities.setCapability("chromedriverExecutableDir", chromedriverDir);
            GlobalConfig.androidCapabilities.forEach((key, value) -> {
                desiredCapabilities.setCapability(key, value);
            });
            try {
                appiumDriver = new AndroidDriver(new URL(GlobalConfig.url), desiredCapabilities);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else if ("ios".equalsIgnoreCase(GlobalConfig.platformName)) {
            globalConfig.getIosCapabilities().forEach((key, value) -> {
                desiredCapabilities.setCapability(key, value);
            });
            try {
                appiumDriver = new IOSDriver(new URL(GlobalConfig.url), desiredCapabilities);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        appiumDriver.manage().timeouts().implicitlyWait(GlobalConfig.wait, TimeUnit.SECONDS);
        return appiumDriver;
    }

    public String getElement(String ele) {
        if (GlobalConfig.platformName.equalsIgnoreCase("android") && ele != null) {
            return ElementTypeEnum.mapAndroid.get(ele);
        } else if (GlobalConfig.platformName.equalsIgnoreCase("ios") && ele != null) {
            return ElementTypeEnum.mapIos.get(ele);
        } else {
            logger.error("出现这个就炸了！！！");
            return null;
        }
    }


}
