package com.sseance.cwa.checkworkattendance.service.impl;

import com.sseance.cwa.checkworkattendance.bean.SheetHandler;
import com.sseance.cwa.checkworkattendance.bean.User;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TestServiceImpl {


    public static void main(String[] args) throws Exception {
        Workbook wb = null;
        File file = new File("C:\\Users\\76790\\Desktop\\daochu2.xlsx");
        InputStream fis = new FileInputStream(file);
        wb = new XSSFWorkbook(fis);
        Sheet sheet = wb.getSheetAt(0);
        List<User> users = new ArrayList<>();
        for (Row row : sheet) {
            if (row.getRowNum() > 3) {
                User user = new User();
                int len = row.getLastCellNum();
                for (int i = 0; i < len; i++) {
                    String str = row.getCell(i).toString();
                    switch (i) {
                        case 0:
                            user.setName(str);
                            break;
                        case 3:
                            user.setNumber(str);
                            break;
                        case 6:
                            user.setLeave(hourToDay(setNumber(str)));
                            break;
                        case 7:
                            user.setRest(setNumber(str));
                            break;
                        case 8:
                            user.setWorkDay(setNumber(str));
                            break;
                        case 9:
                            user.setRealWorkDay(setNumber(str));
                            break;
                        case 10:
                            user.setAbsenteeismDay(setNumber(str));
                            break;
                        case 11:
                            user.setForgetNumber(setNumber(str));
                            break;
                        case 12:
                            user.setForgetNumber(handleForget(user.getForgetNumber(), setNumber(str)));
                            break;
                        case 13:
                            user.setTimeNotes(handleLeave(str));
                            break;
                    }
                }
                users.add(user);
            }
        }
        fis.close();
        exportMuBan(new File("C:/Users/76790/Desktop/out.csv"),users);

    }

    public static String handleRealWorkDay(User user) {
        if (user.getWorkDay() != null) {
            if (StringUtils.hasText(user.getLeave())) {
                DecimalFormat df = new DecimalFormat("0.00");
                return df.format((float)user.getWorkDay()-Float.parseFloat(user.getLeave()));
            }
            return user.getWorkDay().toString();
        }
        return "";
    }

    public static void exportMuBan(File file, List<User> dataList) {
        try (FileOutputStream out = new FileOutputStream(file);
             OutputStreamWriter osw = new OutputStreamWriter(out, "gbk");
             BufferedWriter bw = new BufferedWriter(osw)) {
            bw.append("姓名").append(",")
                    .append("外包工号").append(",")
                    .append("应出勤天数").append(",")
                    .append("工时").append(",")
                    .append("工时备注").append(",")
                    .append("缺卡次数").append(",")
                    .append("异常情况").append("\r");
            if (dataList != null && !dataList.isEmpty()) {
                for (User dto : dataList) {
                    boolean flag = false;
                    bw.append(dto.getName()).append(",")
                            .append(dto.getNumber()).append(",")
                            .append(String.valueOf(dto.getWorkDay())).append(",")
                            .append(handleRealWorkDay(dto)).append(",")
                            .append(dto.getTimeNotes()).append(",")
                            .append(dto.getForgetNumber() !=null?dto.getForgetNumber().toString():"").append(",")
                            .append(dto.getForgetNumber() !=null && dto.getForgetNumber() !=null?"异常":"").append("\r");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Integer handleForget(Integer forgetNumber, Integer setNumber) {
        if (forgetNumber != null) {
            if (setNumber != null) {
                return forgetNumber + setNumber;
            }
            return forgetNumber;
        }
        return setNumber;
    }

    public static String handleLeave(String str) {
        StringBuilder result = new StringBuilder();
        if (StringUtils.hasText(str) && str.contains("事假")) {
            Set<String> set = new HashSet<>();
            for (String record : str.split("小时")) {
                if (record.contains("事假")) {
                    set.add(record + "小时");
                }
            }
            for (String s : set) {
                result.append(s).append("\t");
            }
        }
        return result.toString();
    }

    public static String hourToDay(Integer hour) {
        if (hour != null && !hour.equals(0)) {
            DecimalFormat df = new DecimalFormat("0.00");
            return df.format((float) hour / 8);
        }
        return null;
    }

    public static Integer setNumber(String string) {
        if (StringUtils.hasText(string)) {
            try {
                return Integer.valueOf(string);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }


    public void One() throws Exception {
        OPCPackage pkg = OPCPackage.open("C:\\Users\\76790\\Desktop\\gongshi.xlsx");

        XSSFReader reader = new XSSFReader(pkg);
        SharedStringsTable sst = reader.getSharedStringsTable();
        StylesTable st = reader.getStylesTable();

        XMLReader parser = fetchSheetParser(sst);

        XSSFReader.SheetIterator sheets = (XSSFReader.SheetIterator) reader.getSheetsData();

        InputStream sheet2 = sheets.next();

        InputSource sheetSource = new InputSource(sheet2);

        parser.parse(sheetSource);

        sheet2.close();
    }

    public static XMLReader fetchSheetParser(SharedStringsTable sst) throws SAXException {

        XMLReader parser = XMLReaderFactory.createXMLReader();

        ContentHandler handler = new SheetHandler();

        parser.setContentHandler(handler);

        return parser;

    }
}
