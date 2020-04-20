package com.jlyang.hadoop.util;

import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class ExcelParser {

    public static String[] parse(InputStream inputStream) throws IOException {
        Workbook workbook = WorkbookFactory.create(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.rowIterator();
        List<String> res = new ArrayList<>();
        StringBuilder builder;
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            builder = new StringBuilder();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                switch (cell.getCellType()) {
                    case NUMERIC:
                        builder.append(cell.getNumericCellValue()).append(" ");
                        break;
                    case STRING:
                        builder.append(cell.getStringCellValue()).append(" ");
                        break;
                    case BOOLEAN:
                        builder.append(cell.getBooleanCellValue()).append(" ");
                        break;
                }
            }
            res.add(builder.toString().trim());
        }
        return res.toArray(new String[0]);
    }
}
