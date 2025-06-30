package org.csu.healthsystem.util;

import org.apache.poi.ss.usermodel.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtil {
    public static List<Map<String, Object>> readExcel(InputStream is) throws Exception {
        List<Map<String, Object>> list = new ArrayList<>();
        Workbook workbook = WorkbookFactory.create(is);
        Sheet sheet = workbook.getSheetAt(0);
        Row header = sheet.getRow(0);
        int colCount = header.getPhysicalNumberOfCells();
        List<String> headers = new ArrayList<>();
        for (int i = 0; i < colCount; i++) {
            headers.add(header.getCell(i).getStringCellValue());
        }
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;
            Map<String, Object> map = new HashMap<>();
            for (int j = 0; j < colCount; j++) {
                map.put(headers.get(j), getCellValue(row.getCell(j)));
            }
            list.add(map);
        }
        return list;
    }
    private static Object getCellValue(Cell cell) {
        if (cell == null) return null;
        switch (cell.getCellType()) {
            case STRING: return cell.getStringCellValue();
            case NUMERIC: return cell.getNumericCellValue();
            case BOOLEAN: return cell.getBooleanCellValue();
            default: return null;
        }
    }
}
