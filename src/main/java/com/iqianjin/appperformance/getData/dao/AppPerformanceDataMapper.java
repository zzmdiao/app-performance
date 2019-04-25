package com.iqianjin.appperformance.getData.dao;

import com.iqianjin.appperformance.getData.model.AppPerformanceData;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component(value = "appPerformanceDataMapper")
public interface AppPerformanceDataMapper {
    int insert(AppPerformanceData appPerformanceData);
}
