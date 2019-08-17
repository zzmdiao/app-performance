package com.iqianjin.appperformance.util;

import com.iqianjin.appperformance.base.DriverManger;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

import static com.iqianjin.appperformance.util.ExcelUtils.getCurrentTime;

//android的内存快照，拿去结果后需要执行下面的命令
// hprof-conv -z ./result/1558419108478.hprof ./result/1558419108478-2.hprof
@Slf4j
public class CommandUtil {
    public static String platformName = DriverManger.getInstance().getPlatform();

    public static String getClientVersion(String packageName) {
        Runtime runtime = Runtime.getRuntime();
        Process proc = null;
        try {
            proc = runtime.exec("adb shell dumpsys package " + packageName + " |grep versionName");
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        String line = null;
        try {
            line = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String str = line.substring(line.indexOf("=") + 1, line.length());

        return str;
    }


    public static double streamDouble(double pixRate) {
        double newPixRate = (double) Math.round(pixRate * 100) / 100;
        return newPixRate;
    }

    //将日志输出到log文件
    public static void getAndroidLog() {
        String[] cmds = {"/bin/sh", "-c", "adb logcat -v time *:e | grep com.iqianjin.client > " + System.getProperty("user.dir") + "/data/" + getCurrentTime() + ".log"};
        Adbutils.exec(cmds);
    }

    public static String getPhoneModle() {
        String result = Adbutils.exec("adb shell getprop ro.product.model");
        return result;
    }

    //开启或关闭android性能采集
    public static void androidMonitoring() {
        if ("android".equalsIgnoreCase(platformName)) {
            log.info("开启/关闭android监控");
            Adbutils.execAdb("adb shell am start -n com.iqianjin.client/com.didichuxing.doraemonkit.ui.UniversalActivity -e fragment_CONTENT 18");
        }
    }

    //将android最后的结果文件导出
    public static void getAndroidImportFile() {
        if ("android".equalsIgnoreCase(platformName)) {
            log.info("pull android 数据到本地");
            sleep(10);
            Adbutils.execAdb("adb pull /sdcard/Android/data/com.iqianjin.client/files/doraemon/result/ .");
            log.info("开始转换hprof文件");
            String shpath = System.getProperty("user.dir") + "/result/changeFile.sh";
            String shpath2 = System.getProperty("user.dir") + "/result/";
            Adbutils.execAdb("/bin/sh " + shpath + " " + shpath2);

        }
    }

    public static void sleep(long sec) {
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
