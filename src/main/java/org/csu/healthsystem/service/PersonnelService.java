package org.csu.healthsystem.service;

import lombok.extern.slf4j.Slf4j;
import org.csu.healthsystem.pojo.DO.HealthPersonCategory;
import org.csu.healthsystem.pojo.DO.TotalHealthPerson;
import org.csu.healthsystem.pojo.VO.PersonnelStructureAnalysisVO;
import org.csu.healthsystem.util.HealthPersonCategoryDao;
import org.csu.healthsystem.util.TotalHealthPersonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service("PersonnelService")
@Slf4j
public class PersonnelService {
    @Autowired
    private HealthPersonCategoryDao healthPersonCategoryDao;
    @Autowired
    private TotalHealthPersonDao totalHealthPersonDao;

    public List<TotalHealthPerson> getAllTotalHealthPerson() {
        return totalHealthPersonDao.getAll();
    }
    public List<HealthPersonCategory> getAllHealthPersonCategory() {
        return healthPersonCategoryDao.getAll();
    }
    public PersonnelStructureAnalysisVO getPersonnelStructureAnalysis(Integer year) {
        List<HealthPersonCategory> categories = healthPersonCategoryDao.getAll();
        if (categories == null || categories.isEmpty()) return null;

        HealthPersonCategory target = null;
        if (year != null) {
            target = healthPersonCategoryDao.getByYear(year);
            if (target == null) {
                // 若该年无数据，默认取最新
                target = categories.stream().max(Comparator.comparing(HealthPersonCategory::getYear)).orElse(null);
            }
        } else {
            target = categories.stream().max(Comparator.comparing(HealthPersonCategory::getYear)).orElse(null);
        }
        if (target == null) return null;

        double total = target.getTotal() != null ? target.getTotal() : 0.0;
        double physician = target.getLicensedPhysician() != null ? target.getLicensedPhysician() : 0.0;
        double nurse = target.getNurse() != null ? target.getNurse() : 0.0;
        double pharmacist = target.getPharmacist() != null ? target.getPharmacist() : 0.0;
        double other = total - physician - nurse - pharmacist;

        // 结构占比
        PersonnelStructureAnalysisVO.Structure structure = new PersonnelStructureAnalysisVO.Structure();
        structure.setPhysicianRatio(total > 0 ? round2(physician / total * 100) : 0);
        structure.setNurseRatio(total > 0 ? round2(nurse / total * 100) : 0);
        structure.setPharmacistRatio(total > 0 ? round2(pharmacist / total * 100) : 0);
        structure.setOtherRatio(total > 0 ? round2(other / total * 100) : 0);

        // 医护比
        String doctorNurseRatio = (physician > 0 && nurse > 0) ? "1:" + round2(nurse / physician) : "-";

        // 年增长
        categories = categories.stream()
                .sorted(Comparator.comparing(HealthPersonCategory::getYear))
                .collect(Collectors.toList());
        List<PersonnelStructureAnalysisVO.YearlyGrowth> yearlyGrowthList = new ArrayList<>();
        for (int i = 0; i < categories.size(); i++) {
            HealthPersonCategory c = categories.get(i);
            PersonnelStructureAnalysisVO.YearlyGrowth yg = new PersonnelStructureAnalysisVO.YearlyGrowth();
            yg.setYear(c.getYear());
            yg.setTotalPersonnel(c.getTotal());
            if (i > 0) {
                double prevTotal = categories.get(i - 1).getTotal() != null ? categories.get(i - 1).getTotal() : 0.0;
                yg.setGrowthRate(prevTotal > 0 ? round2((c.getTotal() - prevTotal) / prevTotal * 100) : null);
            } else {
                yg.setGrowthRate(null);
            }
            yearlyGrowthList.add(yg);
        }

        // 分析建议
        PersonnelStructureAnalysisVO.Analysis analysis = new PersonnelStructureAnalysisVO.Analysis();
        boolean nurseShortage = (physician > 0 && nurse / physician < 1.4);
        analysis.setNurseShortage(nurseShortage);
        analysis.setRecommendedRatio("1:1.4");
        List<String> suggestions = new ArrayList<>();
        if (nurseShortage) {
            suggestions.add("加强护理人员培养");
            suggestions.add("提高护理人员待遇");
        } else {
            suggestions.add("继续保持医护结构合理");
        }
        analysis.setImprovementSuggestions(suggestions);

        // 组装VO
        PersonnelStructureAnalysisVO vo = new PersonnelStructureAnalysisVO();
        vo.setCurrentYear(target.getYear());
        vo.setStructure(structure);
        vo.setDoctorNurseRatio(doctorNurseRatio);
        vo.setYearlyGrowth(yearlyGrowthList);
        vo.setAnalysis(analysis);
        return vo;
    }

    private double round2(double v) {
        return Math.round(v * 100.0) / 100.0;
    }
}