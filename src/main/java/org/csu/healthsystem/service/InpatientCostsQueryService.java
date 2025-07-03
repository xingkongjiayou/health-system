package org.csu.healthsystem.service;

import org.csu.healthsystem.pojo.VO.InpatientCostStatisticsVO;
import org.csu.healthsystem.util.BaseQueryDao;
import org.csu.healthsystem.util.InpatientCostsQueryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class InpatientCostsQueryService extends BaseQueryService<InpatientCostStatisticsVO> {
    @Autowired
    private InpatientCostsQueryDao inpatientCostsQueryDao;

    @Override
    public Set<String> getAllowedColumns() {
        return Set.of("id", "hospitalId", "hospitalLevel", "totalFee", "bedFee", "medicineFee", "treatmentFee");
    }

    @Override
    public BaseQueryDao<InpatientCostStatisticsVO> getDao() {
        return inpatientCostsQueryDao;
    }
}