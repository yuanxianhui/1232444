package com.bolue.oa.mapper.enumer;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bolue.oa.entity.enumer.SysEnumerateDetailed;
import com.bolue.oa.entity.enumer.SysEnumerateDetailedKey;

public interface SysEnumerateDetailedMapper {
    int deleteByPrimaryKey(SysEnumerateDetailedKey key);

    int insert(SysEnumerateDetailed record);

    int insertSelective(SysEnumerateDetailed record);

    SysEnumerateDetailed selectByPrimaryKey(SysEnumerateDetailedKey key);

    int updateByPrimaryKeySelective(SysEnumerateDetailed record);

    int updateByPrimaryKey(SysEnumerateDetailed record);
    
    /**
     * 根据枚举类型编号检索枚举明细信息集合
     * @param enumCode
     * @return
     */
    List<SysEnumerateDetailed> selectEnumsByEnumCode(@Param("enumCode")String enumCode);
}