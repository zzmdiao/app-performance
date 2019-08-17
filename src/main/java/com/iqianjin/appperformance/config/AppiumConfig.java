package com.iqianjin.appperformance.config;

import lombok.Data;

import java.util.HashMap;

@Data
public class AppiumConfig {

    private String url;
    private String app;
    private Integer wait;
    private String platName;
    private HashMap<String, String> androidCapabilities;
    private HashMap<String, String> iosCapabilities;

}
