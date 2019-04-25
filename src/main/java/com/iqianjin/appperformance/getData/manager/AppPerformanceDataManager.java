package com.iqianjin.appperformance.getData.manager;


import com.iqianjin.appperformance.getData.dao.AppPerformanceDataMapper;
import com.iqianjin.appperformance.getData.model.AppPerformanceData;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class AppPerformanceDataManager {

    @Autowired
    private AppPerformanceDataMapper appPerformanceDataMapper;
    public void createData(String type, String value, String platName,String phoneModels, String client_version, DateTime now) {
        AppPerformanceData insert = new AppPerformanceData();
        insert.setType(type);
        insert.setValue(value);
        insert.setPlatName(platName);
        insert.setPhoneModels(phoneModels);
        insert.setClient_version(client_version);
        insert.setCreate_time(now.toDate());
        appPerformanceDataMapper.insert(insert);
    }

}
