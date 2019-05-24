package com.iqianjin.appperformance.config;

import java.util.HashMap;

public class AppiumConfig {

    private String url;
    private String app;
    private Integer wait;
    private String platName;
    private HashMap<String, String> androidCapabilities;
    private HashMap<String, String> iosCapabilities;

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getApp() {
        return app;
    }

    public Integer getWait() {
        return wait;
    }

    public void setWait(Integer wait) {
        this.wait = wait;
    }

    public String getPlatName() {
        return platName;
    }

    public void setPlatName(String platName) {
        this.platName = platName;
    }
    public HashMap<String, String> getAndroidCapabilities() {
        return androidCapabilities;
    }

    public void setAndroidCapabilities(HashMap<String, String> androidCapabilities) {
        this.androidCapabilities = androidCapabilities;
    }

    public HashMap<String, String> getIosCapabilities() {
        return iosCapabilities;
    }

    public void setIosCapabilities(HashMap<String, String> iosCapabilities) {
        this.iosCapabilities = iosCapabilities;
    }


}
