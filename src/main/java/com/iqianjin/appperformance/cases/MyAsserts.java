package com.iqianjin.appperformance.cases;

import com.iqianjin.appperformance.util.CommandUtil;

public class MyAsserts extends BaseCase {
    private String tvYesterdayEarnings = "昨日出借回报";
    private String tvTotalProfit = "累计出借回报";
    private String tvTotalAssets = "我的资产";
    private String tvRedBagAmount = "优惠劵";
    private String tv_redbag_new_expired = "查看过期优惠劵";
    private String tv_reward_new_expired = "查看过期奖励劵";
    private String myReward_tab_juan = "我的奖励-奖励劵";
    private String tv_add_interest_guide_confirm = "奖励劵弹窗-知道了按钮";

    public void browseAsserts(int number) {
        click(myTab);
        for (int i = 0; i < number; i++) {
            click(tvYesterdayEarnings);
            swipeToNum(0.5, 0.1, 2);
            goBack();
            click(tvTotalProfit);
            swipeToNum(0.5, 0.1, 2);
            goBack();
            click(tvTotalAssets);
            swipeToNum(0.8, 0.3, 1);
            goBack();
            //优惠劵
            browserRedBag();
            //奖励劵
            browseReward();
        }
    }

    public void browserRedBag() {
        click(tvRedBagAmount);
        if (appiumDriver.getPageSource().contains("暂无优惠劵")) {
            click(tv_redbag_new_expired);
        } else {
            swipeToNum(0.5, 0.1, 5);
            click(tv_redbag_new_expired);
        }
        if (appiumDriver.getPageSource().contains("暂无优惠劵")) {
            goBack();
        } else {
            swipeToNum(0.5, 0.1, 5);
            goBack();
        }
    }

    public void browseReward() {
        click(myReward_tab_juan);
        CommandUtil.sleep(1);
        if (appiumDriver.getPageSource().contains("知道了")) {
            click(tv_add_interest_guide_confirm);
        }
        if (appiumDriver.getPageSource().contains("暂无奖励劵")) {
            click(tv_reward_new_expired);
        } else {
            swipeToNum(0.5, 0.1, 5);
            click(tv_reward_new_expired);
        }

        if (appiumDriver.getPageSource().contains("暂无奖励劵")) {
            goBack();
            goBack();
        } else {
            swipeToNum(0.5, 0.1, 5);
            goBack();
            goBack();
        }
    }

    public static MyAsserts getInstance() {
        return MyAsserts.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final MyAsserts INSTANCE = new MyAsserts();
    }
}
