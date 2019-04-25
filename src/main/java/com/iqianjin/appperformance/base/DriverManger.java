package com.iqianjin.appperformance.base;

import com.iqianjin.appperformance.config.ElementTypeEnum;
import com.iqianjin.appperformance.config.GlobalConfig;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@Component
public class DriverManger {
    private static Logger logger = LoggerFactory.getLogger(DriverManger.class);
    public AppiumDriver appiumDriver;

    @Autowired
    private GlobalConfig globalConfig;

    @Value("${platformName}")
    public String platformName;


    @Bean
    public AppiumDriver createDriver() {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        if ("android".equalsIgnoreCase(platformName)) {
            globalConfig.getAndroidCapabilities().forEach((key, value) -> {
                desiredCapabilities.setCapability(key, value);
            });
            try {
                appiumDriver = new AndroidDriver(new URL(globalConfig.getUrl()), desiredCapabilities);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else if ("ios".equalsIgnoreCase(platformName)) {
            globalConfig.getIosCapabilities().forEach((key, value) -> {
                desiredCapabilities.setCapability(key, value);
            });
            try {
                appiumDriver = new IOSDriver(new URL(globalConfig.getUrl()), desiredCapabilities);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        appiumDriver.manage().timeouts().implicitlyWait(globalConfig.getWait(), TimeUnit.SECONDS);
        return appiumDriver;
    }

    public String getElement(String ele) {
        if (platformName.equalsIgnoreCase("android") && ele != null) {
            return ElementTypeEnum.mapAndroid.get(ele);
        } else if (platformName.equalsIgnoreCase("ios") && ele != null) {
            return ElementTypeEnum.mapIos.get(ele);
        } else {
            logger.error("出现这个就炸了！！！");
            return null;
        }
    }

}
