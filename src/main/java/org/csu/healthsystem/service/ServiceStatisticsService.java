package org.csu.healthsystem.service;

import lombok.extern.slf4j.Slf4j;
import org.csu.healthsystem.pojo.DO.HospitalServiceStatistics;
import org.csu.healthsystem.pojo.VO.ServiceQualityAnalysisVO;
import org.csu.healthsystem.util.HospitalServiceStatisticsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service("ServiceStatisticsService")
public class ServiceStatisticsService {
    @Autowired
    private HospitalServiceStatisticsDao hospitalServiceStatisticsDao;

    public List<HospitalServiceStatistics> getAllHospitalServiceStatistics() {
        return hospitalServiceStatisticsDao.getAll();
    }
    public ServiceQualityAnalysisVO getServiceQualityAnalysis(String hospitalType, String analysisType) {
        List<HospitalServiceStatistics> statList = hospitalServiceStatisticsDao.getAll();
        if (statList == null || statList.isEmpty()) return null;

        // 1. 找到目标医院类型
        HospitalServiceStatistics stat = statList.stream()
                .filter(s -> hospitalType.equals(s.getTypeName()))
                .findFirst().orElse(null);
        if (stat == null) return null;

        // 2. 计算服务指标
        long totalOut = stat.getOutpatientVisits() != null ? stat.getOutpatientVisits() : 0L;
        long totalEmg = stat.getEmergencyVisits() != null ? stat.getEmergencyVisits() : 0L;
        long referrals = stat.getReferrals() != null ? stat.getReferrals() : 0L;
        long transferOut = stat.getTransferOut() != null ? stat.getTransferOut() : 0L;
        double bedUtil = stat.getBedUtilizationRate() != null ? stat.getBedUtilizationRate() : 0.0;
        double avgStay = stat.getAvgLengthOfStay() != null ? stat.getAvgLengthOfStay() : 0.0;
        long totalVisits = totalOut + totalEmg;

        double referralRate = totalVisits > 0 ? round2(referrals * 100.0 / totalVisits) : 0.0;
        double transferRate = totalVisits > 0 ? round2(transferOut * 100.0 / totalVisits) : 0.0;

        ServiceQualityAnalysisVO.ServiceMetrics metrics = new ServiceQualityAnalysisVO.ServiceMetrics();
        metrics.setTotalOutpatientVisits(totalOut);
        metrics.setTotalEmergencyVisits(totalEmg);
        metrics.setAverageBedUtilization(bedUtil);
        metrics.setAverageStayLength(avgStay);
        metrics.setReferralRate(referralRate);
        metrics.setTransferRate(transferRate);

        // 3. comparison 排名
        List<HospitalServiceStatistics> sorted = statList.stream()
                .sorted(Comparator.comparing(HospitalServiceStatistics::getBedUtilizationRate).reversed())
                .collect(Collectors.toList());
        int ranking = 1;
        for (int i = 0; i < sorted.size(); i++) {
            if (hospitalType.equals(sorted.get(i).getTypeName())) {
                ranking = i + 1;
                break;
            }
        }
        double efficiency = bedUtil; // 这里用床位使用率做效率分数

        ServiceQualityAnalysisVO.Comparison cmp = new ServiceQualityAnalysisVO.Comparison();
        cmp.setHospitalType(hospitalType);
        cmp.setEfficiency(efficiency);
        cmp.setRanking(ranking);

        // 4. 质量指标（可根据阈值自动判定）
        ServiceQualityAnalysisVO.QualityIndicators indicators = new ServiceQualityAnalysisVO.QualityIndicators();
        indicators.setServiceEfficiency(bedUtil > 85 ? "高" : (bedUtil > 70 ? "中" : "低"));
        indicators.setResourceUtilization(bedUtil > 85 ? "优秀" : (bedUtil > 70 ? "良好" : "一般"));
        indicators.setPatientFlow(avgStay < 9 ? "良好" : "一般");

        // 5. 建议
        List<String> recs = new ArrayList<>();
        if (bedUtil > 85) recs.add("继续保持高效服务水平");
        else recs.add("提升床位使用率");
        if (avgStay > 10) recs.add("优化急诊流程，缩短住院日");
        else recs.add("优化急诊流程");

        // 6. 组装VO
        ServiceQualityAnalysisVO vo = new ServiceQualityAnalysisVO();
        vo.setServiceMetrics(metrics);
        vo.setQualityIndicators(indicators);
        vo.setComparison(List.of(cmp));
        vo.setRecommendations(recs);
        return vo;
    }
    private double round2(double v) {
        return Math.round(v * 100.0) / 100.0;
    }
}