package com.iqianjin.appperformance.getData.model;

import java.util.Date;

public class AppPerformanceData {

    private Long id;
    private String type;
    private String value;
    private String platName;
    private String phoneModels;
    private String client_version;
    private Date create_time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPlatName() {
        return platName;
    }

    public void setPlatName(String platName) {
        this.platName = platName;
    }

    public String getPhoneModels() {
        return phoneModels;
    }

    public void setPhoneModels(String phoneModels) {
        this.phoneModels = phoneModels;
    }

    public String getClient_version() {
        return client_version;
    }

    public void setClient_version(String client_version) {
        this.client_version = client_version;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }
}
