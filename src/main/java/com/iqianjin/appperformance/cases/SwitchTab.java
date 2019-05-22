package com.iqianjin.appperformance.cases;

public class SwitchTab extends BaseCase {

    public void changeTab(int number) {
        for (int i = 0; i < number; i++) {
            click(productTab);
            click(findTab);
            click(myTab);
            click(homeTab);
        }
    }

}
