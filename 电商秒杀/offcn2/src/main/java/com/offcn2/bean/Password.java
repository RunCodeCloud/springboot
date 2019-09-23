package com.offcn2.bean;

public class Password {

    private Integer id;
    private String password;
    private Integer user_id;

    public  Password(){

    }

    public Password(Integer id, String password, Integer user_id) {
        this.id = id;
        this.password = password;
        this.user_id = user_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "Password{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", user_id=" + user_id +
                '}';
    }
}

