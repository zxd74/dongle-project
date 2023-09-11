package com.dongle.store.system.dao.entity;

/**
 * @author Dongle
 * @desc table is `store_users`
 */
public class StoreUser {
    private String uuid;

    private String name;

    private Integer age;

    private Integer sex;

    private String borth;

    private String phone;

    private String email;

    /**
     * 家庭住址
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getBorth() {
        return borth;
    }

    public void setBorth(String borth) {
        this.borth = borth;
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