package com.iqianjin.appperformance.leeCode;

import com.iqianjin.appperformance.base.DriverManger;
import io.qameta.allure.Attachment;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.OutputType;

import java.io.IOException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MyListener implements BeforeTestExecutionCallback, AfterTestExecutionCallback {

    @Override
    public void beforeTestExecution(ExtensionContext extensionContext) throws Exception {
        takeScreenShotBefore();
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        takeScreenShotAfter();
    }

    @Attachment(value = "执行前截图", type = "image/png")
    private byte[] takeScreenShotBefore() throws IOException {

        return DriverManger.getInstance().appiumDriver.getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "执行后截图", type = "image/png")
    private byte[] takeScreenShotAfter() throws IOException {

        return DriverManger.getInstance().appiumDriver.getScreenshotAs(OutputType.BYTES);
    }

}
