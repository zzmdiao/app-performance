package com.iqianjin.appperformance.cases;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Register extends Login {

    private static Logger logger = LoggerFactory.getLogger(Register.class);

    private String loginBackRegistTv = "注册按钮";
    private String registSubmitTv = "注册下一步";
    private String registDescTv = "注册页提示文案";

    public static Register getInstance(){
        return SingletonHolder.INSTANCE;
    }
    private static class SingletonHolder {
        private static final Register INSTANCE = new Register();
    }

    public String registerFailAndroid(String phoneNumber) {
        sendKeys(userName,phoneNumber);
        click(registSubmitTv);
        return findByWait(registDescTv,2).getText();
    }

    public void gotoRegisterPage() {
        if(isLogin()) {
            logOut( "aa");
            click(loginBackRegistTv);
        }
    }
}
