package com.iqianjin.appperformance.cases;

import com.iqianjin.appperformance.base.DriverManger;
import com.iqianjin.appperformance.config.ElementTypeEnum;
import com.iqianjin.appperformance.util.CommandUtil;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.iqianjin.appperformance.util.CommandUtil.sleep;

@Slf4j
public class BaseCase {
    public AppiumDriver appiumDriver = DriverManger.getInstance().appiumDriver;

    public String platformName = DriverManger.getInstance().getPlatform();

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
    //ios左上角返回元素
    protected List<String> iosBack = Stream.of("public back",
            "ticket back n",
            "public back white").
            collect(Collectors.toList());

    /**
     * 查找元素并定位，返回WebElement
     * 等待timeOutInSeconds秒，每500毫秒查找一次，直到元素by出现
     * 此方法会判定是否有异常弹框以及webview元素，执行效率较慢
     */
    public WebElement findByWait(String locatorName, long timeOutInSeconds) {
        WebElement element = null;
        List<String> tempList = getElement(locatorName);
        element = find(tempList, timeOutInSeconds);
        //处理异常弹框
        if (null == element && "android".equalsIgnoreCase(platformName)) {
            handelExceptionBounces(androidDialogs);
        } else if (null == element && "ios".equalsIgnoreCase(platformName)) {
            handelExceptionBounces(iosDialogs);
        }
        //处理异常webview
        if (null == element && isWebview()) {
            if ("android".equalsIgnoreCase(platformName)){
                goBack();
            }else {
                clickWebview();
            }
        }
        if (null == element) {
            element = find(tempList, timeOutInSeconds);
        }

        return element;
    }

    /**
     * 查找元素，dom中存在即可，找不到不做任何处理
     *
     * @param tempList
     * @param timeOutInSeconds
     * @return
     */
    public WebElement find(List<String> tempList, long timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(appiumDriver, timeOutInSeconds, 500);
        WebElement element = null;
        for (String temp : tempList) {
            log.info("find定位元素，当前元素是：{}", temp);
            String locatorStrategy = temp.split(":")[0];
            String locatorValue = temp.split(":")[1];
            try {
                if ("id".equalsIgnoreCase(locatorStrategy)) {
                    element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(locatorValue)));
                    if (null != element) {
                        return element;
                    }
                } else if ("xpath".equalsIgnoreCase(locatorStrategy)) {
                    element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locatorValue)));
                    if (null != element) {
                        return element;
                    }
                } else if ("pre".equalsIgnoreCase(locatorStrategy)) {
                    // 针对ios，优化定位效率
                    element = findIosByPredicate(locatorValue);
                    if (null != element) {
                        return element;
                    }
                }
            } catch (Exception e) {
                log.error("find定位元素，无此元素:{}", temp);
            }
        }
        return element;
    }

    /**
     * 查找元素，dom中不仅存在，可见而且还有高度和宽度大于0
     *
     * @param locatorName
     * @param timeOutInSeconds
     * @return
     */
    public WebElement findByWait2(String locatorName, long timeOutInSeconds) {
        WebElement element = null;
        List<String> tempList = getElement(locatorName);
        element = findVisibility(tempList, timeOutInSeconds);
        return element;
    }

    public WebElement findVisibility(List<String> tempList, long timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(appiumDriver, timeOutInSeconds, 500);
        WebElement element = null;
        for (String temp : tempList) {
            log.info("findVisibility定位元素，当前元素是：{}", temp);
            String locatorStrategy = temp.split(":")[0];
            String locatorValue = temp.split(":")[1];
            try {
                if ("id".equalsIgnoreCase(locatorStrategy)) {
                    element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorValue)));
                    if (null != element) {
                        return element;
                    }
                } else if ("xpath".equalsIgnoreCase(locatorStrategy)){
                    element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorValue)));
                    if (null != element) {
                        return element;
                    }
                }else if ("pre".equalsIgnoreCase(locatorStrategy)) {
                    element = findIosByPredicate(locatorValue);
                    if (null != element) {
                        return element;
                    }
                }
            } catch (Exception e) {
                log.error("findVisibility定位元素，无此元素:{}", temp);
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
    public WebElement simpleFind(String locatorName) {
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

    public WebElement findElementByXPath(String xpath) {
        try {
            return appiumDriver.findElement(By.xpath(xpath));
        } catch (Exception e) {
            log.error("通过xpath查找，未找到元素:{}", xpath);
        }
        return null;
    }

    public WebElement findElementById(String id) {
        try {
            return appiumDriver.findElement(By.id(id));
        } catch (Exception e) {
            log.error("通过id查找，未找到元素:{}", id);
        }
        return null;
    }

    public WebElement findIosByPredicate(String predicate) {
        try {
            return appiumDriver.findElement(MobileBy.iOSNsPredicateString(predicate));
        } catch (Exception e) {
            log.error("predicate，未找到元素:{}", predicate);
        }
        return null;
    }

    public List<WebElement> findElements(String locatorName) {
        List<String> tempList = getElement(locatorName);
        List<WebElement> element = null;
        for (String temp : tempList) {
            appiumDriver.gets
            log.info("定位元素，当前元素是：{}", temp);
            String locatorStrategy = temp.split(":")[0];
            String locatorValue = temp.split(":")[1];
            try {
                if ("id".equalsIgnoreCase(locatorStrategy)) {
                    element = appiumDriver.findElementsById(locatorValue);
                    if (null != element) {
                        return element;
                    }
                } else {
                    element = appiumDriver.findElementsByXPath(locatorValue);
                    if (null != element) {
                        return element;
                    }
                }
            } catch (Exception e) {
                log.error("定位元素，无此元素:{}", temp);
            }
        }
        return element;
    }

    /**
     * 点击操作
     *
     * @param text
     * @return
     */
    public boolean click(String text) {
        WebElement element = null;
        if ("android".equalsIgnoreCase(platformName)) {
            sleep(1);
            element = findByWait(text, 2);
            if (null != element) {
                log.info("android点击元素:{}", text);
                element.click();
                return true;
            }
        } else {
            element = simpleFind(text);
            if (null == element && isWebview()) {
                if ("android".equalsIgnoreCase(platformName)){
                    goBack();
                }else {
                    clickWebview();
                }
                element = simpleFind(text);
            }
            if (null == element) {
                handelExceptionBounces(iosDialogs);
                element = simpleFind(text);
            }
            if (null != element) {
                log.info("ios点击元素:{}", text);
                element.click();
                return true;
            }
        }
        return false;
    }

    /**
     * 另一种点击操作，传入element
     *
     * @param element
     */
    public void clickElement(WebElement element) {
//        WebDriverWait wait = new WebDriverWait(appiumDriver, 5);
        try {
//            wait.until(elementFoundAndClicked(element));
            sleep(1);
            element.click();
        } catch (Exception e) {
            log.error("clickElement报错了");
            //处理异常webview
            if (isWebview()) {
                if ("android".equalsIgnoreCase(platformName)){
                    goBack();
                }else {
                    clickWebview();
                }
                element.click();
            }
        }

    }

    /**
     * 在元素locatorName中输入内容content
     *
     * @param locatorName
     * @param content
     */
    public void sendKeys(String locatorName, String content) {
        WebElement webElement = findByWait(locatorName, 2);
        if (null != webElement) {
            webElement.clear();
            webElement.sendKeys(content);
        } else {
            log.error("输入文本，找不到定位元素:{}", locatorName);
        }
    }

    private ExpectedCondition<Boolean> elementFoundAndClicked(WebElement element) {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                element.click();
                return true;
            }
        };
    }


    /**
     * 处理异常弹框，只针对原生弹框
     *
     * @param list
     */
    public void handelExceptionBounces(List<String> list) {
        for (String temp : list) {
            String locatorStrategy = temp.split(":")[0];
            String locatorValueOther = temp.split(":")[1];
            if ("xpath".equalsIgnoreCase(locatorStrategy)) {
                WebElement tempElement = findElementByXPath(locatorValueOther);
                if (null != tempElement) {
                    log.info("开始点击xpath元素locatorValueOther:{}", locatorValueOther);
                    clickElement(tempElement);
                    break;
                }
            } else if ("id".equalsIgnoreCase(locatorStrategy)) {
                WebElement tempElement = findElementById(locatorValueOther);
                if (null != tempElement) {
                    log.info("开始点击id元素locatorValueOther:{}", locatorValueOther);
                    clickElement(tempElement);
                    break;
                }
            } else if ("pre".equalsIgnoreCase(locatorStrategy)) {
                WebElement tempElement = findIosByPredicate(locatorValueOther);
                if (null != tempElement) {
                    log.info("开始点击pre元素locatorValueOther:{}", locatorValueOther);
                    clickElement(tempElement);
                    break;
                }
            }
        }
    }

    /**
     * 判断是否是webview
     *
     * @return
     */
    public boolean isWebview() {
        log.info("进入判断是否有webview页面");
        CommandUtil.sleep(2);
        Set<String> ContextHandles = appiumDriver.getContextHandles();
        log.info("当前contextHandle数量:{},内容：{}",ContextHandles.size(),ContextHandles);
        if (ContextHandles.size() == 1) {
            return false;
        } else {
            ContextHandles.forEach((handle) -> {
                if (handle.contains("WEBVIEW")) {
                    log.info("切换到webView:{}", handle);
                    try {
                        appiumDriver.context(handle);
                    } catch (Exception e) {
                        log.error("当前handle是:{}", handle);
                        e.printStackTrace();
                    }
                }
            });
            return true;
        }
    }

    /**
     * 点击webview元素
     */
    public void clickWebview() {
        for (String temp : dialogsWebview) {
            try {
                WebElement element = appiumDriver.findElementByCssSelector(temp);
                if (element != null) {
                    element.click();
                }
                log.info("点击webview元素:{}", temp);
            } catch (Exception e) {
                log.error("找不到webview元素:{}", temp,e);
            }
        }
        log.info("切换到native");
        appiumDriver.context("NATIVE_APP");
    }

    /**
     * 上下滑动到指定元素
     *
     * @param to
     * @param from
     * @param locatorName
     */
    public WebElement swipUpWaitElement(double to, double from, String locatorName) {
        WebElement element;
        log.info("滑动到指定元素:{}", locatorName);
        while (true) {
            element = simpleFind(locatorName);
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
            log.error("向上or向下滑动失败");
            e.printStackTrace();
        }

    }

    /**
     * 左滑，右滑
     */
    public void swipeLeftOrRight(double to, double from) {
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
     * @param num
     */
    public void swipeToNum(double to, double from, int num) {
        //todo 优化：ios执行比较慢
        String pageSource = appiumDriver.getPageSource();
        if (pageSource.contains("去别处看看吧") || pageSource.contains("暂无预约记录") ||
                pageSource.contains("暂无流水记录") || pageSource.contains("暂无资金流水")) {
            return;
        }

        for (int i = 1; i < num; i++) {
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
     * 滑动到底部 没有提示文案的
     *
     * @param by
     * @param to
     * @param from
     */
    public void swipeToBottomSuper(String by, double to, double from) {
        sleep(1);
        String pageSource = appiumDriver.getPageSource();
        if (pageSource.contains("去别处看看吧") || pageSource.contains("去别处看看吧~") || pageSource.contains("暂无内容")
                || pageSource.contains("暂无预约记录") || pageSource.contains("暂无流水记录") || pageSource.contains("暂无资金流水")) {
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
                log.info("努力前进中or上拉加载更多");
                swipeUpOrDown(to, from);
            }
            if ("android".equalsIgnoreCase(platformName) && list.size() > 0) {
                pointFrom = list.get(list.size() - 1).getAttribute("text");
            } else if (list.size() > 0) {
                pointFrom = list.get(list.size() - 3).getAttribute("name");
            }
            log.info("pointTo值是:{}pointFrom值是:{}", pointTo, pointFrom);
            if (pointTo != null && pointFrom != null && pointTo.equals(pointFrom)) {
                log.info("页面再也划不动了");
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
                    log.info("点击返回元素:{}", element);
                    break;
                }
            }
        }
    }

    /**
     * 双击
     *
     * @param element
     */
    public void doubleClick(String element) {
        WebElement webElement = simpleFind(element);
        TouchActions action = new TouchActions(appiumDriver);
        action.doubleTap(webElement);
        action.perform();
    }

    /**
     * 长按某一元素
     *
     * @param element
     */
    public void longPreeElement(String element) {
        TouchAction action = new TouchAction(appiumDriver);
        action.longPress(LongPressOptions.longPressOptions()
                .withElement(ElementOption.element(simpleFind(element)))
                .withDuration(Duration.ofSeconds(1)));
        action.release().perform();
    }

    /**
     * 长按某一坐标
     *
     * @param x
     * @param y
     */
    public void longPreePostion(int x, int y) {
        log.info("长按坐标");
        TouchAction action = new TouchAction(appiumDriver);
        action.longPress(LongPressOptions.longPressOptions()
                .withPosition(PointOption.point(x, y))
                .withDuration(Duration.ofSeconds(2)));
        action.release().perform();
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

    public List<String> getElement(String ele) {

        if (platformName.equalsIgnoreCase("android") && ele != null) {
            return ElementTypeEnum.mapAndroid.get(ele);
        } else if (platformName.equalsIgnoreCase("ios") && ele != null) {
            return ElementTypeEnum.mapIos.get(ele);
        } else {
            log.error("出现这个就炸了！！！");
            return null;
        }
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
//            log.info("输入内容是temp:{}", temp);
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
//    public void context_to_webview(String element) {
//        String temp = ElementTypeEnum.mapAndroid.get(element);
//        log.info("webview元素:{}", temp);
//        sleep(3);
//        Set<String> ContextHandles = appiumDriver.getContextHandles();
//        log.info("当前1 ContextHandles{}", ContextHandles);
//        sleep(1);
//        log.info("当前2 ContextHandles{}", ContextHandles);
//        if (ContextHandles.size() == 1) {
//            log.info("该web页未开启debug状态");
//        } else {
//            ContextHandles.forEach((handle) -> {
//                if (handle.contains("WEBVIEW")) {
//                    log.info("切换到webView:{}", handle);
//                    appiumDriver.context(handle);
//                    try {
//                        appiumDriver.findElementByCssSelector(temp).click();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//            log.info("切换到native");
//            appiumDriver.context("NATIVE_APP");
//        }
//    }

    public static BaseCase getInstance() {
        return BaseCase.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final BaseCase INSTANCE = new BaseCase();
    }
}
