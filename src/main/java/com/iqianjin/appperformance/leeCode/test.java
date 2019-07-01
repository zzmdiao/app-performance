package com.iqianjin.appperformance.leeCode;

import java.util.ArrayList;
import java.util.List;

public class test {
    public static void main(String[] args) {
        List<String> list = new ArrayList();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        for (String i: list){
            System.out.println(i!="a");
        }
    }
}
