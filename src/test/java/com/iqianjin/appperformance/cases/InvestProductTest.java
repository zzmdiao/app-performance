package com.iqianjin.appperformance.cases;

import com.iqianjin.appperformance.BaseTest;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class InvestProductTest extends BaseTest {

    @Test
    @Order(1)
    @DisplayName("购买爱盈宝")
    public void aybProductRecordTest() {
        Login.getInstance().login("zzm001", "test123");
        InvestProduct.getInstance().buyAYB(1, "1000");
    }

    @Test
    @Order(2)
    @DisplayName("购买月进宝")
    public void yjbProductRecordTest() {
        InvestProduct.getInstance().buyYJB(1, "1000");
    }
}
