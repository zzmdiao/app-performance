package com.iqianjin.appperformance.cases;

import com.iqianjin.appperformance.BaseTest;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LendRecordTest extends BaseTest {

    @Test
    @Order(1)
    @DisplayName("爱盈宝出借记录")
    public void aybProductRecordTest() {
        Login.getInstance().login("zzm005", "test123");
        LendRecord.getInstance().aybProductRecord(1);
    }

    @Test
    @Order(2)
    @DisplayName("整存宝+出借记录")
    public void zcbProductRecordTest() {
        LendRecord.getInstance().zcbProductRecord(1);
    }

    @Test
    @Order(3)
    @DisplayName("散标出借记录")
    public void sanbiaoProductRecordTest() {
        LendRecord.getInstance().sanbiaoRecord(3);
    }

}
