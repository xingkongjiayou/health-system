package org.csu.healthsystem.service;

import lombok.extern.slf4j.Slf4j;
import org.csu.healthsystem.pojo.VO.InpatientCostStatisticsVO;
import org.csu.healthsystem.util.InpatientCostsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("InCostStatisticsService")
public class InCostStatisticsService {

    @Autowired
    private InpatientCostsDao inpatientCostsDao;

    public List<InpatientCostStatisticsVO> getInpatientCostStatistics() {
        return inpatientCostsDao.getAllInpatientCostStatistics();
    }
}