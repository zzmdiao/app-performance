package com.iqianjin.appperformance.cases;

import com.iqianjin.appperformance.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class LendRecordTest extends BaseTest {
    @Autowired
    LendRecord lendRecord;
    @Autowired
    Login login;
    @Autowired
    BaseCase baseCase;

    @Test
    @DisplayName("爱盈宝出借记录")
    public void aybProductRecordTest() {
        baseCase.startMonitoring();
        login.login("zzm003", "test123");
        lendRecord.aybProductRecord(3);
    }

    @Test
    @DisplayName("整存宝+出借记录")
    public void zcbProductRecordTest() {
        baseCase.startMonitoring();
        login.login("zzm001", "test123");
        lendRecord.zcbProductRecord(3);
    }

    @Test
    @DisplayName("散标出借记录")
    public void sanbiaoProductRecordTest() {
        baseCase.startMonitoring();
        login.login("zzm001", "test123");
        lendRecord.sanbiaoRecord(3);
    }

}