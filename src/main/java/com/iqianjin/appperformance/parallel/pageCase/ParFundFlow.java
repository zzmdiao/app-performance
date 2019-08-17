package com.iqianjin.appperformance.parallel.pageCase;

public class ParFundFlow extends ParBase {

    private String fundFlowTab = "资金流水";
    private String tvFlowTime = "资金流水日期";
    private String recharge = "充值";
    private String cash = "提现";
    private String lend = "出借";
    private String recover = "回收";
    private String asignment = "转让债权";
    private String frozen = "冻结";

    public void fundTab(int num) {
        parClick(myTab);
        sleep(1);
        if (isInPageSource("立即续期")) {
            parSwipeUpOrDown(0.5, 0.1);
        }
        for (int i = 0; i < num; i++) {
            parClick(fundFlowTab);

            swipFund(recharge);
            swipFund(cash);
            swipFund(lend);
            swipFund(recover);
            if ("android".equalsIgnoreCase(getPlatName())) {
                swipFund(asignment);
                swipFund(frozen);
            }
            parGoBack();
        }
    }

    public void swipFund(String str) {
        parSwipeToNum(0.5, 0.1, 5);
        parClick(str);
    }

    public static ParFundFlow getInstance() {
        return ParFundFlow.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final ParFundFlow INSTANCE = new ParFundFlow();
    }

}
