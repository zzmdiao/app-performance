package com.iqianjin.appperformance.util;

import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class XmlDocumentUtil {
    private static Logger logger = LoggerFactory.getLogger(XmlDocumentUtil.class);
    //todo:待优化，执行效率并没有提高多少 思路：1、减少与appium-server的交互 2、缓存pagesource
    public static boolean contains(String pageSource, String content) {
        SAXReader reader = new SAXReader();
        try {
            Document document = reader.read(pageSource);
            List<Node> list = document.selectNodes(content);
            if (list.size() > 0) {
                return true;
            }
        } catch (DocumentException e1) {
            logger.error("！！！");
        }
        return false;
    }

    public static void main(String[] args) {
        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read("/Users/finup/IdeaProjects/app-performanceTest/src/main/resources/test.xml");
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        List<Node> list = document.selectNodes("//*[@name='我的']");
        System.out.println(list.size());
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }
}

