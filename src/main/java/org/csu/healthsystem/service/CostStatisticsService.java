package org.csu.healthsystem.service;

import lombok.extern.slf4j.Slf4j;
import org.csu.healthsystem.pojo.VO.CostStructureComparisonAnalysisVO;
import org.csu.healthsystem.pojo.VO.CostStructureComparisonDataVO;
import org.csu.healthsystem.pojo.VO.CostStructureComparisonVO;
import org.csu.healthsystem.util.CostStatisticsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service("CostStatisticsService")
public class CostStatisticsService {

    @Autowired
    private CostStatisticsDao costStatisticsDao;

    public CostStructureComparisonVO getCostStructureComparison(String compareType, String costType) {
        List<CostStructureComparisonDataVO> data;
        if ("hospital_level".equals(compareType)) {
            if ("outpatient".equals(costType)) {
                data = costStatisticsDao.getOutpatientCostStructureComparison();
            } else if ("inpatient".equals(costType)) {
                data = costStatisticsDao.getInpatientCostStructureComparison();
            } else {
                throw new IllegalArgumentException("不支持的costType");
            }
        } else {
            throw new IllegalArgumentException("不支持的compareType");
        }

        // 排序并排名
        data.sort(Comparator.comparingDouble(CostStructureComparisonDataVO::getTotalFee).reversed());
        for (int i = 0; i < data.size(); i++) {
            data.get(i).setRanking(i + 1);
        }

        // 计算分析
        double avg = data.stream().mapToDouble(CostStructureComparisonDataVO::getTotalFee).average().orElse(0);
        double minRatio = data.stream().mapToDouble(CostStructureComparisonDataVO::getMedicineRatio).min().orElse(0);
        double maxRatio = data.stream().mapToDouble(CostStructureComparisonDataVO::getMedicineRatio).max().orElse(0);
        String minLevel = data.stream().min(Comparator.comparingDouble(CostStructureComparisonDataVO::getTotalFee)).map(CostStructureComparisonDataVO::getHospitalLevel).orElse("");
        String maxLevel = data.stream().max(Comparator.comparingDouble(CostStructureComparisonDataVO::getTotalFee)).map(CostStructureComparisonDataVO::getHospitalLevel).orElse("");
        String trend = "随医院等级递减";
        List<String> recs = new ArrayList<>();
        if (maxRatio > 40) recs.add("控制药费占比");
        if (data.stream().anyMatch(d -> d.getExamRatio() != null && d.getExamRatio() > 30)) recs.add("提高检查效率");

        CostStructureComparisonAnalysisVO analysis = new CostStructureComparisonAnalysisVO();
        analysis.setAverageTotalFee(avg);
        analysis.setMedicineRatioRange(String.format("%.1f%%-%.1f%%", minRatio, maxRatio));
        analysis.setHighestCostLevel(maxLevel);
        analysis.setLowestCostLevel(minLevel);
        analysis.setCostTrend(trend);
        analysis.setRecommendations(recs);

        CostStructureComparisonVO vo = new CostStructureComparisonVO();
        vo.setComparisonData(data);
        vo.setAnalysis(analysis);
        return vo;
    }

}
