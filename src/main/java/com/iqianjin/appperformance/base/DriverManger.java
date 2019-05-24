package com.iqianjin.appperformance.base;

import com.iqianjin.appperformance.config.GlobalConfig;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class DriverManger {
    private static Logger logger = LoggerFactory.getLogger(DriverManger.class);

    public AppiumDriver appiumDriver;
    static DriverManger driverManger;

    public static DriverManger getInstance() {
        if (driverManger == null) {
            driverManger = new DriverManger();
        }
        return driverManger;
    }

    public void createDriver() {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        Yaml yaml = new Yaml();
        InputStream in = DriverManger.class.getResourceAsStream("/globalConfig.yml");
        GlobalConfig config = yaml.loadAs(in, GlobalConfig.class);

        if ("android".equalsIgnoreCase(config.getAppium().getPlatName())) {
            String chromedriverDir = System.getProperty("user.dir") + "/src/main/resources/chromedrivers";
            desiredCapabilities.setCapability("chromedriverExecutableDir", chromedriverDir);
            config.getAppium().getAndroidCapabilities().forEach((key, value) -> {
                desiredCapabilities.setCapability(key, value);
            });
            try {
                appiumDriver = new AndroidDriver(new URL(config.getAppium().getUrl()), desiredCapabilities);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else if ("ios".equalsIgnoreCase(config.getAppium().getPlatName())) {
            config.getAppium().getIosCapabilities().forEach((key, value) -> {
                desiredCapabilities.setCapability(key, value);
            });
            try {
                appiumDriver = new IOSDriver(new URL(config.getAppium().getUrl()), desiredCapabilities);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        appiumDriver.manage().timeouts().implicitlyWait(config.getAppium().getWait(), TimeUnit.SECONDS);
    }

    public String getPlatform() {
        return appiumDriver.getCapabilities().getCapability("platformName").toString();
    }

}
