package com.iqianjin.appperformance.parallel.pageCase;

import com.iqianjin.appperformance.parallel.ParllelAndroidDriver;
import com.iqianjin.appperformance.config.ElementTypeEnum;
import com.iqianjin.appperformance.parallel.ParllelIosDriver;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Slf4j
public class ParBase {
    private static ThreadLocal<String> local = new ThreadLocal<>();

    public String homeTab = "首页tab";
    public String productTab = "产品tab";
    public String findTab = "发现tab";
    public String myTab = "我的tab";
    public String tv_start = "开机广告";
    //异常的原生弹框，比如开机动画，都放在dialogs list
    List<String> androidDialogs = Stream.of("xpath://*[@text='确认']",
            "id:createAccountBack",
            "xpath://*[@text='取消']",
            "id:dialogALeftButton",
            "xpath://*[@text='同意授权']")
            .collect(Collectors.toList());
    List<String> iosDialogs = Stream.of("pre:type == 'XCUIElementTypeButton' AND name == '确定'",
            "pre:type == 'XCUIElementTypeButton' AND name == '取消'",
            "pre:type == 'XCUIElementTypeButton' AND name == '同意授权'")
            .collect(Collectors.toList());
    //webView 元素
    List<String> dialogsWebview = Stream.of(".dialog-invest-close", ".close")
            .collect(Collectors.toList());

    protected List<String> iosBack = Stream.of("public back",
            "ticket back n",
            "public back white").
            collect(Collectors.toList());
//
//    public AppiumDriver appiumDriverAndroid = ParllelAndroidDriver.getInstance().appiumDriverAndroid;
//    public AppiumDriver appiumDriverios = ParllelIosDriver.getInstance().appiumDriverios;

    public void parClick(String text) {
        WebElement element = null;
        element = find(text, 2);
        try {
            if (null != element) {
                log.info("parClick点击元素:{}", text);
                element.click();
            }
        }catch (Exception e){
            log.error("parClick点击元素报错:{}",text);
        }

    }


    /**
     * 另一种点击操作，传入element
     *
     * @param element
     */
    public void parClickElement(WebElement element) {
        try {
            if (null != element) {
                log.info("parClickElement点击元素:{}", element);
                element.click();
            }else {
                log.error("parClickElement点击元素为空:{}", element);
            }
        } catch (Exception e) {
            log.error("clickElement报错了");
            //处理异常webview
            if (parIsWebview()) {
                parClickWebview();
                element.click();
            }
        }
    }

    public void parSendKeys(String locatorName, String content) {
        WebElement webElement = find(locatorName, 2);
        if (null != webElement) {
            webElement.clear();
            webElement.sendKeys(content);
        } else {
            log.error("parSendKeys输入文本，找不到定位元素:{}", locatorName);
        }
    }

    /**
     * 返回
     */
    public void parGoBack() {
        if (getPlatName().equalsIgnoreCase("android")) {
            ParllelAndroidDriver.getInstance().appiumDriverAndroid.navigate().back();
        } else {
            for (String str : iosBack) {
                WebElement element = findElementById(str);
                if (element != null) {
                    element.click();
                    log.info("点击返回元素:{}", element);
                    break;
                }
            }
        }
    }

    public WebElement findElementById(String id) {
        try {
            if ("android".equalsIgnoreCase(getPlatName())) {
                return ParllelAndroidDriver.getInstance().appiumDriverAndroid.findElement(By.id(id));
            } else {
                return ParllelIosDriver.getInstance().appiumDriverios.findElement(By.id(id));
            }
        } catch (Exception e) {
            log.error("通过id查找，未找到元素:{}", id);
        }
        return null;
    }

    public WebElement findElementByXPath(String xpath) {
        try {
            if ("android".equalsIgnoreCase(getPlatName())) {
                return ParllelAndroidDriver.getInstance().appiumDriverAndroid.findElement(By.xpath(xpath));
            } else {
                return ParllelIosDriver.getInstance().appiumDriverios.findElement(By.xpath(xpath));
            }
        } catch (Exception e) {
            log.error("通过xpath查找，未找到元素:{}", xpath);
        }
        return null;
    }

    public WebElement find(String locatorName, long timeOutInSeconds) {
        WebElement element = null;
        List<String> tempList = getElement(locatorName);
        log.info("定位元素：{},定位方式：{}", locatorName, tempList.toArray());
        if (getPlatName().equalsIgnoreCase("android")) {
            WebDriverWait waitAndroid = new WebDriverWait(ParllelAndroidDriver.getInstance().appiumDriverAndroid, timeOutInSeconds, 500);
            for (String temp : tempList) {
                String locatorStrategy = temp.split(":")[0];
                String locatorValue = temp.split(":")[1];
                try {
                    if ("id".equalsIgnoreCase(locatorStrategy)) {
                        element = waitAndroid.until(ExpectedConditions.presenceOfElementLocated(By.id(locatorValue)));
                        if (null != element) {
                            return element;
                        }
                    } else if ("xpath".equalsIgnoreCase(locatorStrategy)) {
                        element = waitAndroid.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locatorValue)));
                        if (null != element) {
                            return element;
                        }
                    }
                } catch (Exception e) {
                    log.error("android定位元素，无此元素:{}", temp);
//                    e.printStackTrace();
                }
            }
        } else {
            WebDriverWait waitIos = new WebDriverWait(ParllelIosDriver.getInstance().appiumDriverios, timeOutInSeconds, 500);
            for (String temp : tempList) {
                String locatorStrategy = temp.split(":")[0];
                String locatorValue = temp.split(":")[1];
                try {
                    if ("id".equalsIgnoreCase(locatorStrategy)) {
                        element = waitIos.until(ExpectedConditions.presenceOfElementLocated(By.id(locatorValue)));
                        if (null != element) {
                            return element;
                        }
                    } else if ("pre".equalsIgnoreCase(locatorStrategy)) {
                        element = findIosByPredicate(locatorValue);
                        if (null != element) {
                            return element;
                        }
                    }
                } catch (Exception e) {
                    log.error("ios定位元素，无此元素:{}", temp);
//                    e.printStackTrace();
                }
            }
        }
        return element;
    }

    /**
     * 简单查找，不做异常弹框等处理
     *
     * @param locatorName
     * @return
     */
    public WebElement parSimpleFind(String locatorName) {
        List<String> tempList = getElement(locatorName);
        WebElement element = null;
        for (String temp : tempList) {
            String locatorStrategy = temp.split(":")[0];
            String locatorValue = temp.split(":")[1];
            try {
                if ("id".equalsIgnoreCase(locatorStrategy)) {
                    element = findElementById(locatorValue);
                    if (null != element) {
                        return element;
                    }
                } else if ("xpath".equalsIgnoreCase(locatorStrategy)) {
                    element = findElementByXPath(locatorValue);
                    if (null != element) {
                        return element;
                    }
                } else if ("pre".equalsIgnoreCase(locatorStrategy)) {
                    element = findIosByPredicate(locatorValue);
                    if (null != element) {
                        return element;
                    }
                }
            } catch (Exception e) {
                log.error("simpleFind定位元素出错:{}", temp);
            }
        }
        return element;
    }

    public WebElement findIosByPredicate(String predicate) {
        try {
            return ParllelIosDriver.getInstance().appiumDriverios.findElement(MobileBy.iOSNsPredicateString(predicate));
        } catch (Exception e) {
            log.error("predicate，未找到元素:{}", predicate);
        }
        return null;
    }

    public boolean isInPageSource(String value) {
        if ("android".equalsIgnoreCase(getPlatName())) {
            return ParllelAndroidDriver.getInstance().appiumDriverAndroid.getPageSource().contains(value);
        } else {
            return ParllelIosDriver.getInstance().appiumDriverios.getPageSource().contains(value);
        }
    }

    /**
     * 判断是否是webview
     *
     * @return
     */
    public boolean parIsWebview() {
        log.info("进入判断是否有webview页面");
        sleep(2);
        if ("android".equalsIgnoreCase(getPlatName())) {
            Set<String> ContextHandles = ParllelAndroidDriver.getInstance().appiumDriverAndroid.getContextHandles();
            log.info("当前contextHandle数量:{},内容：{}", ContextHandles.size(), ContextHandles);
            if (ContextHandles.size() == 1) {
                return false;
            } else {
                ContextHandles.forEach((handle) -> {
                    if (handle.contains("WEBVIEW")) {
                        log.info("切换到webView:{}", handle);
                        try {
                            ParllelAndroidDriver.getInstance().appiumDriverAndroid.context(handle);
                        } catch (Exception e) {
                            log.error("当前handle是:{}", handle);
                            e.printStackTrace();
                        }
                    }
                });
                return true;
            }
        } else {
            Set<String> ContextHandles = ParllelIosDriver.getInstance().appiumDriverios.getContextHandles();
            log.info("当前contextHandle数量:{},内容：{}", ContextHandles.size(), ContextHandles);
            if (ContextHandles.size() == 1) {
                return false;
            } else {
                ContextHandles.forEach((handle) -> {
                    if (handle.contains("WEBVIEW")) {
                        log.info("切换到webView:{}", handle);
                        try {
                            ParllelIosDriver.getInstance().appiumDriverios.context(handle);
                        } catch (Exception e) {
                            log.error("当前handle是:{}", handle);
                            e.printStackTrace();
                        }
                    }
                });
                return true;
            }
        }

    }

    /**
     * 点击webview元素
     */
    public void parClickWebview() {
        if ("android".equalsIgnoreCase(getPlatName())) {
            parGoBack();
        } else {
            for (String temp : dialogsWebview) {
                try {
                    WebElement element = ParllelIosDriver.getInstance().appiumDriverios.findElementByCssSelector(temp);
                    if (element != null) {
                        element.click();
                    }
                    log.info("点击webview元素:{}", temp);
                } catch (Exception e) {
                    log.error("找不到webview元素:{}", temp, e);
                }
            }
            log.info("切换到native");
            ParllelIosDriver.getInstance().appiumDriverios.context("NATIVE_APP");
        }
    }

    /**
     * 上滑和下滑，根据系数滑动
     *
     * @param to   滑动起始位系数
     * @param from 滑动终点系数
     */
    public void parSwipeUpOrDown(double to, double from) {
        if ("android".equalsIgnoreCase(getPlatName())) {
            swipeUpOrDown(to, from, ParllelAndroidDriver.getInstance().appiumDriverAndroid);
        } else {
            swipeUpOrDown(to, from, ParllelIosDriver.getInstance().appiumDriverios);
        }
    }

    public void swipeUpOrDown(double to, double from, AppiumDriver appiumDriver) {
        int height = appiumDriver.manage().window().getSize().height;
        int width = appiumDriver.manage().window().getSize().width;
        TouchAction action = new TouchAction(appiumDriver);
        try {
            action.longPress(PointOption.point(width / 2, (int) (height * to)))
                    .moveTo(PointOption.point(width / 2, (int) (height * from)))
                    .release().perform();
        } catch (Exception e) {
            log.error("向上or向下滑动失败");
            e.printStackTrace();
        }

    }

    /**
     * 左滑，右滑
     */
    public void parSwipeLeftOrRight(double to, double from) {
        if ("android".equalsIgnoreCase(getPlatName())) {
            swipeLeftOrRight(to, from, ParllelAndroidDriver.getInstance().appiumDriverAndroid);
        } else {
            swipeLeftOrRight(to, from, ParllelIosDriver.getInstance().appiumDriverios);
        }
    }

    public void swipeLeftOrRight(double to, double from, AppiumDriver appiumDriver) {
        log.info("开始左右滑动");

        int width = appiumDriver.manage().window().getSize().width;
        int height = appiumDriver.manage().window().getSize().height;
        TouchAction action = new TouchAction(appiumDriver);
        try {
            action.longPress(PointOption.point((int) (width * to), height / 2)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
                    .moveTo(PointOption.point((int) (width * from), height / 2)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
                    .release().perform();
        } catch (Exception e) {
            log.error("向左or向右滑动失败");
            e.printStackTrace();
        }

    }

    /**
     * 上下滑动指定次数
     *
     * @param to
     * @param from
     * @param
     */
    public void parSwipeToNum(double to, double from, int num) {
        if ("android".equalsIgnoreCase(getPlatName())) {
            swipeToNum(to, from, num, ParllelAndroidDriver.getInstance().appiumDriverAndroid);
        } else {
            swipeToNum(to, from, num, ParllelIosDriver.getInstance().appiumDriverios);
        }
    }

    public void swipeToNum(double to, double from, int num, AppiumDriver appiumDriver) {
        //todo 优化：ios执行比较慢
        String pageSource = appiumDriver.getPageSource();
        if (pageSource.contains("去别处看看吧") || pageSource.contains("暂无预约记录") ||
                pageSource.contains("暂无流水记录") || pageSource.contains("暂无资金流水")) {
            return;
        }

        for (int i = 1; i < num; i++) {
            parSwipeUpOrDown(to, from);
        }
    }

    /**
     * 上下滑动到指定元素
     *
     * @param to
     * @param from
     * @param locatorName
     */
    //todo: 待优化，有的页面能找到元素，但是不在当前页无法点击会出问题
    public WebElement parSwipUpWaitElement(double to, double from, String locatorName) {
        WebElement element;
        log.info("上下滑动到指定元素:{}", locatorName);
        while (true) {
            parSwipeUpOrDown(to, from);
            element = find(locatorName, 1);
            if (element != null) {
                return element;
            }
        }

    }

    /**
     * 长按某一坐标
     *
     * @param x
     * @param y
     */
    public void parLongPreePostion(int x, int y) {
        if ("android".equalsIgnoreCase(getPlatName())) {
            longPreePostion(x, y, ParllelAndroidDriver.getInstance().appiumDriverAndroid);
        } else {
            longPreePostion(x, y, ParllelIosDriver.getInstance().appiumDriverios);
        }
    }

    public void longPreePostion(int x, int y, AppiumDriver appiumDriver) {
        log.info("长按坐标");
        TouchAction action = new TouchAction(appiumDriver);
        action.longPress(LongPressOptions.longPressOptions()
                .withPosition(PointOption.point(x, y))
                .withDuration(Duration.ofSeconds(2)));
        action.release().perform();
    }

    public List<String> getElement(String ele) {
        log.info("当前平台是：{}，获取元素：{}", getPlatName(), ele);
        if (getPlatName().equalsIgnoreCase("android")) {
            return ElementTypeEnum.mapAndroid.get(ele);
        } else {
            return ElementTypeEnum.mapIos.get(ele);
        }
    }

    public void setPlatName(String platName) {
        local.set(platName);
    }

    public String getPlatName() {
        return local.get();
    }

    public static ParBase getInstance() {
        return ParBase.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        public static final ParBase INSTANCE = new ParBase();
    }

    public static void sleep(long sec) {
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
