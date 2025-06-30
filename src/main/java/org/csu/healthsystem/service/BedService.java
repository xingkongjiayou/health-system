package org.csu.healthsystem.service;

import lombok.extern.slf4j.Slf4j;
import org.csu.healthsystem.pojo.DO.HealthBedCategory;
import org.csu.healthsystem.pojo.DO.HospitalServiceStatistics;
import org.csu.healthsystem.pojo.DO.TotalBedCount;
import org.csu.healthsystem.pojo.VO.BedUtilizationAnalysisVO;
import org.csu.healthsystem.util.HealthBedCategoryDao;
import org.csu.healthsystem.util.HospitalServiceStatisticsDao;
import org.csu.healthsystem.util.TotalBedCountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service("BedService")
@Slf4j
public class BedService {
    @Autowired
    private HealthBedCategoryDao healthBedCategoryDao;

    public List<HealthBedCategory> getAllHealthBedCategory() {
        return healthBedCategoryDao.getAll();
    }

    @Autowired
    private TotalBedCountDao totalBedCountDao;

    public List<TotalBedCount> getAllTotalBedCount() {
        return totalBedCountDao.getAll();
    }
    @Autowired
    private HospitalServiceStatisticsDao hospitalServiceStatisticsDao;

    public BedUtilizationAnalysisVO getBedUtilizationAnalysis(Integer year, String hospitalType) {
        // 查询床位数和服务统计
        List<HealthBedCategory> bedList = healthBedCategoryDao.getAll();
        List<HospitalServiceStatistics> statList = hospitalServiceStatisticsDao.getAll();

        // 只按类型过滤
        HospitalServiceStatistics stat = statList.stream()
                .filter(s -> StringUtils.isEmpty(hospitalType) || hospitalType.equals(s.getTypeName()))
                .findFirst().orElse(null);

        // 床位数按年份过滤
        HealthBedCategory bed = bedList.stream()
                .filter(b -> b.getYear().equals(year))
                .findFirst().orElse(null);

        BedUtilizationAnalysisVO.UtilizationData data = new BedUtilizationAnalysisVO.UtilizationData();
        if (stat != null && bed != null) {
            data.setHospitalType(stat.getTypeName());
            data.setBedUtilizationRate(stat.getBedUtilizationRate());
            data.setAvgLengthOfStay(stat.getAvgLengthOfStay());
            data.setTotalBeds(bed.getHospital());
            data.setOccupiedBeds(bed.getHospital() * stat.getBedUtilizationRate() / 100.0);
        }

        // 统计分析：全表分析
        double avgUtil = statList.stream().mapToDouble(HospitalServiceStatistics::getBedUtilizationRate).average().orElse(0);
        String highest = statList.stream().max(Comparator.comparingDouble(HospitalServiceStatistics::getBedUtilizationRate))
                .map(HospitalServiceStatistics::getTypeName).orElse("");
        String lowest = statList.stream().min(Comparator.comparingDouble(HospitalServiceStatistics::getBedUtilizationRate))
                .map(HospitalServiceStatistics::getTypeName).orElse("");

        BedUtilizationAnalysisVO.Analysis analysis = new BedUtilizationAnalysisVO.Analysis();
        analysis.setAverageUtilization(Math.round(avgUtil * 100.0) / 100.0);
        analysis.setHighestUtilization(highest);
        analysis.setLowestUtilization(lowest);
        analysis.setRecommendations(List.of("优化床位配置", "提高床位周转率"));

        BedUtilizationAnalysisVO vo = new BedUtilizationAnalysisVO();
        vo.setUtilizationData(data != null ? List.of(data) : Collections.emptyList());
        vo.setAnalysis(analysis);
        return vo;
    }
}
