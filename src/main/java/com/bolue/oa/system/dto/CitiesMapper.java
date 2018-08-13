package com.bolue.oa.system.dto;

import java.util.List;

import com.bolue.oa.system.entity.Cities;

public interface CitiesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cities record);

    int insertSelective(Cities record);

    List<Cities> selectByPrimaryKey(Cities record);

    int updateByPrimaryKeySelective(Cities record);

    int updateByPrimaryKey(Cities record);
}