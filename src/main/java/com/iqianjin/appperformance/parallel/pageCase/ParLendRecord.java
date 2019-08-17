package com.iqianjin.appperformance.parallel.pageCase;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;

@Slf4j
public class ParLendRecord extends ParBase {

    private String lendRecord = "出借记录";
    private String aybRecord = "爱盈宝";
    private String zcbRecord = "整存宝+";
    private String sanbiaoRecord = "散标";
    private String inRecord = "转让中";
    private String offecord = "已转让";
    private String overRecord = "已结束";
    private String yjbRecord = "月进宝记录";


    public void isRenewed() {
        parClick(myTab);
        for (int i = 0; i < 2; i++) {
            log.info("当前页面是否包含立即续期");
            if (isInPageSource("立即续期")) {
                parSwipeUpOrDown(0.5, 0.1);
            }
        }
        parClick(lendRecord);
    }

    /**
     * num 循环次数
     *
     * @param num
     */
    public void aybProductRecord(int num) {

        productRecrod(aybRecord, num);
    }

    public void zcbProductRecord(int num) {
        productRecrod(zcbRecord, num);
    }

    public void yjbProductRecord(int num) {
        productRecrod(yjbRecord, num);
    }

    public void productRecrod(String by, int num) {
        for (int i = 0; i < num; i++) {
            parClick(by);
            log.info("开始滑动");
            parSwipeToNum(0.5, 0.1, 5);
            parSwipeLeftOrRight(0.8, 0.1);
            parSwipeToNum(0.5, 0.1, 5);
            parGoBack();
        }
    }

    public void sanbiaoRecord(int num) {
        WebElement element;
        for (int i = 0; i < num; i++) {
            parSwipUpWaitElement(0.5, 0.1, sanbiaoRecord) ;
            parClick(sanbiaoRecord);
            parSwipeToNum(0.5, 0.1, 5);
            parClick(inRecord);
            parSwipeToNum(0.5, 0.1, 5);
            parClick(offecord);
            parSwipeToNum(0.5, 0.1, 5);
            parClick(overRecord);
            parSwipeToNum(0.5, 0.1, 5);
            parGoBack();
        }
    }

    public static ParLendRecord getInstance() {
        return ParLendRecord.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final ParLendRecord INSTANCE = new ParLendRecord();
    }

}
