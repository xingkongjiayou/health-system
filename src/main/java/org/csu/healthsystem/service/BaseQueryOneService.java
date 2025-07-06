package org.csu.healthsystem.service;

import lombok.extern.slf4j.Slf4j;
import org.csu.healthsystem.pojo.DO.Condition;
import org.csu.healthsystem.pojo.DO.PredictDO;
import org.csu.healthsystem.util.BaseQueryOneDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("BaseQueryOneService")
@Slf4j
public class BaseQueryOneService{
    @Autowired
    BaseQueryOneDao baseQueryOneDao;
    public Set<String> getAllowedColumns(){
        return Set.of(
                /* 通用时间 */
                "year",

                /* population_urban_rural */
                "ruralPopulation", "urbanPopulation",
                "malePopulation", "femalePopulation", "genderRatio",

                /* population_basic */
                "totalHouseholds", "urbanHouseholds", "countyHouseholds",
                "totalPopulation", "countyPopulation",

                /* population_resident */
                "residentPopulation", "urbanResident", "ruralResident",
                "urbanizationRate",

                /* population_natural_change */
                "births", "birthRate", "deaths", "deathRate",
                "naturalIncrease", "naturalIncreaseRate",

                /* health_bed_category */
                "hospital", "communityHealth", "healthCenter", "total",

                /* health_person_category */
                "healthPersonnel", "licensedPhysician",
                "nurse", "pharmacist",

                /* hospital_level_stats */
                "level3Total", "level3a", "level3b", "level3Ungraded",
                "level2Total", "level2a", "level2b", "level2Ungraded",
                "level1Ungraded",

                /* hospital_service_statistics */
                "typeName", "outpatientVisits", "emergencyVisits",
                "referrals", "transferOut",
                "bedUtilizationRate", "avgLengthOfStay"
        );
    }
    public BaseQueryOneDao getDao(){
        return baseQueryOneDao;
    }

    public List<Double> query(PredictDO req) {
        Map<String,Object> params = buildFilterMap(req.getFilters());
        String toPredict=req.getToPredict();
        if(getAllowedColumns().contains(toPredict)) {
            return getDao().selectOneByCondition(params, camelToSnakeTrue(toPredict),
                    getDao().selectTableByColumn(camelToSnakeTrue(toPredict)));
        }
        return null;
    }

    public List<Double> query(String req,Map<String,Condition> filters) {
        Map<String,Object> params = buildFilterMap(filters);
        if(getAllowedColumns().contains(req)) {
            return getDao().selectOneByCondition(params, camelToSnakeTrue(req),
                    getDao().selectTableByColumn(camelToSnakeTrue(req)));
        }
        return null;
    }

    private Map<String,Object> buildFilterMap(Map<String, Condition> filters){
        Map<String,Object> m = new HashMap<>();
        if(filters == null) return m;

        filters.forEach((field, cond) -> {
            String col = camelToSnake(field);
            if(!getAllowedColumns().contains(col) || cond==null) return;

            if(cond.getEq()  != null) m.put(col + "Eq",  cond.getEq());
            if (cond.getNotEq()  != null) m.put(col + "NotEq",  cond.getNotEq());
            if(cond.getGt()  != null) m.put(col + "Gt",  cond.getGt());
            if(cond.getGte() != null) m.put(col + "Gte", cond.getGte());
            if(cond.getLt()  != null) m.put(col + "Lt",  cond.getLt());
            if(cond.getLte() != null) m.put(col + "Lte", cond.getLte());
            if (cond.getIn() != null && !cond.getIn().isEmpty()) {
                m.put(col + "In", cond.getIn());
            }
            if (cond.getNotIn()  != null && !cond.getNotIn().isEmpty())
                m.put(col + "NotIn",  cond.getNotIn());

            if (cond.getLike()    != null) m.put(col + "Like",    cond.getLike());
            if (cond.getNotLike() != null) m.put(col + "NotLike", cond.getNotLike());
        });
        return m;
    }
    public String camelToSnake(String s) {
        return s/*.replaceAll("([A-Z])", "_$1").toLowerCase()*/;
    }
    public String camelToSnakeTrue(String s) {
        return s.replaceAll("([A-Z])", "_$1").toLowerCase();
    }

}
