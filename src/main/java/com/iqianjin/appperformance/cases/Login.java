package com.iqianjin.appperformance.cases;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

@Component
public class Login extends BaseCase {

    private static Logger logger = LoggerFactory.getLogger(Login.class);

//    private By otherLogin = By.id("registOtherLoginLl");
//    public By userName = By.id("userNameEt");
//    private By passwdStatus = By.id("passwdStatusEt");
//    private By loginSubmit = By.id("loginSubmitTv");
//    private By gestureForget = By.id("gestureForget");
//    private By loginAgain = By.xpath("//*[@text='重新登录']");

    private String otherLogin = "其他登录";
    public String userName = "用户名";
    public String passwdStatus = "密码";
    public String loginSubmit = "登录按钮";
    public String gestureForget = "忘记手势密码";
    public String loginAgain = "重新登录";
    public String cancle = "取消升级";

    @Value("${platformName}")
    public String platformName;

    public Login(AppiumDriver<? extends MobileElement> appiumDriver) {
        super(appiumDriver);
    }


    /**
     * 判断是否已登录
     */
    public boolean isLogin() {
        boolean isExist = true;
        if (appiumDriver.getPageSource().contains("跳过")) {
            click(tv_start);
        }
//        if (appiumDriver.getPageSource().contains("发现新版本")) {
//            click(cancle);
//        }
        if (appiumDriver.getPageSource().contains("忘记手势密码") || appiumDriver.getPageSource().contains("请绘制解锁图案")) {
            return isExist;
        }
        if (appiumDriver.getPageSource().contains("我的")) {
            click(myTab);
            if (appiumDriver.getPageSource().contains("注册/登录爱钱进")) {
                isExist = false;
            }
        }
        logger.info("是否登录isExist:{}", isExist);
        return isExist;
    }

//    public void logout() {
//        if (appiumDriver.getPageSource().contains("忘记手势密码")) {
//            logger.info("当前页面包含《忘记手势密码》元素，尝试解锁手势");
//
//            if (platformName.equalsIgnoreCase("android")){
//                getViewUnlockAndroid("gestureLockPatternView");
//            }else {
//                getViewUnlockIos();
//            }
//
//            if (appiumDriver.getPageSource().contains("密码输入错误")) {
//                click(gestureForget);
//                click(loginAgain);
//            }
//
//        }
//    }

    /**
     * 登录
     *
     * @param user
     * @param pass
     */
    public void login(String user, String pass) {
        if (isLogin()) {
//            if (appiumDriver.getPageSource().contains("忘记手势密码")) {
//                logger.info("当前页面包含《忘记手势密码》元素，尝试解锁手势");
            //尝试解锁
            if (platformName.equalsIgnoreCase("android")) {
                getViewUnlockAndroid("gestureLockPatternView");
                if (appiumDriver.getPageSource().contains("设置")) {
                    return;
                }
            } else {
                getViewUnlockIos();
                if (appiumDriver.getPageSource().contains("设置")) {
                    return;
                }
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
//            }
        }
        //登录
        click(myTab);
        if (appiumDriver.getPageSource().contains("注册/登录爱钱进")) {
            click(otherLogin);
            loginAgain(user, pass);
        }
//        //设置锁屏手势
//        if (appiumDriver.getPageSource().contains("绘制解锁图案")) {
//            logger.info("当前页面包含 《绘制解锁图案》元素,开始绘制解锁手势");
//            if("android".equalsIgnoreCase(platformName)){
//                getViewUnlockAndroid("gestureSetLockView");
//                getViewUnlockAndroid("gestureSetLockView");
//            }else {
//                getViewUnlockIos();
//                getViewUnlockIos();
//            }
//            click(myTab);
//        }

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
        logger.info("设置android解锁图案");
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
        logger.info("设置ios解锁图案");
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
        if (appiumDriver.getPageSource().contains("登录") && appiumDriver.getPageSource().contains("忘记密码") && appiumDriver.getPageSource().contains("注册")) {
            return true;
        }
        return false;
    }

}
