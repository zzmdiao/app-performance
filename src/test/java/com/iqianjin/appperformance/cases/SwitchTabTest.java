package com.iqianjin.appperformance.cases;

import com.iqianjin.appperformance.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class SwitchTabTest extends BaseTest {
    @Autowired
    private BaseCase baseCase;
    @Autowired
    Login login;
    @Autowired
    SwitchTab switchTab;
    @Test
    public void switchTab(){
        baseCase.startMonitoring();
        login.login("zzm001", "test123");
        switchTab.changeTab(10);
    }
}