package com.iqianjin.appperformance.cases;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class LendRecord extends BaseCase {

    private String lendRecord = "出借记录";
    private String aybRecord = "爱盈宝";
    private String zcbRecord = "整存宝+";
    private String sanbiaoRecord = "散标";
    private String inRecord = "转让中";
    private String offecord = "已转让";
    private String overRecord = "已结束";
    private String noMore = "没有更多";
    private String record_invert_item_issue = "散标出借记录期号";
    private String yjbRecord = "月进宝记录";


    public void isRenewed() {
        click(myTab);
        for (int i = 0; i < 2; i++) {
            log.info("当前页面是否包含立即续期");
            if (appiumDriver.getPageSource().contains("立即续期")) {
                swipeUpOrDown(0.5, 0.1);
            }
        }
        click(lendRecord);
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
            click(by);
            log.info("开始滑动");
//            swipeToBottom(0.5, 0.1);
            swipeToNum(0.5, 0.1, 5);
            swipeLeftOrRight(0.8, 0.1);
//            swipeToBottom(0.5, 0.1);
            swipeToNum(0.5, 0.1, 5);
            goBack();
        }
    }

    public void sanbiaoRecord(int num) {
        for (int i = 0; i < num; i++) {
            swipUpWaitElement(0.5, 0.1, sanbiaoRecord).click();
//            swipeToBottomSuper(record_invert_item_issue, 0.5, 0.1);
            swipeToNum(0.5, 0.1, 5);
            click(inRecord);
//            swipeToBottomSuper(record_invert_item_issue, 0.5, 0.1);
            swipeToNum(0.5, 0.1, 5);
            click(offecord);
//            swipeToBottomSuper(record_invert_item_issue, 0.5, 0.1);
            swipeToNum(0.5, 0.1, 5);
            click(overRecord);
//            swipeToBottomSuper(record_invert_item_issue, 0.5, 0.1);
            swipeToNum(0.5, 0.1, 5);
            goBack();
        }
    }

    public static LendRecord getInstance() {
        return LendRecord.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final LendRecord INSTANCE = new LendRecord();
    }

}
