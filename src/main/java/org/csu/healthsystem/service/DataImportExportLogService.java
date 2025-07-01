package org.csu.healthsystem.service;

import org.csu.healthsystem.util.DataImportExportLogDao;
import org.csu.healthsystem.pojo.VO.DataImportExportLogVO;
import org.csu.healthsystem.pojo.VO.PageResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataImportExportLogService {

    @Autowired
    private DataImportExportLogDao logDao;

    public PageResultVO<DataImportExportLogVO> getLogs(Integer page, Integer size, String operation, String status) {
        int offset = (page != null && page > 0) ? (page - 1) * size : 0;
        int pageSize = (size != null && size > 0) ? size : 10;
        List<DataImportExportLogVO> records = logDao.selectLogs(operation, status, offset, pageSize);
        long total = logDao.countLogs(operation, status);

        PageResultVO<DataImportExportLogVO> result = new PageResultVO<>();
        result.setTotal(total);
        result.setRecords(records);
        return result;
    }
}
