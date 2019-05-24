package com.iqianjin.appperformance.cases;

import com.iqianjin.appperformance.base.DriverManger;
import com.iqianjin.appperformance.config.ElementTypeEnum;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.iqianjin.appperformance.util.CommandUtil.sleep;

public class BaseCase {
    private static Logger logger = LoggerFactory.getLogger(BaseCase.class);
    public AppiumDriver appiumDriver = DriverManger.getInstance().appiumDriver;

    public String platformName = DriverManger.getInstance().getPlatform();

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
    //webView 元素
    List<String> dialogsWebview = Stream.of(".dialog-invest-close")
            .collect(Collectors.toList());
    //ios左上角返回元素
    List<String> iosBack = Stream.of("public back",
            "ticket back n",
            "public back white").
            collect(Collectors.toList());

    /**
     * 等待timeOutInSeconds秒，每500毫秒查找一次，直到元素by出现
     *
     * @param locatorName
     * @return
     */
    public WebElement findByWait(String locatorName, long timeOutInSeconds) {
        WebElement element = null;
        locatorName = getElement(locatorName);
        String locatorStrategy = locatorName.split(":")[0];
        String locatorValue = locatorName.split(":")[1];
        WebDriverWait wait = new WebDriverWait(appiumDriver, timeOutInSeconds, 500);
//        if(platformName.equals("android") && !appiumDriver.getPageSource().contains("com.iqianjin")){
//            logger.error("app崩溃了:"+appiumDriver.getPageSource());
//            driverManger.createDriver();
//        }
        try {
            if ("id".equalsIgnoreCase(locatorStrategy)) {
                element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(locatorValue)));
            } else {
                element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locatorValue)));
            }

        } catch (Exception e) {
            logger.info("未找到元素locatorName:{},开始处理异常弹框元素", locatorName);
            //首先判断是否是webview页面
            for (String temp : dialogs) {
                locatorStrategy = temp.split(":")[0];
                String locatorValueOther = temp.split(":")[1];
                if (locatorStrategy.equals("xpath") && null != findElementByXPath(locatorValueOther)) {
                    logger.info("开始点击xpath元素locatorValue:{}", locatorValueOther);
                    findElementByXPath(locatorValueOther).click();
                    break;
                } else if (locatorStrategy.equals("id") && null != findElementById(locatorValueOther)) {
                    logger.info("开始点击id元素locatorValue:{}", locatorValueOther);
                    findElementById(locatorValueOther).click();
                    break;
                }
            }
        }

        return element;
    }

    public WebElement findByWait2(String locatorName, long timeOutInSeconds) {
        WebElement element = null;
        locatorName = getElement(locatorName);
        String locatorStrategy = locatorName.split(":")[0];
        String locatorValue = locatorName.split(":")[1];
        WebDriverWait wait = new WebDriverWait(appiumDriver, timeOutInSeconds, 500);

        try {
            if ("id".equalsIgnoreCase(locatorStrategy)) {
                element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorValue)));
            } else {
                element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorValue)));
            }
        } catch (Exception e) {
            logger.info("忽略这个");
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
        locatorName = getElement(locatorName);
        String locatorStrategy = locatorName.split(":")[0];
        String locatorValue = locatorName.split(":")[1];
        if (locatorStrategy.equalsIgnoreCase("id")) {
            return appiumDriver.findElementsById(locatorValue);
        } else {
            return appiumDriver.findElementsByXPath(locatorValue);
        }
    }


    public void click(String text) {
        WebElement element = findByWait(text, 2);
        if (element != null) {
            element.click();
            logger.info("点击元素:{}", text);
        } else {
            logger.error("元素为空:{}", text);
            goBack();
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
     * 切换到webview,并进行点击操作
     * 注意：兼容性可能会有问题，参考https://github.com/appium/appium/blob/e35e4f7bae04aecd8dafe61af669b22adf71d621/docs/en/writing-running-appium/web/chromedriver.md
     * android：打包时需要开发同学打开webview调试模式：WebView.setWebContentsDebuggingEnabled(true)
     * ios：本地安装 brew install ios-webkit-debug-proxy，手机设置-safari-高级-web检查器打开
     */
    public void context_to_webview(String element) {
        String temp = ElementTypeEnum.mapAndroid.get(element);
        logger.info("webview元素:{}", temp);
        sleep(3);
        Set<String> ContextHandles = appiumDriver.getContextHandles();
        logger.info("当前1 ContextHandles{}", ContextHandles);
        sleep(1);
        logger.info("当前2 ContextHandles{}", ContextHandles);
        if (ContextHandles.size() == 1) {
            logger.info("该web页未开启debug状态");
        } else {
            ContextHandles.forEach((handle) -> {
                if (handle.contains("WEBVIEW")) {
                    logger.info("切换到webView:{}", handle);
                    appiumDriver.context(handle);
                    try {
                        appiumDriver.findElementByCssSelector(temp).click();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            logger.info("切换到native");
            appiumDriver.context("NATIVE_APP");
        }
    }

    public boolean isWebview() {
        sleep(5);
        Set<String> ContextHandles = appiumDriver.getContextHandles();
        if (ContextHandles.size() == 1) {
            return false;
        } else {
            ContextHandles.forEach((handle) -> {
                if (handle.contains("WEBVIEW")) {
                    logger.info("切换到webView:{}", handle);
                    try {
                        appiumDriver.context(handle);
                    } catch (Exception e) {
                        logger.error("当前handle是:{}", handle);
                    }
                }
            });
            return true;
        }
    }

    public void clickWebview() {
        if (isWebview()) {
            for (String temp : dialogsWebview) {
                try {
                    WebElement element = appiumDriver.findElementByCssSelector(temp);
                    if (element != null) {
                        element.click();
                    }
                    logger.info("点击元素:{}", temp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        logger.info("切换到native");
        appiumDriver.context("NATIVE_APP");
    }

    /**
     * 上下滑动到指定元素
     *
     * @param to
     * @param from
     * @param locatorName
     * @param timeOutInSeconds
     */
    public WebElement swipUpWaitElement(double to, double from, String locatorName, long timeOutInSeconds) {
        WebElement element = null;
        logger.info("滑动到指定元素:{}", locatorName);
        while (true) {
            element = findByWait2(locatorName, timeOutInSeconds);
            if (element != null) {
                return element;
            }
            swipeUpOrDown(to, from);
        }

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
            logger.error("向上or向下滑动失败");
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
        try {
            action.longPress(PointOption.point((int) (width * to), height / 2)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
                    .moveTo(PointOption.point((int) (width * from), height / 2)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
                    .release().perform();
        } catch (Exception e) {
            logger.error("向左or向右滑动失败");
            e.printStackTrace();
        }

    }

    /**
     * 滑动指定次数
     *
     * @param to
     * @param from
     * @param num
     */
    public void swipeToNum(double to, double from, int num) {
        String pageSource = appiumDriver.getPageSource();
        if (pageSource.contains("去别处看看吧") || pageSource.contains("暂无预约记录") || pageSource.contains("暂无流水记录") || pageSource.contains("暂无资金流水")) {
            return;
        }
        for (int i = 0; i < num; i++) {
            swipeUpOrDown(to, from);
        }
    }

    /**
     * 滑动到底部
     */
    public void swipeToBottom(double to, double from) {
        String pageSource = appiumDriver.getPageSource();
        if (pageSource.contains("去别处看看吧") || pageSource.contains("暂无预约记录") || pageSource.contains("暂无流水记录") || pageSource.contains("暂无资金流水")) {
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
        String pageSource = appiumDriver.getPageSource();
        if (pageSource.contains("去别处看看吧") || pageSource.contains("暂无内容") || pageSource.contains("暂无预约记录") || pageSource.contains("暂无流水记录") || pageSource.contains("暂无资金流水")) {
            return;
        }
        List<WebElement> list = findElements(by);
        String pointTo = "";
        if ("android".equalsIgnoreCase(platformName) && list.size() > 0) {
            pointTo = list.get(list.size() - 1).getAttribute("text");
        } else if (list.size() > 0) {
            pointTo = list.get(list.size() - 3).getAttribute("name");
        }
        boolean flag = true;
        String pointFrom = "";
        while (flag) {
            swipeUpOrDown(to, from);
            String pageSourceOther = appiumDriver.getPageSource();
            if (pageSourceOther.contains("努力前进中") || pageSourceOther.contains("上拉加载更多")) {
                logger.info("努力前进中or上拉加载更多");
                swipeUpOrDown(to, from);
            }
            if ("android".equalsIgnoreCase(platformName) && list.size() > 0) {
                pointFrom = list.get(list.size() - 1).getAttribute("text");
            } else if (list.size() > 0) {
                pointFrom = list.get(list.size() - 3).getAttribute("name");
            }
            logger.info("pointTo值是:{}pointFrom值是:{}", pointTo, pointFrom);
            if (pointTo != null && pointFrom != null && pointTo.equals(pointFrom)) {
                logger.info("页面再也划不动了");
                flag = false;
            } else {
                pointTo = pointFrom;
            }

        }
    }

    /**
     * 返回
     */
    public void goBack() {
        if ("android".equalsIgnoreCase(platformName)) {
            appiumDriver.navigate().back();
        } else {
            for (String str : iosBack) {
                WebElement element = findElementById(str);
                if (element != null) {
                    element.click();
                    logger.info("点击元素:{}", element);
                    break;
                }
            }
        }
    }


    public static String getClassName(String name) {
        String[] s = name.split("\\.");
        return s[s.length - 1];
    }

//    public void startMonitoring() {
//        Thread t = new Thread(getPerformanceData);
//        t.start();
//        if ("android".equalsIgnoreCase(platformName)) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    CommandUtil.getAndroidLog();
//                }
//            }).start();
//        }
//    }
//    public void stopMonitoring(String sheetName){
//        getPerformanceData.shutDown(sheetName);
//    }

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
