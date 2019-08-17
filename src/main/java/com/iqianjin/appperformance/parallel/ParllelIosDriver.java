package com.iqianjin.appperformance.parallel;

import com.iqianjin.appperformance.base.DriverManger;
import com.iqianjin.appperformance.parallel.config.ParallelConfig;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class ParllelIosDriver {
    public AppiumDriver appiumDriverios;

    public ParllelIosDriver() {
        appiumDriverios = createIosDriver();
    }

    public AppiumDriver createIosDriver() {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        Yaml yaml = new Yaml();
        InputStream in = DriverManger.class.getResourceAsStream("/parallelConfig.yml");
        ParallelConfig config = yaml.loadAs(in, ParallelConfig.class);
        config.getAppium().getIosCapabilities().forEach((key, value) -> {
            desiredCapabilities.setCapability(key, value);
        });
        try {
            appiumDriverios = new IOSDriver(new URL(config.getAppium().getIosCapabilities().get("url")), desiredCapabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        appiumDriverios.manage().timeouts().implicitlyWait(config.getAppium().getWait(), TimeUnit.SECONDS);
        return appiumDriverios;
    }

    public static ParllelIosDriver getInstance() {
        return ParllelIosDriver.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final ParllelIosDriver INSTANCE = new ParllelIosDriver();
    }
}
