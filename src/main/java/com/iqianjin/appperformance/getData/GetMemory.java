package com.iqianjin.appperformance.getData;


import com.iqianjin.appperformance.util.CommandUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class GetMemory {
    private static Logger logger = LoggerFactory.getLogger(GetMemory.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        for (int i = 0; i < 100; i++) {
            System.out.println(talHeapSize("com.iqianjin.client"));
        }
    }

    /**
     * test
     *
     * @param PackageName
     * @return
     * @throws IOException
     */
    public static double talHeapSize(String PackageName) {
        double Heap = 0;
        String heapStr = "0";
        try {
            Runtime runtime = Runtime.getRuntime();
            Process proc = runtime.exec("adb  shell top -n 1| grep " + PackageName);
            try {
                if (proc.waitFor() != 0) {
                    logger.error("exit value:{}", proc.exitValue());
                }
                BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
                String line = null;
                while ((line = in.readLine()) != null) {
                    if (line.contains(PackageName) && line.contains("/") == false) {
                        List<String> strList = Arrays.asList(line.split("\\s+"));
                        heapStr = strList.get(9).trim();
                        if (heapStr.contains("K")) {
                            heapStr = strList.get(9).trim().replace("K", "");
                            break;
                        } else {
                            heapStr = strList.get(8).trim().replace("K", "");
                            break;
                        }
                    }
                }
                Heap = Double.parseDouble(heapStr);
            } catch (InterruptedException e) {
                System.err.println(e);
            } finally {
                try {
                    proc.destroy();
                } catch (Exception e2) {
                }
            }
        } catch (Exception StringIndexOutOfBoundsException) {
            logger.info("请检查设备是否连接 内存 ");
            StringIndexOutOfBoundsException.printStackTrace();
            return (-0.1);
        }
        return CommandUtil.streamDouble(Heap / 1024);
    }

}
