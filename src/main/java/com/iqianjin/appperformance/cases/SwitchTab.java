package com.iqianjin.appperformance.cases;

public class SwitchTab extends BaseCase {

    public void changeTab(int number) {
        for (int i = 0; i < number; i++) {
            click(homeTab);
            click(productTab);
            click(findTab);
            click(myTab);
        }
    }

    public static SwitchTab getInstance() {
        return SwitchTab.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final SwitchTab INSTANCE = new SwitchTab();
    }
}
