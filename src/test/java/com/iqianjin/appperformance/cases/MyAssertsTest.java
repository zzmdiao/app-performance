package com.iqianjin.appperformance.cases;

import com.iqianjin.appperformance.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class MyAssertsTest extends BaseTest {


    @Test
    @DisplayName("浏览我的资产、加息劵、红包")
    void browseAsserts() {
        MyAsserts.getInstance().browseAsserts(num);
    }
}
