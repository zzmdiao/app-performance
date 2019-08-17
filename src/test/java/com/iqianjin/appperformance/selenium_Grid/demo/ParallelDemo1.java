package com.iqianjin.appperformance.selenium_Grid.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@Execution(ExecutionMode.CONCURRENT)
public class ParallelDemo1 {
    @Test
    void canSetCustomPropertyToApple() {
        for (int i=0;i<10;i++){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("from ParallelDemo1 canSetCustomPropertyToApple"+i);
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
            System.out.println("from ParallelDemo1 canSetCustomPropertyToBanana"+i);
        }
    }

    @Test
    void test01() {
        for (int i=0;i<10;i++){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("from ParallelDemo1 test01 "+i);
        }
    }

    @Test
    void test02() {
        for (int i=0;i<10;i++){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("from ParallelDemo1 test02 "+i);
        }
    }
}
