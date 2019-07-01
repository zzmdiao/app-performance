package com.iqianjin.appperformance.cases;

import com.iqianjin.appperformance.BaseTest;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class InvestProductTest extends BaseTest {

    @Test
    @Order(2)
    @DisplayName("购买爱盈宝")
    public void aybInvestProductTest() {
        InvestProduct.getInstance().buyAYB(num, "1000");
    }

    @Test
    @Order(1)
    @DisplayName("购买月进宝")
    public void yjbInvestProductTest() {
        InvestProduct.getInstance().buyYJB(num, "1000");
    }
}
