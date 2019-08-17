package com.iqianjin.appperformance.parallel.pageCase;

import com.iqianjin.appperformance.parallel.ParllelAndroidDriver;
import com.iqianjin.appperformance.parallel.ParllelIosDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;


@Slf4j
public class ParLogin extends ParBase {

    private String otherLogin = "其他登录";
    public String userName = "用户名";
    public String passwdStatus = "密码";
    public String loginSubmit = "登录按钮";
    public String gestureForget = "忘记手势密码";
    public String setLock = "请绘制解锁图案";
    public String loginAgain = "重新登录";
    public String cancle = "取消升级";
    public String setUP = "设置按钮";
    public String loginOut = "退出登录";
    public String confirmButton = "确认按钮";


    /**
     * 登录
     *
     * @param user
     * @param pass
     */
    public void login(String user, String pass) {
        WebElement element = parSimpleFind(tv_start);
        if (null != element) {
            parClickElement(element);
        }
        log.info("开始进入注册/登录");
        if (isLogin()) {
            if (isInPageSource(user)) {
                log.info("当前用户是同一个，尝试解锁");
                setOneUnlock();
                if (isInPageSource("密码输入错误")) {
                    logOut(user);
                    loginAgain(user, pass);
                }
            } else {
                logOut(user);
                loginAgain(user, pass);
            }
        } else {
            parClick(otherLogin);
            loginAgain(user, pass);
        }

    }

    /**
     * 判断是否已登录
     */
    public boolean isLogin() {
        boolean flag = false;
        sleep(2);
        if (parIsWebview()) {
            parClickWebview();
        }

        WebElement myTabElement = find(myTab,1);
        if (null != myTabElement) {
            parClickElement(myTabElement);
            if (null != find(otherLogin,1)) {
                log.info("点击我的，当前是否是登录状态:{}", flag);
                return flag;
            }
        }

        if (null != parSimpleFind(gestureForget) || null != parSimpleFind(setLock)) {
            flag = true;
        }
        log.info("当前是否是登录状态:{}", flag);
        return flag;
    }

    public void loginAgain(String user, String pass) {
        for (int i = 0; i < 3; i++) {
            if (loginFail()) {
                parSendKeys(userName, user);
                parSendKeys(passwdStatus, pass);
                parClick(loginSubmit);
            } else {
                log.info("登录完成");
                break;
            }
        }

        setUnlock();
    }

    /**
     * 退出登录
     *
     * @param
     */
    public void logOut(String user) {
        if (null != parSimpleFind(gestureForget)) {
            log.info("当前页面包含《忘记手势密码》元素");
            parClick(gestureForget);
            parClick(loginAgain);
            if ("android".equalsIgnoreCase(getPlatName())) {
                parClick(otherLogin);
            }
        }
        if (null != parSimpleFind(setLock)) {
            setUnlock();
            parClick(myTab);
            if (isInPageSource(user)){
                return;
            }

            parClick(setUP);
            parSwipeToNum(0.5, 0.1, 1);
            parClick(loginOut);
            parClick(confirmButton);
            parClick(myTab);
            parClick(otherLogin);
        }
    }

    public void setUnlock() {
        if (null != parSimpleFind(setUP)) {
            return;
        }
        if ("android".equalsIgnoreCase(getPlatName())) {
            getViewUnlockAndroid("gestureSetLockView");
            getViewUnlockAndroid("gestureSetLockView");
        } else {
            getViewUnlockIos();
            getViewUnlockIos();
        }
    }

    public void setOneUnlock() {
        if (null != parSimpleFind(setUP)) {
            return;
        }
        if ("android".equalsIgnoreCase(getPlatName())) {
            getViewUnlockAndroid("gestureLockPatternView");
        } else {
            getViewUnlockIos();
        }
    }

    //根据view插件设置九宫格解锁
    public void getViewUnlockAndroid(String ElementView) {
        log.info("设置android解锁图案L");
        WebElement webElement = ParllelAndroidDriver.getInstance().appiumDriverAndroid.findElement(By.id(ElementView));
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
        TouchAction ta = new TouchAction(ParllelAndroidDriver.getInstance().appiumDriverAndroid);
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
        log.info("设置ios解锁图案L");
        List<WebElement> elements = ParllelIosDriver.getInstance().appiumDriverios.findElements(By.xpath("//XCUIElementTypeButton"));
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
        TouchAction ta = new TouchAction(ParllelIosDriver.getInstance().appiumDriverios);
        Duration duration = Duration.ofMillis(500);
        ta.longPress(PointOption.point(bX, bY)).waitAction(WaitOptions.waitOptions(duration))
                .moveTo(PointOption.point(twoX, twoY)).waitAction(WaitOptions.waitOptions(duration))
                .moveTo(PointOption.point(threeX, threeY)).waitAction(WaitOptions.waitOptions(duration))
                .moveTo(PointOption.point(fourX, fourY)).waitAction(WaitOptions.waitOptions(duration))
                .moveTo(PointOption.point(fiveX, fiveY)).waitAction(WaitOptions.waitOptions(duration))
                .release().perform();

    }

    public Boolean loginFail() {
        if (null != parSimpleFind(setUP) || null != parSimpleFind(setLock)) {
            return false;
        } else {
            return true;
        }
    }

    public static ParLogin getInstance() {
        return ParLogin.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final ParLogin INSTANCE = new ParLogin();
    }
}

