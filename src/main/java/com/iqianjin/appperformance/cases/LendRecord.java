package com.iqianjin.appperformance.cases;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;



@Component
public class LendRecord extends BaseCase{

    private static Logger logger = LoggerFactory.getLogger(LendRecord.class);

    public LendRecord(AppiumDriver<? extends MobileElement> appiumDriver) {
        super(appiumDriver);
    }

//    private By lendRecord = By.xpath("//*[@text='出借记录']");
//    private By aybRecord = By.xpath("//*[@text='爱盈宝']");
//    private By zcbRecord = By.xpath("//*[@text='整存宝+']");
//    private By sanbiaoRecord = By.xpath("//*[@text='散标']");
//    private By inRecord = By.xpath("//*[@text='转让中']");
//    private By offecord = By.xpath("//*[@text='已转让']");
//    private By overRecord = By.xpath("//*[@text='已结束']");
//
//    private By record_invert_item_issue=By.id("record_invert_item_issue");//散标出借记录期号

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
     * @param num
     */
    public void aybProductRecord(int num){
        productRecrod(aybRecord , num);
    }

    public void zcbProductRecord(int num){
        productRecrod(zcbRecord, num);
    }

    public void productRecrod(String by, int num){
        click(myTab);
        if(appiumDriver.getPageSource().contains("立即续期")){
            swipeUpOrDown(0.5,0.2);
        }
        for(int i=0;i<num;i++) {
            findByWait(lendRecord,2).click();
            click(by);
            logger.info("开始滑动直到底部");
            swipeToBottom(0.5, 0.1);
            swipeLeftOrRight(0.8, 0.05);
            swipeToBottom(0.5, 0.1);
            goBack();
        }
    }

    public void sanbiaoRecord(int num){
        click(myTab);
        if(appiumDriver.getPageSource().contains("立即续期")){
            swipeUpOrDown(0.5,0.2);
        }
        click(lendRecord);
//        swipeUpOrDown(0.5,0.2);
        swipUpWaitElement(0.5,0.2,lendRecord,1);
        for (int i=0;i<num;i++){
            click(sanbiaoRecord);
            swipeToBottomSuper(record_invert_item_issue,0.5,0.1);
            click(inRecord);
            swipeToBottomSuper(record_invert_item_issue,0.5,0.1);
            click(offecord);
            swipeToBottomSuper(record_invert_item_issue,0.5,0.1);
            click(overRecord);
            swipeToBottomSuper(record_invert_item_issue,0.5,0.1);
            goBack();
        }
    }



}
