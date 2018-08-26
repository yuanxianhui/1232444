package com.bolue.oa.entity.enumer;

public class SysEnumerateDetailedKey {
    private String eunmCode;

    private String code;

    public String getEunmCode() {
        return eunmCode;
    }

    public void setEunmCode(String eunmCode) {
        this.eunmCode = eunmCode == null ? null : eunmCode.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }
}