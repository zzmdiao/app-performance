package com.iqianjin.appperformance.cases;


public class FundFlow extends BaseCase {

    private String fundFlowTab = "资金流水";
    private String tvFlowTime = "资金流水日期";

    public void fundTab(int num) {
        String noRecord = "暂无流水记录";
        click(myTab);
        if (appiumDriver.getPageSource().contains("立即续期")) {
            swipeUpOrDown(0.5, 0.2);
        }
        click(fundFlowTab);
        for (int i = 0; i < num; i++) {
            click(fundFlowTab);
            swipFund();
            swipFund();
            swipFund();
            swipFund();
            swipFund();
            swipFund();
            swipeToBottomSuper(tvFlowTime, 0.5, 0.1);
            goBack();
        }
    }

    public void swipFund() {
        swipeToBottomSuper(tvFlowTime, 0.5, 0.1);
        swipeLeftOrRight(0.8, 0.05);
    }

}
