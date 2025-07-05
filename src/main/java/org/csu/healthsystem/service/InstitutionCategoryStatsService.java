package org.csu.healthsystem.service;

import lombok.RequiredArgsConstructor;
import org.csu.healthsystem.pojo.DO.InstitutionCategoryStats;
import org.csu.healthsystem.util.BaseQueryDao;
import org.csu.healthsystem.util.InstitutionCategoryStatsDao;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * 医疗卫生机构类别统计查询服务
 */
@Service("InstitutionCategoryStatsService")
@RequiredArgsConstructor
public class InstitutionCategoryStatsService extends BaseQueryService2<InstitutionCategoryStats> {

    private final InstitutionCategoryStatsDao institutionCategoryStatsDao;

    /** 允许前端查询 / 排序 / 聚合的字段（驼峰） */
    @Override
    public Set<String> getAllowedColumns() {
        return Set.of(
                "year",
                "hospital",
                "communityHealth",
                "healthCenter",
                "cdc",
                "mch",
                "total"
        );
    }

    /** 返回对应 DAO */
    @Override
    public BaseQueryDao<InstitutionCategoryStats> getDao() {
        return institutionCategoryStatsDao;
    }
}
