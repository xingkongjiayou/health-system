package org.csu.healthsystem.util;

import com.opencsv.CSVReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvUtil {
    public static List<Map<String, Object>> readCsv(InputStream is) throws Exception {
        List<Map<String, Object>> list = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String[] headers = reader.readNext();
            String[] line;
            while ((line = reader.readNext()) != null) {
                Map<String, Object> map = new HashMap<>();
                for (int i = 0; i < headers.length; i++) {
                    map.put(headers[i], line[i]);
                }
                list.add(map);
            }
        }
        return list;
    }
}
