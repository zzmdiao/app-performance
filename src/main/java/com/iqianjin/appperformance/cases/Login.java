package com.iqianjin.appperformance.cases;

import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

import static com.iqianjin.appperformance.util.CommandUtil.exec2;
import static com.iqianjin.appperformance.util.CommandUtil.sleep;

public class Login extends BaseCase {

    private static Logger logger = LoggerFactory.getLogger(Login.class);

    private String otherLogin = "其他登录";
    public String userName = "用户名";
    public String passwdStatus = "密码";
    public String loginSubmit = "登录按钮";
    public String gestureForget = "忘记手势密码";
    public String loginAgain = "重新登录";
    public String cancle = "取消升级";
    public String setUP = "设置按钮";
    public String loginOut = "退出登录";
    public String confirmButton = "确认按钮";

    static Login login;

    public static Login getInstance() {
        if (login == null) {
            login = new Login();
        }
        return login;
    }

    /**
     * 登录
     *
     * @param user
     * @param pass
     */
    public void login(String user, String pass) {
        if (appiumDriver.getPageSource().contains("跳过")) {
            click(tv_start);
        }
        sleep(3);
        logger.info("开始进入注册/登录");
        if (isLogin()) {
            if (appiumDriver.getPageSource().contains(user)){
                logger.info("当前用户是同一个，尝试解锁");
                setOneUnlock();
                if (appiumDriver.getPageSource().contains("密码输入错误")){
                    logOut(user);
                    loginAgain(user, pass);
                }
            }else {
                logOut(user);
                loginAgain(user, pass);
            }
        } else {
            click(otherLogin);
            loginAgain(user, pass);
        }

    }

    /**
     * 判断是否已登录
     */
    public boolean isLogin() {
        boolean flag = false;

        if (appiumDriver.getPageSource().contains("发现新版本")) {
            click(cancle);
        }

        if (appiumDriver.getPageSource().contains("我的")) {
            click(myTab);
            if (appiumDriver.getPageSource().contains("其他登录")) {
                logger.info("判断当前是否是登录状态:{}", flag);
                return flag;
            }
        }

        String pageSource = appiumDriver.getPageSource();
        if (pageSource.contains("忘记手势密码") || pageSource.contains("请绘制解锁图案")) {
            flag = true;
        }
        logger.info("判断当前是否是登录状态:{}", flag);
        return flag;
    }

    public void loginAgain(String user, String pass) {
        for (int i = 0; i < 3; i++) {
            if (loginFail()) {
                sendKeys(userName, user);
                sendKeys(passwdStatus, pass);
                click(loginSubmit);
            } else {
                logger.info("登录完成");
                break;
            }
        }

        setUnlock();
        click(myTab);
    }

    /**
     * 退出登录
     *
     * @param
     */
    public void logOut(String user) {
        String pagesource = appiumDriver.getPageSource();
        if (pagesource.contains("忘记手势密码")) {
            logger.info("当前页面包含《忘记手势密码》元素");
            click(gestureForget);
            click(loginAgain);
            if ("android".equalsIgnoreCase(platformName)) {
                click(otherLogin);
            }
        }
        if (pagesource.contains("请绘制解锁图案")) {
            setUnlock();
            click(myTab);
            if (appiumDriver.getPageSource().contains(user)){
                return;
            }
            click(setUP);
            swipeToNum(0.5, 0.1, 1);
            click(loginOut);
            click(confirmButton);
            click(myTab);
            click(otherLogin);
        }
    }

    public void setUnlock() {
        if (appiumDriver.getPageSource().contains("设置")) {
            return;
        }
        if ("android".equalsIgnoreCase(platformName)) {
            getViewUnlockAndroid("gestureSetLockView");
            getViewUnlockAndroid("gestureSetLockView");
        } else {
            getViewUnlockIos();
            getViewUnlockIos();
        }
    }
    public void setOneUnlock() {
        if (appiumDriver.getPageSource().contains("设置")) {
            return;
        }
        if ("android".equalsIgnoreCase(platformName)) {
            getViewUnlockAndroid("gestureLockPatternView");
        } else {
            getViewUnlockIos();
        }
    }

    //根据view插件设置九宫格解锁
    public void getViewUnlockAndroid(String ElementView) {
        logger.info("设置android解锁图案L");
        WebElement webElement = appiumDriver.findElement(By.id(ElementView));
        //获取控件起始坐标的x、y
        int bX = webElement.getLocation().getX();
        int bY = webElement.getLocation().getY();
        //获取控件的高度、宽度
        int width = webElement.getSize().getWidth();
        int height = webElement.getSize().getHeight();
        //宽度、高度之间的间隔
        int x = bX + width / 6;
        int y = bY + height / 6;
        int xStep = width / 3;
        int yStep = height / 3;
        int xxStep = 2 * xStep;
        int yyStep = 2 * yStep;
        TouchAction ta = new TouchAction(appiumDriver);
        Duration duration = Duration.ofMillis(500);
        ta.longPress(PointOption.point(bX, bY)).waitAction(WaitOptions.waitOptions(duration))
                .moveTo(PointOption.point(x, y)).waitAction(WaitOptions.waitOptions(duration))
                .moveTo(PointOption.point(x, y + yStep)).waitAction(WaitOptions.waitOptions(duration))
                .moveTo(PointOption.point(x, y + yyStep)).waitAction(WaitOptions.waitOptions(duration))
                .moveTo(PointOption.point(x + xStep, y + yyStep)).waitAction(WaitOptions.waitOptions(duration))
                .moveTo(PointOption.point(x + xxStep, y + yyStep)).waitAction(WaitOptions.waitOptions(duration))
                .release().perform();
    }

    public void getViewUnlockIos() {
        logger.info("设置ios解锁图案L");
        List<WebElement> elements = appiumDriver.findElements(By.xpath("//XCUIElementTypeButton"));
        Point firstElement = elements.get(0).getLocation();
        Point thridElement = elements.get(3).getLocation();
        Point sixElement = elements.get(6).getLocation();
        Point sevenElement = elements.get(7).getLocation();
        Point eightElement = elements.get(8).getLocation();
        //获取控件起始坐标的x、y
        int bX = firstElement.getX();
        int bY = firstElement.getY();
        // 第二个点的坐标
        int twoX = thridElement.getX();
        int twoY = thridElement.getY();
        // 第三个点的坐标
        int threeX = sixElement.getX();
        int threeY = sixElement.getY();
        // 第四个点的坐标
        int fourX = sevenElement.getX();
        int fourY = sevenElement.getY();
        // 第五个点的坐标
        int fiveX = eightElement.getX();
        int fiveY = eightElement.getY();
        TouchAction ta = new TouchAction(appiumDriver);
        Duration duration = Duration.ofMillis(500);
        ta.longPress(PointOption.point(bX, bY)).waitAction(WaitOptions.waitOptions(duration))
                .moveTo(PointOption.point(twoX, twoY)).waitAction(WaitOptions.waitOptions(duration))
                .moveTo(PointOption.point(threeX, threeY)).waitAction(WaitOptions.waitOptions(duration))
                .moveTo(PointOption.point(fourX, fourY)).waitAction(WaitOptions.waitOptions(duration))
                .moveTo(PointOption.point(fiveX, fiveY)).waitAction(WaitOptions.waitOptions(duration))
                .release().perform();

    }

    public Boolean loginFail() {
        sleep(2);
        String pageSource = appiumDriver.getPageSource();

        if (pageSource.contains("设置") || pageSource.contains("请绘制解锁图案")) {
            return false;
        } else {
            return true;
        }
    }

    //查看手机的输入法 adb shell ime list -s
    // Latin输入法
    private final static String LIST_LATIN = "adb shell ime set com.android.inputmethod.latin/.LatinIME";
    // Appium输入法
    private final static String LIST_APPIUM = "adb shell ime set io.appium.android.ime/.UnicodeIME";
}

