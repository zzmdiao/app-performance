package com.iqianjin.appperformance.cases;

import com.iqianjin.appperformance.BaseTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RegisterTest extends BaseTest {

    @BeforeAll
    static void cleanData() {
        //todo:清除数据，接口或者sql
    }

    @ParameterizedTest
    @CsvSource({
            "333 1234 5678, 手机号码输入有误"
    })
    @Disabled
    public void loginFail(String phone, String expct) {
        Register.getInstance().gotoRegisterPage();
        String result = Register.getInstance().registerFailAndroid(phone);
        assertEquals(expct, result);
    }

    @ParameterizedTest(name = "注册{index},expct={2}")
    @CsvSource({
            "18940867596,test123, 该用户已注册",
            "18610055056,test123, 设置"
    })
    @Disabled
    public void loginSucess(String phone, String password, String expct) {
        Register.getInstance().gotoRegisterPage();
//        String result = Register.getInstance().registerSucessAndroid(phone, password);
//        assertEquals(expct, result);
    }
}
