package com.iqianjin.appperformance.cases;

import com.iqianjin.appperformance.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SwitchTabTest extends BaseTest {

    @Test
    @DisplayName("切换四个tab")
    public void switchTab() {
        Login login = new Login();
        SwitchTab switchTab = new SwitchTab();
//        baseCase.startMonitoring();
        login.login("zzm001", "test123");
        switchTab.changeTab(20);
    }
}
