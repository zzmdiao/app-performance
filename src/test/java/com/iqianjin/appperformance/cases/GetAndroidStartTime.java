package com.iqianjin.appperformance.cases;

import com.iqianjin.appperformance.base.GetStartTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GetAndroidStartTime {

    @Test
    @DisplayName("通过adb命令获取android启动时长，写入文件startTime.txt")
    void getStartTime() {
        GetStartTime.getAndroidStartTime(5);
    }

    @Test
    @DisplayName("通过录屏分帧获取启动时长")
    void getAndroidStartTime() {
        GetStartTime.getFFmpeg();
    }
}
