package com.iqianjin.appperformance.cases;

import com.iqianjin.appperformance.BaseTest;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LendRecordTest extends BaseTest {

    @Test
    @Order(1)
    @DisplayName("爱盈宝出借记录")
    public void aybProductRecordTest() {
        Login login = new Login();
        LendRecord lendRecord = new LendRecord();
        login.login("zzm005", "test123");
        lendRecord.aybProductRecord(3);
    }

    @Test
    @Order(2)
    @DisplayName("整存宝+出借记录")
    public void zcbProductRecordTest() {
        LendRecord lendRecord = new LendRecord();
        lendRecord.zcbProductRecord(3);
    }

    @Test
    @Order(3)
    @DisplayName("散标出借记录")
    public void sanbiaoProductRecordTest() {
        LendRecord lendRecord = new LendRecord();
        lendRecord.sanbiaoRecord(3);
    }

}
