package com.iqianjin.appperformance.cases;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.springframework.stereotype.Component;

@Component
public class SwitchTab extends BaseCase {

    public SwitchTab(AppiumDriver<? extends MobileElement> appiumDriver) {
        super(appiumDriver);
    }

    public void changeTab(int number) {
        for (int i = 0; i < number; i++) {
            click(productTab);
            click(findTab);
            click(myTab);
            click(homeTab);
        }
    }

}
