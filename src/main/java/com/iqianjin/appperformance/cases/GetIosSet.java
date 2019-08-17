package com.iqianjin.appperformance.cases;


import com.iqianjin.appperformance.util.CommandUtil;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class GetIosSet extends BaseCase {

    private String dFloatingWindow = "doraemon悬浮窗";
    private String dCustomButton = "doraemon自定义";
    private String dDataCollection = "doraemon数据采集按钮";
    private String dClearData1 = "doraemon清除本地数据1";
    private String dClearData2 = "doraemon清除本地数据2";
    private String dConfirmButton = "doraemon确定按钮";

    //切换环境
    private String xiaolian = "笑脸";
    private String xiaolianEnvi = "环境";
    private String xiaolianEnviInput = "环境输入框";
    private String xiaolianButton = "确定替换服务器";
    private String otherLogin = "其他登录";

    public void startIosMonitor() {
        if ("ios".equalsIgnoreCase(platformName)) {
            log.info("ios ---- 开始开启数据采集");
            click(dFloatingWindow);
            click(dCustomButton);
            CommandUtil.sleep(2);
            if (appiumDriver.getPageSource().contains("数据采集已关闭")) {
                clearData();
                click(dDataCollection);
            } else {
                click(dDataCollection);
                clearData();
                click(dDataCollection);
            }
            click(dFloatingWindow);
        }
    }

    public void stopIosMonitor() {
        if ("ios".equalsIgnoreCase(platformName)) {
            log.info("ios ---- 开始关闭数据采集");
            click(dFloatingWindow);
            click(dCustomButton);
            click(dDataCollection);
        }
    }

    public void clearData() {
        click(dFloatingWindow);
        click(dFloatingWindow);
        click(dClearData1);
        click(dClearData2);
        click(dConfirmButton);
        click(dFloatingWindow);
        click(dFloatingWindow);
        click(dCustomButton);
    }

    /**
     * 切换环境
     *
     * @param envi
     */
    public void switchEnvironment(String envi) {
        if ("ios".equalsIgnoreCase(platformName)) {
            longPreePostion(70, 70);
            click(xiaolian);
            click(xiaolianEnvi);
            sendKeys(xiaolianEnviInput, envi);
            click(xiaolianButton);
            click(productTab);

            click(myTab);
            click(otherLogin);
        }
    }

    public static GetIosSet getInstance() {
        return GetIosSet.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final GetIosSet INSTANCE = new GetIosSet();
    }
}
