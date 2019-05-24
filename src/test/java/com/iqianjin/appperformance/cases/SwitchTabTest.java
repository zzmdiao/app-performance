package com.iqianjin.appperformance.cases;

import com.iqianjin.appperformance.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SwitchTabTest extends BaseTest {

    @Test
    @DisplayName("切换四个tab")
    public void switchTab() {
        Login.getInstance().login("zzm001", "test123");
        SwitchTab.getInstance().changeTab(5);
    }
}
