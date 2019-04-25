package com.iqianjin.appperformance.getData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GetBattery {
    private static Logger logger = LoggerFactory.getLogger(GetBattery.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        for (int i = 0; i < 100; i++) {
            logger.info(battery() + "");
            Thread.sleep(3000);
        }
    }

    public static double battery() {
        double batt = 0;
        Runtime runtime = Runtime.getRuntime();
        Process proc = null;
        try {
            proc = runtime.exec("adb  shell dumpsys battery");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String str3 = "0";
        try {
            if (proc.waitFor() != 0) {
                logger.error("exit value = " + proc.exitValue());
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            StringBuffer stringBuffer = new StringBuffer();
            String line = null;
            while (true) {
                try {
                    if (!((line = in.readLine()) != null)) break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stringBuffer.append(line + " ");
                if (line.contains("level")) {
                    str3 = line.substring(line.indexOf(":") + 2, line.length());
                }
            }
            batt = Double.parseDouble(str3.trim());
        } catch (InterruptedException e) {
            batt = -0.1;
            e.printStackTrace();
        } finally {
            try {
                proc.destroy();
            } catch (Exception e2) {
            }
        }
        return (batt);
    }
}
