package com.iqianjin.appperformance.cases;

import com.iqianjin.appperformance.util.CommandUtil;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class GetAndroidStartTime {
    CommandUtil commandUtil = new CommandUtil();

    @Test
    @Tag("采集android启动时间，写入文件startTime.txt")
    void getStartTime() {
        commandUtil.getAndroidStartTime(3);
    }
}
