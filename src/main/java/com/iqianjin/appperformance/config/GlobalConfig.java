package com.iqianjin.appperformance.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "appium")
public class GlobalConfig {

    private String url;
    private String app;
    private Integer wait;
    private Map<String, String> androidCapabilities = new HashMap<>();
    private Map<String, String> iosCapabilities = new HashMap<>();

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public Integer getWait() {
        return wait;
    }

    public void setWait(Integer wait) {
        this.wait = wait;
    }

    public Map<String, String> getAndroidCapabilities() {
        return androidCapabilities;
    }

    public void setAndroidCapabilities(Map<String, String> androidCapabilities) {
        this.androidCapabilities = androidCapabilities;
    }

    public Map<String, String> getIosCapabilities() {
        return iosCapabilities;
    }

    public void setIosCapabilities(Map<String, String> iosCapabilities) {
        this.iosCapabilities = iosCapabilities;
    }
}
