package com.iqianjin.appperformance.cases;

import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

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

    /**
     * 判断是否已登录
     */
    public boolean isLogin() {
        sleep(1);
        logger.info("判断当前是否是登录状态");

        if (appiumDriver.getPageSource().contains("发现新版本")) {
            click(cancle);
        }

        if (appiumDriver.getPageSource().contains("我的")) {
            click(myTab);
            sleep(1);
            if (appiumDriver.getPageSource().contains("其他登录")) {
                return false;
            }
        }

        if (appiumDriver.getPageSource().contains("忘记手势密码") || appiumDriver.getPageSource().contains("请绘制解锁图案")) {
            return true;
        }

        return true;
    }

    /**
     * 登录
     *
     * @param user
     * @param pass
     */
    public void login(String user, String pass) {
        if (appiumDriver.getPageSource().contains("允许")) {
            appiumDriver.findElementById("button1").click();
        }
        if (appiumDriver.getPageSource().contains("允许出现在其他应用上")) {
            appiumDriver.findElementById("switch_widget").click();
            goBack();
        }
        if (appiumDriver.getPageSource().contains("跳过")) {
            click(tv_start);
        }

        logger.info("开始进入注册/登录");
        if (isLogin()) {
            //尝试解锁
            logger.info("当前是否登录:{}", isLogin());
            if (platformName.equalsIgnoreCase("android")) {
                if (appiumDriver.getPageSource().contains("设置")) {
                    return;
                }
                if (appiumDriver.getPageSource().contains("gestureLockPatternView")) {
                    getViewUnlockAndroid("gestureLockPatternView");
                } else {
                    getViewUnlockAndroid("gestureSetLockView");
                    getViewUnlockAndroid("gestureSetLockView");
                }
            } else {
                if (appiumDriver.getPageSource().contains("设置")) {
                    return;
                }
                getViewUnlockIos();
            }
            //解锁失败，去登录
            if (appiumDriver.getPageSource().contains("密码输入错误")) {
                click(gestureForget);
                click(loginAgain);
                if ("android".equalsIgnoreCase(platformName)) {
                    click(otherLogin);
                    loginAgain(user, pass);
                } else {
                    loginAgain(user, pass);
                }
            }
        }
        //登录
        if (appiumDriver.getPageSource().contains("注册/登录爱钱进")) {
            click(otherLogin);
            loginAgain(user, pass);
        }

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

        if ("android".equalsIgnoreCase(platformName)) {
            getViewUnlockAndroid("gestureSetLockView");
            getViewUnlockAndroid("gestureSetLockView");
        } else {
            getViewUnlockIos();
            getViewUnlockIos();
        }
        click(myTab);
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
        //获取控件起始坐标的x、y
        int bX = elements.get(0).getLocation().getX();
        int bY = elements.get(0).getLocation().getY();
        // 第二个点的坐标
        int twoX = elements.get(3).getLocation().getX();
        int twoY = elements.get(3).getLocation().getY();
        // 第三个点的坐标
        int threeX = elements.get(6).getLocation().getX();
        int threeY = elements.get(6).getLocation().getY();
        // 第四个点的坐标
        int fourX = elements.get(7).getLocation().getX();
        int fourY = elements.get(7).getLocation().getY();
        // 第五个点的坐标
        int fiveX = elements.get(8).getLocation().getX();
        int fiveY = elements.get(8).getLocation().getY();
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
        if (appiumDriver.getPageSource().contains("忘记密码") && appiumDriver.getPageSource().contains("注册")) {
            return true;
        }
        return false;
    }

}
