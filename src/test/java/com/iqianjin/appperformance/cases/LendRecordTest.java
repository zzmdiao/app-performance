package com.iqianjin.appperformance.cases;

import com.iqianjin.appperformance.BaseTest;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LendRecordTest extends BaseTest {

    @Test
    @Order(1)
    @DisplayName("爱盈宝出借记录")
    public void aybProductRecordTest() {
        LendRecord.getInstance().aybProductRecord(num);
    }

    @Test
    @Order(2)
    @DisplayName("整存宝+出借记录")
    public void zcbProductRecordTest() {
        LendRecord.getInstance().zcbProductRecord(num);
    }
    @Test
    @Order(3)
    @DisplayName("月进宝出借记录")
    public void yjbProductRecordTest(){
        LendRecord.getInstance().yjbProductRecord(num);
    }
    @Test
    @Order(3)
    @DisplayName("散标出借记录")
    public void sanbiaoProductRecordTest() {
        LendRecord.getInstance().sanbiaoRecord(num);
    }

}
