package com.iqianjin.appperformance.cases;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LendRecord extends BaseCase {

    private static Logger logger = LoggerFactory.getLogger(LendRecord.class);

    private String lendRecord = "出借记录";
    private String aybRecord = "爱盈宝";
    private String zcbRecord = "整存宝+";
    private String sanbiaoRecord = "散标";
    private String inRecord = "转让中";
    private String offecord = "已转让";
    private String overRecord = "已结束";
    private String noMore = "没有更多";
    private String record_invert_item_issue = "散标出借记录期号";

    /**
     * num 循环次数
     *
     * @param num
     */
    public void aybProductRecord(int num) {
        click(myTab);
        productRecrod(aybRecord, num);
    }

    public void zcbProductRecord(int num) {
        productRecrod(zcbRecord, num);
    }

    public void productRecrod(String by, int num) {
        for (int i = 0; i < 2; i++) {
            if (appiumDriver.getPageSource().contains("立即续期")) {
                swipeUpOrDown(0.5, 0.2);
            }
        }
        for (int i = 0; i < num; i++) {
            findByWait(lendRecord, 2).click();
            click(by);
            logger.info("开始滑动");
//            swipeToBottom(0.5, 0.1);
            swipeToNum(0.5,0.1,5);
            swipeLeftOrRight(0.8, 0.1);
//            swipeToBottom(0.5, 0.1);
            swipeToNum(0.5,0.1,5);
            goBack();
        }
    }

    public void sanbiaoRecord(int num) {

        for (int i = 0; i < num; i++) {
            click(sanbiaoRecord);
            swipeToBottomSuper(record_invert_item_issue, 0.5, 0.1);
            click(inRecord);
            swipeToBottomSuper(record_invert_item_issue, 0.5, 0.1);
            click(offecord);
            swipeToBottomSuper(record_invert_item_issue, 0.5, 0.1);
            click(overRecord);
            swipeToBottomSuper(record_invert_item_issue, 0.5, 0.1);
            goBack();
        }
    }


}
