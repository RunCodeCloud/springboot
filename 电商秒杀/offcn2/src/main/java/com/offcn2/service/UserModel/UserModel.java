package com.offcn2.service.UserModel;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class UserModel {
    private Integer id;
    @NotBlank(message = "用户名不能为空")
    //NotBlank不能为null和""
    private String name;
    @NotNull(message = "性别不能不填")
    private Integer gender;
    @NotNull(message = "年龄不能不填")
    @Min(value = 0,message = "年龄必须大于0岁")
    @Max(value = 150,message = "年龄必须小于150岁")
    private Integer age;
    @NotBlank(message = "手机号不能为空")
    private String telphone;
    private String register_mode;
    private String third_party_id;
    @NotBlank(message = "密码不能为空")
    private String password;

    public UserModel(){

    }

    public UserModel(Integer id, String name, Integer gender, Integer age, String telphone, String register_mode, String third_party_id, String password) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.telphone = telphone;
        this.register_mode = register_mode;
        this.third_party_id = third_party_id;
        this.password = password;
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

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", age=" + age +
                ", telphone='" + telphone + '\'' +
                ", register_mode='" + register_mode + '\'' +
                ", third_party_id='" + third_party_id + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
