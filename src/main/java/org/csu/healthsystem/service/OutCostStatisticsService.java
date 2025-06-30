package org.csu.healthsystem.service;

import lombok.extern.slf4j.Slf4j;
import org.csu.healthsystem.pojo.VO.OutpatientCostStatisticsVO;
import org.csu.healthsystem.util.OutpatientCostsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("OutCostStatisticsService")
public class OutCostStatisticsService {

    @Autowired
    private OutpatientCostsDao outpatientCostsDao;

    public List<OutpatientCostStatisticsVO> getOutpatientCostStatistics() {
        return outpatientCostsDao.getAllOutpatientCostStatistics();
    }
}