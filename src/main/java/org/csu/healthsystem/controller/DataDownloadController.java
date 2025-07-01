package org.csu.healthsystem.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
@RequestMapping("/api/data")
public class DataDownloadController {

    @GetMapping("/download/{exportId}")
    public void downloadExport(@PathVariable String exportId, HttpServletResponse response) {
        try {
            // 假设所有导出文件都在 export 目录，自动识别后缀
            File dir = new File("export");
            File[] files = dir.listFiles((d, name) -> name.startsWith(exportId));
            if (files == null || files.length == 0) {
                response.setStatus(404);
                response.getWriter().write("文件不存在");
                return;
            }
            File file = files[0];
            String fileName = file.getName();
            String contentType = "application/octet-stream";
            if (fileName.endsWith(".xlsx")) contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            if (fileName.endsWith(".csv")) contentType = "text/csv";
            if (fileName.endsWith(".json")) contentType = "application/json";
            response.setContentType(contentType);
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            try (java.io.FileInputStream fis = new java.io.FileInputStream(file);
                 java.io.OutputStream os = response.getOutputStream()) {
                byte[] buffer = new byte[4096];
                int len;
                while ((len = fis.read(buffer)) != -1) {
                    os.write(buffer, 0, len);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500);
        }
    }
}
