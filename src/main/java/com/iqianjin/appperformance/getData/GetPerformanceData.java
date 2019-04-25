package com.iqianjin.appperformance.getData;

import com.iqianjin.appperformance.getData.manager.AppPerformanceDataManager;
import com.iqianjin.appperformance.util.CommandUtil;
import com.iqianjin.appperformance.util.ExcelUtil;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

//@Component
public class GetPerformanceData implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(GetPerformanceData.class);

    @Autowired
    private AppPerformanceDataManager appPerformanceDataManager;

    private AtomicBoolean flag = new AtomicBoolean(true);
    private int num = 0;
    public ArrayList<String> cpuList = new ArrayList<>();
    public ArrayList<String> memList = new ArrayList<>();
    public ArrayList<String> batteryList = new ArrayList<>();
    public ArrayList<String> flowList = new ArrayList<>();
    public ArrayList<String> fpsList = new ArrayList<>();
    public ArrayList<String> lostFrameList = new ArrayList<>();
    public String packageName = "com.iqianjin.client";

    private String version = CommandUtil.getClientVersion(packageName);
    private String platName ="android";
    private String phoneModels = CommandUtil.getPhoneModle();
    @Override
    public void run() {
        logger.info("开启线程----------");
        logger.info(Thread.currentThread().getName());
        while (flag.get()) {
            String cpu = Double.toString(GetTop.topCpu(packageName));
            String mem = Double.toString(GetMemory.talHeapSize(packageName));
            String flow = Double.toString((double) (GetFlow.getWifiFlow(packageName)));
            String fps = Double.toString(GetFps.fps(packageName)[0]);
            String lostFrameRate = Double.toString(GetFps.fps(packageName)[1]);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            appPerformanceDataManager.createData("cpu",cpu,platName,phoneModels,version, DateTime.now());
            appPerformanceDataManager.createData("mem",mem,platName,phoneModels,version, DateTime.now());
            appPerformanceDataManager.createData("flow",flow,platName,phoneModels,version, DateTime.now());
            appPerformanceDataManager.createData("fps",fps,platName,phoneModels,version, DateTime.now());
            appPerformanceDataManager.createData("lostFrameRate",lostFrameRate,platName,phoneModels,version, DateTime.now());

            cpuList.add(cpu);
            memList.add(mem);
            flowList.add(flow);
            fpsList.add(fps);
            lostFrameList.add(lostFrameRate);

            num++;
        }
    }

    public void shutDown(String sheetName) {
        flag.set(false);
        logger.info("------------------------测试结果--------------------");
        logger.info("监控次数:{}",num);
        logger.info("cpuList:" + cpuList);
        logger.info("memList:" + memList);
        logger.info("flowList:" + flowList);
        logger.info("fpsList:" + fpsList);
        logger.info("lostFrameList:" + lostFrameList);
        logger.info("---------------------------------------------------");
        List<List> allList = new ArrayList<List>();
        allList.add(cpuList);
        allList.add(memList);
        allList.add(fpsList);
        allList.add(lostFrameList);
        allList.add(batteryList);
        ExcelUtil.createExcel(sheetName, allList);
    }

}
