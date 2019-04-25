package com.iqianjin.appperformance.cases;

import com.iqianjin.appperformance.base.DriverManger;
import com.iqianjin.appperformance.getData.GetPerformanceData;
import com.iqianjin.appperformance.util.CommandUtil;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class BaseCase {
    private static Logger logger = LoggerFactory.getLogger(BaseCase.class);

//    @Autowired
//    private GetPerformanceData getPerformanceData;

    @Autowired
    private DriverManger driverManger;

    @Value("${platformName}")
    public String platformName;

    public AppiumDriver appiumDriver;

    @Autowired
    public BaseCase(AppiumDriver<? extends MobileElement> appiumDriver) {
        this.appiumDriver = appiumDriver;
    }
//    public By homeTab = By.id("tab1Iv");
//    public By productTab = By.id("tab2Iv");
//    public By findTab = By.id("tab3Iv");
//    public By myTab = By.id("tab4Iv");
//    public By tv_start = By.id("tv_start_time"); //启动动画跳过按钮

    public String homeTab = "首页tab";
    public String productTab = "产品tab";
    public String findTab = "发现tab";
    public String myTab = "我的tab";
    public String tv_start = "开机广告";

    //异常的弹框，比如开机动画，都放在dialogs list
    //todo:不支持webview
    List<String> dialogs = Stream.of("xpath://*[@text='跳过']",
            "id:createAccountBack",
            "xpath://*[@text='取消']",
            "id:dialogALeftButton",
            "xpath://*[@text='同意授权']")
            .collect(Collectors.toList());

//    public WebElement find(String locatorName) {
//
//        WebElement element = null;
//        try {
//            element = findByWait(locatorName,3);
//        } catch (Exception e) {
//            element = findByWait(locatorName,3);
//        }
//        return element;
//    }

    /**
     * 等待timeOutInSeconds秒，每500毫秒查找一次，直到元素by出现
     *
     * @param locatorName
     * @return
     */
    public WebElement findByWait(String locatorName, long timeOutInSeconds) {
        WebElement element = null;
        locatorName = driverManger.getElement(locatorName);
        String locatorStrategy = locatorName.split(":")[0];
        String locatorValue = locatorName.split(":")[1];
        WebDriverWait wait = new WebDriverWait(appiumDriver, timeOutInSeconds, 500);
        try {
            if ("id".equalsIgnoreCase(locatorStrategy)) {
                element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(locatorValue)));
            } else {
                element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locatorValue)));
            }
        } catch (Exception e) {
            logger.info("未找到元素by:{}开始处理异常弹框元素", locatorName);
            for (String temp : dialogs) {
                String locatorValueOther = temp.split(":")[1];
                switch (locatorStrategy) {
                    case "xpath":
                        if (null != findElementByXPath(locatorValueOther)) {
                            logger.info("开始点击xpath元素locatorValue:{}", locatorValueOther);
                            findElementByXPath(locatorValueOther).click();
                        }
                        break;
                    case "id":
                        if (null != findElementById(locatorValueOther)) {
                            logger.info("开始点击id元素locatorValue:{}", locatorValueOther);
                            findElementById(locatorValueOther).click();
                        }
                        break;
                }
            }
        }

        return element;
    }

    public WebElement findElementByXPath(String xpath) {
        try {
            return appiumDriver.findElementByXPath(xpath);
        } catch (Exception e) {
            logger.error("未找到元素xpath:{}", xpath);
        }
        return null;
    }

    public WebElement findElementById(String id) {
        try {
            return appiumDriver.findElementById(id);
        } catch (Exception e) {
            logger.error("未找到元素id:{}", id);
        }
        return null;
    }

    public List<WebElement> findElements(String locatorName) {
        locatorName = driverManger.getElement(locatorName);
        String locatorStrategy = locatorName.split(":")[0];
        String locatorValue = locatorName.split(":")[1];
        if (locatorStrategy.equalsIgnoreCase("id")) {
            return appiumDriver.findElementsById(locatorValue);
        } else {
            return appiumDriver.findElementsByXPath(locatorValue);
        }
    }


    public void click(String text) {
        try {
            findByWait(text, 2).click();
        } catch (Exception e) {
            logger.info("报错了哈，再试一次:{}", text);
            e.printStackTrace();
            findByWait(text, 2).click();
        }
    }


    public void sendKeys(String locatorName, String content) {
        findByWait(locatorName, 1).clear();
        findByWait(locatorName, 1).sendKeys(content);
    }

    /**
     * 无限循环，直到输入的内容和content一致 手机号
     *
     * @param
     * @param
     */
//    public void sendKeysPhone(By by, String content) {
//        while (true) {
//            clear(by);
//            find(by).sendKeys(content);
//            String temp = find(by).getText().replace(" ", "");
//            logger.info("输入内容是temp:{}", temp);
//            if (temp.equals(content)) {
//                break;
//            }
//        }
//    }

    /**
     * 切换到webview
     * 注意：兼容性可能会员问题
     */
    public void context_to_webview() {
        logger.info("等待5秒");
        sleep(5);
        Set<String> ContextHandles = appiumDriver.getContextHandles();
        logger.info("当前ContextHandles{}", ContextHandles);
        logger.info(appiumDriver.getPageSource());
        sleep(5);
        logger.info("当前ContextHandles{}", ContextHandles);
        if (ContextHandles.size() == 1) {
            logger.info("该web页未开启debug状态");
        } else {
            ContextHandles.forEach((handle) -> {
                if (handle.contains("WEBVIEW_com.iqianjin.client")) {
                    logger.info("切换到webView:{}", handle);
                    appiumDriver.context(handle);
                }
            });
        }
    }

    /**
     * 滑动到指定元素
     *
     * @param to
     * @param from
     * @param locatorName
     * @param timeOutInSeconds
     */
    public WebElement swipUpWaitElement(double to, double from, String locatorName, long timeOutInSeconds) {
        WebElement element = null;
        while (true) {
            swipeUpOrDown(to, from);
//            WebDriverWait wait = new WebDriverWait(appiumDriver, timeOutInSeconds, 500);
//            element = wait.until(ExpectedConditions.presenceOfElementLocated(by));
            element = findByWait(locatorName, timeOutInSeconds);
            if (element != null) {
                return element;
            }
        }

//        By by;
//        WebDriverWait wait = new WebDriverWait(appiumDriver, timeOutInSeconds, 500);
//        locatorName = driverManger.getElement(locatorName);
//        String locatorStrategy = locatorName.split(":")[0];
//        String locatorValue = locatorName.split(":")[1];
//        while (true){
//            swipeUpOrDown(to,from);
//            if("id".equalsIgnoreCase(locatorStrategy)){
//               by  = By.id(locatorValue);
//            }else {
//                by  = By.xpath(locatorValue);
//            }
//            element = wait.until(ExpectedConditions.presenceOfElementLocated(by));
//            logger.error("element:{}",element);
//            if (null!=element){
//                return element;
//            }
//        }

    }

    /**
     * 上滑和下滑，根据系数滑动
     *
     * @param to   滑动起始位系数
     * @param from 滑动终点系数
     */
    public void swipeUpOrDown(double to, double from) {
        int height = appiumDriver.manage().window().getSize().height;
        int width = appiumDriver.manage().window().getSize().width;
        TouchAction action = new TouchAction(appiumDriver);
        try {
            action.longPress(PointOption.point(width / 2, (int) (height * to)))
                    .moveTo(PointOption.point(width / 2, (int) (height * from)))
                    .release().perform();
        } catch (Exception e) {
            logger.error("向上滑下滑动失败");
            e.printStackTrace();
        }

    }

    /**
     * 左滑，右滑
     */
    public void swipeLeftOrRight(double to, double from) {
        logger.info("开始左右滑动");
        int width = appiumDriver.manage().window().getSize().width;
        int height = appiumDriver.manage().window().getSize().height;
        TouchAction action = new TouchAction(appiumDriver);
        action.longPress(PointOption.point((int) (width * to), height / 2)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
                .moveTo(PointOption.point((int) (width * from), height / 2)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
                .release().perform();
    }

    /**
     * 滑动到底部
     */
    public void swipeToBottom(double to, double from) {
        if (appiumDriver.getPageSource().contains("去别处看看吧")) {
            return;
        } else {
            while (!appiumDriver.getPageSource().contains("没有更多")) {
                swipeUpOrDown(to, from);
            }
        }
    }

    /**
     * 对于滑动到底部 没有提示文案的
     *
     * @param by
     * @param to
     * @param from
     */
    public void swipeToBottomSuper(String by, double to, double from) {
        sleep(1);
        if (appiumDriver.getPageSource().contains("去别处看看吧") || appiumDriver.getPageSource().contains("暂无内容")) {
            return;
        }
        List<WebElement> list = findElements(by);
        String pointTo;
        if ("android".equalsIgnoreCase(platformName)) {
            pointTo = list.get(list.size() - 1).getAttribute("text");
        } else {
            pointTo = list.get(list.size() - 1).getAttribute("name");
        }
        boolean flag = true;

        while (flag) {
            swipeUpOrDown(to, from);
            String pointFrom;
            if ("android".equalsIgnoreCase(platformName)) {
                pointFrom = list.get(list.size() - 1).getAttribute("text");
            } else {
                pointFrom = list.get(list.size() - 1).getAttribute("name");
            }
            if (!pointFrom.equals(pointTo)) {
                pointTo = pointFrom;
            }
            logger.info("pointTo值是:{}pointFrom值是:{}", pointTo, pointFrom);
            if (pointTo.equals(pointFrom)) {
                logger.info("页面再也划不动了");
                flag = false;
            }
        }
    }

    /**
     * 物理返回
     */
    public void goBack() {
        appiumDriver.navigate().back();
    }

    public void sleep(long sec) {
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String getClassName(String name) {
        String[] s = name.split("\\.");
        return s[s.length - 1];
    }

    public void startMonitoring() {
//        Thread t = new Thread(getPerformanceData);
//        t.start();
        if ("android".equalsIgnoreCase(platformName)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    CommandUtil.getAndroidLog();
                }
            }).start();
        }
    }
//    public void stopMonitoring(String sheetName){
//        getPerformanceData.shutDown(sheetName);
//    }


}
