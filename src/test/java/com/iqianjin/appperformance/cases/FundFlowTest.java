package com.iqianjin.appperformance.cases;

import com.iqianjin.appperformance.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FundFlowTest extends BaseTest {

    @Test
    @DisplayName("浏览资金流水")
    void fundTab() {
        Login login = new Login();
        FundFlow fundFlow = new FundFlow();
        login.login("zzm005", "test123");
        fundFlow.fundTab(3);
    }
}
