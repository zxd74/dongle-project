package com.dongle.book.collect.model;

/**
 * @author Dongle
 * @desc CP枚举
 * @since 2021/7/31 9:55
 */
public enum CpEnum {

    DANG_DANG("10000","当当","DangDangCollectService");

    private final String cpId;
    private final String name;
    private final String clazz;

    CpEnum(String cpId, String name, String clazz) {
        this.cpId = cpId;
        this.name = name;
        this.clazz = clazz;
    }

    public String getCpId() {
        return cpId;
    }

    public String getName() {
        return name;
    }

    public String getClazz() {
        return clazz;
    }

    public static CpEnum getCp(String cpId){
        for (CpEnum cp:CpEnum.values()){
            if (cp.getCpId().equals(cpId)){
                return cp;
            }
        }
        return null;
    }

}
