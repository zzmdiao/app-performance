package com.iqianjin.appperformance.config;

import java.util.*;

public class ElementTypeEnum {
    public static final Map<String, String> mapAndroid = new HashMap();
    public static final Map<String, String> mapIos = new HashMap();

    static {
        /*****************************  android *****************************/
        //开机广告
        mapAndroid.put("开机广告", "xpath://*[contains(@text,'跳过')]");
        //版本升级
        mapAndroid.put("取消升级", "xpath://*[@text='取消']");
        //首页元素
        mapAndroid.put("首页tab", "id:tab1Iv");
        mapAndroid.put("产品tab", "id:tab2Iv");
        mapAndroid.put("发现tab", "id:tab3Iv");
        mapAndroid.put("我的tab", "id:tab4Iv");

        //登录相关
        mapAndroid.put("其他登录", "id:registOtherLoginLl");
        mapAndroid.put("用户名", "id:userNameEt");
        mapAndroid.put("密码", "id:passwdStatusEt");
        mapAndroid.put("登录按钮", "id:loginSubmitTv");
        mapAndroid.put("忘记手势密码", "id:gestureForget");
        mapAndroid.put("重新登录", "xpath://*[@text='重新登录']");

        //出借记录
        mapAndroid.put("出借记录", "xpath://*[@text='出借记录']");
        mapAndroid.put("爱盈宝", "xpath://*[@text='爱盈宝']");
        mapAndroid.put("整存宝+", "xpath://*[@text='整存宝+']");
        mapAndroid.put("散标", "xpath://*[@text='散标']");
        mapAndroid.put("转让中", "xpath://*[@text='转让中']");
        mapAndroid.put("已转让", "xpath://*[@text='已转让']");
        mapAndroid.put("已结束", "xpath://*[@text='已结束']");
        mapAndroid.put("散标出借记录期号", "id:record_invert_item_issue");
        //购买爱盈宝
        mapAndroid.put("三月期立即投资按钮", "xpath://*[@text='3']");
        mapAndroid.put("金额输入框", "id:join_product_edit_text");
        mapAndroid.put("购买页立即投资按钮", "id:join_product_confirm");
        mapAndroid.put("弹框确认投资按钮", "id:confirmBuySubmit");
        mapAndroid.put("购买成功页完成按钮", "id:submit");
        mapAndroid.put("投资成功弹框", ".dialog-invest-close");
        //月进宝
        mapAndroid.put("月进宝","xpath: //*[@text='月进宝']");
        mapAndroid.put("立即投资按钮","id:productItemBtn");
        mapAndroid.put("月进宝金额输入框","id:etAmount");
        mapAndroid.put("月进宝立即投资按钮","id:joinProductConfirm");
        //我的-资金流水
        mapAndroid.put("资金流水", "xpath://*[@text='资金流水']");
        mapAndroid.put("资金流水日期", "id:tvFlowTime");
        /***************************** ios *****************************/
        //开机广告
        mapIos.put("开机广告", "xpath://XCUIElementTypeButton[contains(@text,'跳过')]");
        //版本升级
        mapIos.put("取消升级", "xpath://*[@text='取消']");
        //首页元素
        mapIos.put("首页tab", "id:首页");
        mapIos.put("产品tab", "id:产品");
        mapIos.put("发现tab", "id:发现");
        mapIos.put("我的tab", "id:我的");
        //登录相关
        mapIos.put("其他登录", "id:其他登录");
        mapIos.put("用户名", "id:请输入手机号码/用户名/邮箱");
        mapIos.put("密码", "id:请输入登录密码");
        mapIos.put("登录按钮", "id:登录");
        mapIos.put("忘记手势密码", "id:忘记手势密码");
        mapIos.put("重新登录", "id:重新登录");
        //出借记录
        mapIos.put("出借记录", "id:出借记录");
        mapIos.put("爱盈宝", "id:爱盈宝");
        mapIos.put("整存宝+", "id:整存宝+");
        mapIos.put("散标", "id:散标");
        mapIos.put("转让中", "id:转让中");
        mapIos.put("已转让", "id:已转让");
        mapIos.put("已结束", "id:已结束");
        mapIos.put("没有更多","id:没有更多");
        //todo 散标比好比对，修改id为对应账号的最后一个记录
        mapIos.put("散标出借记录期号", "id:I17N03003");
        //购买爱盈宝
        mapIos.put("三月期立即投资按钮", "xpath://XCUIElementTypeStaticText[@name='3']");
        mapIos.put("金额输入框", "xpath://XCUIElementTypeTextField");
        mapIos.put("购买页立即投资按钮", "id:立即投资");
        mapIos.put("弹框确认投资按钮", "id:确认投资");
        mapIos.put("购买成功页完成按钮", "xpath://XCUIElementTypeApplication[@name=\"爱钱进\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeButton\n");
        mapIos.put("投资成功弹框", ".dialog-invest-close");
        //月进宝
        mapIos.put("月进宝","id:月进宝");
        mapIos.put("立即投资按钮","id:立即投资");
        mapIos.put("月进宝金额输入框","xpath://XCUIElementTypeTextField");
        mapIos.put("月进宝立即投资按钮","id:立即投资");
        //我的-资金流水
        mapIos.put("资金流水", "xpath://*[@text='资金流水']");
        mapIos.put("资金流水日期", "xpth://XCUIElementTypeStaticText");
    }

}
