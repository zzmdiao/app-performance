package com.iqianjin.appperformance.cases;

import com.iqianjin.appperformance.util.CommandUtil;

public class SwitchTab extends BaseCase {

    public void changeTab(int number) {
        // 先处理 首页我的 webview弹窗
        if (isWebview()){
            if ("android".equalsIgnoreCase(platformName)){
                goBack();
            }else {
                clickWebview();
            }
        }
        click(homeTab);
        if (isWebview()){
            if ("android".equalsIgnoreCase(platformName)){
                goBack();
            }else {
                clickWebview();
            }
        }

        for (int i = 0; i < number; i++) {
            if (!click(homeTab)) {
                continue;
            }
            if (!click(productTab)) {
                continue;
            }
            if (!click(findTab)) {
                continue;
            }
            if (!click(myTab)) {
                continue;
            }
        }
    }

    public static SwitchTab getInstance() {
        return SwitchTab.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final SwitchTab INSTANCE = new SwitchTab();
    }
}
