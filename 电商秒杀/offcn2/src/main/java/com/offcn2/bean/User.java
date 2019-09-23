package com.offcn2.bean;

public class User {
    private Integer id;
    private String name;
    private int gender;
    private Integer age;
    private String telphone;
    private String register_mode;
    private String third_party_id;

    public User(){

    }

    public User(Integer id, String name, int gender, Integer age, String telphone, String register_mode, String third_party_id) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.telphone = telphone;
        this.register_mode = register_mode;
        this.third_party_id = third_party_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getRegister_mode() {
        return register_mode;
    }

    public void setRegister_mode(String register_mode) {
        this.register_mode = register_mode;
    }

    public String getThird_party_id() {
        return third_party_id;
    }

    public void setThird_party_id(String third_party_id) {
        this.third_party_id = third_party_id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", age=" + age +
                ", telphone='" + telphone + '\'' +
                ", register_mode='" + register_mode + '\'' +
                ", third_party_id='" + third_party_id + '\'' +
                '}';
    }
}
