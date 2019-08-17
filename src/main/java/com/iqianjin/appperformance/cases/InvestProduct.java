package com.iqianjin.appperformance.cases;


import com.iqianjin.appperformance.util.CommandUtil;
import org.openqa.selenium.WebElement;

public class InvestProduct extends BaseCase {

    private String threeInvestButton = "三月期立即投资按钮";
    private String join_product_edit_text = "金额输入框";
    private String join_product_confirm = "购买页立即投资按钮";
    private String confirmBuySubmit = "弹框确认投资按钮";
    private String buySuccessCompplete = "购买成功页完成按钮";
    private String ayb_Title = "爱盈宝";
    private String yjb_Title = "月进宝";
    private String yjb_invest_button = "立即投资按钮";
    private String yjb_product_amount = "月进宝金额输入框";
    private String yjb_product_confirm = "月进宝立即投资按钮";

    public void buyAYB(int num, String amount) {
        click(productTab);
        // 切换环境后无法点击  StaticText is not visible on the screen and thus is not interactable
        CommandUtil.sleep(3);
        for (int i = 0; i < num; i++) {
            click(ayb_Title);

            if (!click(threeInvestButton)) {
                goBack();
                continue;
            }
            sendKeys(join_product_edit_text, amount);
            click(join_product_confirm);
            click(confirmBuySubmit);
            if (isWebview()) {
                if ("android".equalsIgnoreCase(platformName)){
                    goBack();
                }else {
                    clickWebview();
                }
            }
            click(buySuccessCompplete);
            click(productTab);
        }
    }

    public void buyYJB(int num, String amount) {
        CommandUtil.sleep(1);
        click(productTab);
        WebElement element = null;
        for (int i = 0; i < num; i++) {
            element = swipUpWaitElement(0.5, 0.1, yjb_Title);
            clickElement(element);
            click(yjb_invest_button);
            sendKeys(yjb_product_amount, amount);
            click(yjb_product_confirm);
            click(confirmBuySubmit);
            click(buySuccessCompplete);
            click(productTab);
        }
    }

    public static InvestProduct getInstance() {
        return InvestProduct.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final InvestProduct INSTANCE = new InvestProduct();
    }

}
