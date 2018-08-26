package com.bolue.oa.entity.user;

import lombok.Data;

@Data
public class SysUserDto {
    private String userCode;

    private String userName;

    private String accountCode;

    private String userSex;

    private String phoneNumber;
    
    private String userCardId;

    private String provinceCode;

    private String cityCode;

    private String areaCode;

    private String address;

    private String staffFlg;

    private String entryTime;

    private String officialTime;

    private String dimissionTime;

    private String validateFlag;

    private String updateTime;

    private String updateUser;

}
