package com.iqianjin.appperformance.cases;



import static com.iqianjin.appperformance.util.CommandUtil.sleep;

public class FundFlow extends BaseCase {

    private String fundFlowTab = "资金流水";
    private String tvFlowTime = "资金流水日期";
    private String recharge = "充值";
    private String cash = "提现";
    private String lend = "出借";
    private String recover = "回收";
    private String asignment = "转让债权";
    private String frozen = "冻结";

    public void fundTab(int num) {
        click(myTab);
        sleep(1);
        if (appiumDriver.getPageSource().contains("立即续期")) {
            swipeUpOrDown(0.5, 0.1);
        }
        for (int i = 0; i < num; i++) {
            boolean flag = click(fundFlowTab);
            if (!flag) {
                continue;
            }
            swipFund(recharge);
            swipFund(cash);
            swipFund(lend);
            swipFund(recover);
            if ("android".equalsIgnoreCase(platformName)) {
                swipFund(asignment);
                swipFund(frozen);
            }
            goBack();
        }
    }

    public void swipFund(String str) {
//        if ("android".equalsIgnoreCase(platformName)) {
//            swipeToBottomSuper(tvFlowTime, 0.5, 0.1);
//        } else {
//            swipeToBottom(0.5, 0.1);
//        }
        swipeToNum(0.5, 0.1, 5);
        click(str);
    }
    public static FundFlow getInstance() {
        return FundFlow.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final FundFlow INSTANCE = new FundFlow();
    }

}
