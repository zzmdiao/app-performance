package com.iqianjin.appperformance.cases;


public class Register extends Login {

    private String loginBackRegistTv = "注册按钮";
    private String registSubmitTv = "注册下一步";
    private String registDescTv = "注册页提示文案";

    public String registerFailAndroid(String phoneNumber) {
        sendKeys(userName, phoneNumber);
        click(registSubmitTv);
        return findByWait(registDescTv, 2).getText();
    }

    public void gotoRegisterPage() {
        if (isLogin()) {
            logOut("aa");
            click(loginBackRegistTv);
        }
    }

    public static Register getInstance() {
        return Register.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final Register INSTANCE = new Register();
    }
}
