package org.csu.healthsystem.service;

import org.csu.healthsystem.pojo.VO.OutpatientCostStatisticsVO;
import org.csu.healthsystem.util.BaseQueryDao;
import org.csu.healthsystem.util.OutpatientCostsQueryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class OutpatientCostsQueryService extends BaseQueryService<OutpatientCostStatisticsVO> {
    @Autowired
    private OutpatientCostsQueryDao outpatientCostsQueryDao;

    @Override
    public Set<String> getAllowedColumns() {
        // 用驼峰风格，BaseQueryService会自动转下划线
        return Set.of("id", "hospitalId", "hospitalLevel", "totalFee", "medicineFee", "examFee", "treatmentFee");
    }

    @Override
    public BaseQueryDao<OutpatientCostStatisticsVO> getDao() {
        return outpatientCostsQueryDao;
    }
}