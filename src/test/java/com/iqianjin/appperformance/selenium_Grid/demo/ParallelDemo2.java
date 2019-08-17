package com.iqianjin.appperformance.selenium_Grid.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;


@Execution(ExecutionMode.CONCURRENT)
public class ParallelDemo2 {

    @Test
    void canSetCustomPropertyToApple() {
        for (int i=0;i<10;i++){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("from ParallelDemo2 canSetCustomPropertyToApple"+i);
        }
    }

    @Test
    void canSetCustomPropertyToBanana() {
        for (int i=0;i<10;i++){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("from ParallelDemo2 canSetCustomPropertyToBanana"+i);
        }
    }
}
