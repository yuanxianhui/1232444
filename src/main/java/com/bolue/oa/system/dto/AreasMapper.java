package com.bolue.oa.system.dto;

import java.util.List;

import com.bolue.oa.system.entity.Areas;

public interface AreasMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Areas record);

    int insertSelective(Areas record);

    List<Areas> selectByPrimaryKey(Areas record);

    int updateByPrimaryKeySelective(Areas record);

    int updateByPrimaryKey(Areas record);
}