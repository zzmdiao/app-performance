package com.iqianjin.appperformance.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExcelUtil {
    //todo：每次都会覆盖excel，不会在原来的基础上创建新的sheet
    private static Logger logger = LoggerFactory.getLogger(ExcelUtil.class);
    public static void createExcel(String sheetName, List<List> list) {

        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet(sheetName);
        XSSFRow row;
        XSSFCell cell;

        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i);
            cell = row.createCell(i);
            for (int j = 0; j < list.get(i).size(); j++) {
                cell = row.createCell(j);
                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                cell.setCellValue(list.get(i).get(j).toString());
            }

        }

        try {
            FileOutputStream fout = new FileOutputStream(System.getProperty("user.dir") + "/data/" + getCurrentTime() + ".xlsx");
            wb.write(fout);
            fout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("Excel文件生成成功...");

    }

    public static String getCurrentTime() {
        String nowTime = "";
        Date date = new Date();
        SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd");
        nowTime = from.format(date);
        return nowTime;

    }

}
