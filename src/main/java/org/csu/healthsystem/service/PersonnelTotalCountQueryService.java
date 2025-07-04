package org.csu.healthsystem.service;

import org.csu.healthsystem.pojo.DO.TotalHealthPerson;
import org.csu.healthsystem.util.BaseQueryDao;
import org.csu.healthsystem.util.PersonnelTotalCountQueryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PersonnelTotalCountQueryService extends BaseQueryService<TotalHealthPerson> {
    @Autowired
    private PersonnelTotalCountQueryDao personnelTotalCountQueryDao;

    @Override
    public Set<String> getAllowedColumns() {
        // 用驼峰风格，BaseQueryService会自动转下划线
        return Set.of("id", "year", "totalCount");
    }

    @Override
    public BaseQueryDao<TotalHealthPerson> getDao() {
        return personnelTotalCountQueryDao;
    }
}
