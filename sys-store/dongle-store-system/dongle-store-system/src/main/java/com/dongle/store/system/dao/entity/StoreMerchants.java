package com.dongle.store.system.dao.entity;

/**
 * @author Dongle
 * @desc table is `store_merchants`
 */
public class StoreMerchants {
    private String uuid;

    private String name;

    private String phone;

    private String email;

    /**
     * 商家地址
     */
    private String address;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}