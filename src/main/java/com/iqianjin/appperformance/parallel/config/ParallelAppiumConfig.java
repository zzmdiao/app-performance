package com.iqianjin.appperformance.parallel.config;

import lombok.Data;

import java.util.HashMap;

@Data
public class ParallelAppiumConfig {

    private Integer wait;
    private HashMap<String, String> androidCapabilities;
    private HashMap<String, String> iosCapabilities;

}
