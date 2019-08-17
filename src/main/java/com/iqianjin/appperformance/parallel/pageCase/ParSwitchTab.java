package com.iqianjin.appperformance.parallel.pageCase;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ParSwitchTab extends ParBase {

    public void changeTab(int num){
        // 先处理 首页我的 webview弹窗
        sleep(2);
        if (parIsWebview()){
            parClickWebview();
        }
        parClick(homeTab);
        if (parIsWebview()){
            parClickWebview();
        }

        for (int i = 0; i < num; i++) {
            parClick(homeTab);
            parClick(productTab);
            parClick(findTab);
            parClick(myTab);
        }
    }

    public static ParSwitchTab getInstance() {
        return ParSwitchTab.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final ParSwitchTab INSTANCE = new ParSwitchTab();
    }
}
