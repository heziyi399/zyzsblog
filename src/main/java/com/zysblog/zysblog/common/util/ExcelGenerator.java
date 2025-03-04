package com.zysblog.zysblog.common.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExcelGenerator {

    public static void main(String[] args) throws IOException {
        String inputFilePath = "/Users/heziyi/Desktop/5313/project-statistic-tools/shell/total.txt"; // 输入的txt文件路径
        List<String> lines = Files.readAllLines(Paths.get(inputFilePath));
        int t = 0;
        for(String line : lines) {
            System.out.println(line);
            t +=Integer.valueOf(line);
        }
        System.out.println(t);
//        String outputFilePath = "output.xlsx"; // 输出的Excel文件路径
        int total = 0;
        Map<String,Integer> personTotalMap = new HashMap<>();
        Pattern pattern = Pattern.compile("\\b\\d+\\b");
        // 读取txt文件
//            List<String> lines = Files.readAllLines(Paths.get(inputFilePath));

        // 计算总和
//            for (String line : lines) {
//                String[] parts = line.split("\\s+");
//                if (parts.length >= 2) {
//                    String name = parts[0];
//                    int number = 0;
//                    try {
//                        number = Integer.parseInt(parts[parts.length-1]);
//                        total+=number;
//                    } catch (NumberFormatException e) {
//                        System.err.println("Invalid number format in line: " + line);
//                    }
//                    personTotalMap.put(name, personTotalMap.getOrDefault(name, 0) + number);
//                }
//            }
        System.out.println(total);
//
//            // 创建Excel文件
//            Workbook workbook = new XSSFWorkbook();
//            Sheet sheet = workbook.createSheet("Summary");
//
//            // 创建标题行
//            Row headerRow = sheet.createRow(0);
//            Cell headerCell = headerRow.createCell(0);
//            headerCell.setCellValue("人名");
//            Cell headerCell2 = headerRow.createCell(1);
//            headerCell2.setCellValue("总和");
//
//            int rowNum = 1;
//            for (Map.Entry<String, Integer> entry : personTotalMap.entrySet()) {
//                if(entry.getValue() == 0) {
//                    continue;
//                }
//                Row dataRow = sheet.createRow(rowNum++);
//                Cell nameCell = dataRow.createCell(0);
//                nameCell.setCellValue(entry.getKey());
//                Cell totalCell = dataRow.createCell(1);
//                totalCell.setCellValue(entry.getValue());
//            }
//
//            // 写入Excel文件
//            try (FileOutputStream fileOut = new FileOutputStream(outputFilePath)) {
//                workbook.write(fileOut);
//            }
//
//            // 关闭工作簿
//            workbook.close();
//
    }
}
