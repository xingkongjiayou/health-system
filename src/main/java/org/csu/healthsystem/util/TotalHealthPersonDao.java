package org.csu.healthsystem.util;

import org.apache.ibatis.annotations.Mapper;
import org.csu.healthsystem.pojo.DO.TotalHealthPerson;

import java.util.List;

@Mapper
public interface TotalHealthPersonDao {
    List<TotalHealthPerson> getAll();
}