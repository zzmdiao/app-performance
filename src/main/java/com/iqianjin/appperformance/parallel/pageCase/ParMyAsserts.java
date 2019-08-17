package com.iqianjin.appperformance.parallel.pageCase;

public class ParMyAsserts extends ParBase {
    private String tvYesterdayEarnings = "昨日出借回报";
    private String tvTotalProfit = "累计出借回报";
    private String tvTotalAssets = "我的资产";
    private String tvRedBagAmount = "优惠劵";
    private String tv_redbag_new_expired = "查看过期优惠劵";
    private String tv_reward_new_expired = "查看过期奖励劵";
    private String myReward_tab_juan = "我的奖励-奖励劵";
    private String tv_add_interest_guide_confirm = "奖励劵弹窗-知道了按钮";

    public void browseAsserts(int number) {
        parClick(myTab);
        for (int i = 0; i < number; i++) {
            parClick(tvYesterdayEarnings);
            parSwipeToNum(0.5, 0.1, 2);
            parGoBack();
            parClick(tvTotalProfit);
            parSwipeToNum(0.5, 0.1, 2);
            parGoBack();
            parClick(tvTotalAssets);
            parSwipeToNum(0.8, 0.3, 1);
            parGoBack();
            //优惠劵
            browserRedBag();
            //奖励劵
            browseReward();
        }
    }

    public void browserRedBag() {
        parClick(tvRedBagAmount);

        parSwipUpWaitElement(0.5, 0.1, tv_redbag_new_expired);
        parClick(tv_redbag_new_expired);
        if (isInPageSource("暂无优惠劵")) {
            parGoBack();
        } else {
            parSwipeToNum(0.5, 0.1, 5);
            parGoBack();
        }
    }

    public void browseReward() {
        parClick(myReward_tab_juan);
        sleep(1);
        if (isInPageSource("知道了")) {
            parClick(tv_add_interest_guide_confirm);
        }
        parSwipUpWaitElement(0.5, 0.1, tv_reward_new_expired);
        parClick(tv_reward_new_expired);

        if (isInPageSource("暂无奖励劵")) {
            parGoBack();
            parGoBack();
        } else {
            parSwipeToNum(0.5, 0.1, 5);
            parGoBack();
            parGoBack();
        }
    }

    public static ParMyAsserts getInstance() {
        return ParMyAsserts.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final ParMyAsserts INSTANCE = new ParMyAsserts();
    }
}
