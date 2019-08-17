package com.iqianjin.appperformance.parallel.config;


import com.iqianjin.appperformance.parallel.pageCase.ParBase;
import com.iqianjin.appperformance.util.Adbutils;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class SetEnv extends ParBase {

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
        if ("ios".equalsIgnoreCase(getPlatName())) {
            log.info("ios ---- 开始开启数据采集");
            parClick(dFloatingWindow);
            parClick(dCustomButton);
            sleep(2);
            if (isInPageSource("数据采集已关闭")) {
                clearData();
                parClick(dDataCollection);
            } else {
                parClick(dDataCollection);
                clearData();
                parClick(dDataCollection);
            }
            parClick(dFloatingWindow);
        }
    }

    public void stopIosMonitor() {
        if ("ios".equalsIgnoreCase(getPlatName())) {
            log.info("ios ---- 开始关闭数据采集");
            parClick(dFloatingWindow);
            parClick(dCustomButton);
            parClick(dDataCollection);
        }
    }

    public void clearData() {
        parClick(dFloatingWindow);
        parClick(dFloatingWindow);
        parClick(dClearData1);
        parClick(dClearData2);
        parClick(dConfirmButton);
        parClick(dFloatingWindow);
        parClick(dFloatingWindow);
        parClick(dCustomButton);
    }

    /**
     * 切换环境
     *
     * @param envi
     */
    public void switchEnvironment(String envi) {
        if ("ios".equalsIgnoreCase(getPlatName())) {
            parLongPreePostion(70, 70);
            parClick(xiaolian);
            parClick(xiaolianEnvi);
            parSendKeys(xiaolianEnviInput, envi);
            parClick(xiaolianButton);
            parClick(productTab);

            parClick(myTab);
            parClick(otherLogin);
        }
    }

    //开启或关闭android性能采集
    public  void androidMonitoring() {
        if ("android".equalsIgnoreCase(getPlatName())) {
            log.info("开启/关闭android监控");
            Adbutils.execAdb("adb shell am start -n com.iqianjin.client/com.didichuxing.doraemonkit.ui.UniversalActivity -e fragment_CONTENT 18");
        }
    }

    //将android最后的结果文件导出
    public  void getAndroidImportFile() {
        if ("android".equalsIgnoreCase(getPlatName())) {
            log.info("pull android 数据到本地");
            sleep(10);
            Adbutils.execAdb("adb pull /sdcard/Android/data/com.iqianjin.client/files/doraemon/result/ .");
            log.info("开始转换hprof文件");
            String shpath = System.getProperty("user.dir") + "/result/changeFile.sh";
            String shpath2 = System.getProperty("user.dir") + "/result/";
            Adbutils.execAdb("/bin/sh " + shpath + " " + shpath2);

        }
    }

    public static SetEnv getInstance() {
        return SetEnv.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final SetEnv INSTANCE = new SetEnv();
    }
}
