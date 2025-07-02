package org.csu.healthsystem.service;


import org.csu.healthsystem.pojo.DO.DataTag;
import org.csu.healthsystem.pojo.VO.DataTagAssignRequestVO;
import org.csu.healthsystem.pojo.VO.DataTagCreateRequestVO;
import org.csu.healthsystem.util.DataTagDao;
import org.csu.healthsystem.pojo.VO.DataTagVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class DataTagService {
    @Autowired
    private DataTagDao dataTagDao;

    // 数据类型与表名映射，支持所有可打标签的数据表
    private static final Map<String, String> DATA_TYPE_TABLE = Map.of(
        "population", "population",
        "institution", "institution_category_stats",
        "personnel", "health_person_category",
        "bed", "health_bed_category",
        "service", "hospital_service_statistics",
        "cost", "outpatient_costs"
    );

    // 允许打标签的数据类型集合
    private static final Set<String> SUPPORTED_TYPES = DATA_TYPE_TABLE.keySet();

    public List<DataTagVO> getAllTags() {
        return dataTagDao.getAllTags();
    }
    public void createTag(DataTagCreateRequestVO vo) {
        DataTag tag = new DataTag();
        BeanUtils.copyProperties(vo, tag);
        dataTagDao.insertTag(tag);
    }

    public void assignTags(DataTagAssignRequestVO vo) {
        String table = DATA_TYPE_TABLE.get(vo.getDataType());
        if (table == null) {
            throw new RuntimeException("不支持的数据类型: " + vo.getDataType());
        }
        if (vo.getDataIds() == null || vo.getDataIds().isEmpty() || vo.getTagIds() == null || vo.getTagIds().isEmpty()) {
            throw new RuntimeException("数据ID和标签ID不能为空");
        }
        dataTagDao.batchAssignTags(table, vo.getDataIds(), vo.getTagIds());
    }
}
