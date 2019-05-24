package com.iqianjin.appperformance.cases;


public class InvestProduct extends BaseCase {

    private String threeInvestButton = "三月期立即投资按钮";
    private String join_product_edit_text = "金额输入框";
    private String join_product_confirm = "购买页立即投资按钮";
    private String confirmBuySubmit = "弹框确认投资按钮";
    private String buySuccessCompplete = "购买成功页完成按钮";

    private String yjb_Title = "月进宝";
    private String yjb_invest_button = "立即投资按钮";
    private String yjb_product_amount = "月进宝金额输入框";
    private String yjb_product_confirm = "月进宝立即投资按钮";

    public void buyAYB(int num, String amount) {
        for (int i = 0; i < num; i++) {
            click(productTab);
            click(threeInvestButton);
            sendKeys(join_product_edit_text, amount);
            click(join_product_confirm);
            click(confirmBuySubmit);
            clickWebview();
            click(buySuccessCompplete);
        }
    }

    public void buyYJB(int num, String amount) {
        for (int i = 0; i < num; i++) {
            click(productTab);
            click(yjb_Title);
            click(yjb_invest_button);
            sendKeys(yjb_product_amount, amount);
            click(yjb_product_confirm);
            click(confirmBuySubmit);
            click(buySuccessCompplete);
        }
    }

    public static InvestProduct getInstance() {
        return InvestProduct.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final InvestProduct INSTANCE = new InvestProduct();
    }
}
