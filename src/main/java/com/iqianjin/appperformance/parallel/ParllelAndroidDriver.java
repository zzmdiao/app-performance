package com.iqianjin.appperformance.parallel;

import com.iqianjin.appperformance.base.DriverManger;
import com.iqianjin.appperformance.parallel.config.ParallelConfig;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class ParllelAndroidDriver {
    public AppiumDriver appiumDriverAndroid;

    public ParllelAndroidDriver() {
        appiumDriverAndroid = createAndroidDriver();
    }

    private AppiumDriver createAndroidDriver() {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        Yaml yaml = new Yaml();
        InputStream in = DriverManger.class.getResourceAsStream("/parallelConfig.yml");
        ParallelConfig config = yaml.loadAs(in, ParallelConfig.class);
        config.getAppium().getAndroidCapabilities().forEach((key, value) -> {
            desiredCapabilities.setCapability(key, value);
        });
        try {
            appiumDriverAndroid = new AndroidDriver(new URL(config.getAppium().getAndroidCapabilities().get("url")), desiredCapabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        appiumDriverAndroid.manage().timeouts().implicitlyWait(config.getAppium().getWait(), TimeUnit.SECONDS);
        return appiumDriverAndroid;
    }

    public static ParllelAndroidDriver getInstance() {
        return ParllelAndroidDriver.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final ParllelAndroidDriver INSTANCE = new ParllelAndroidDriver();
    }
}
