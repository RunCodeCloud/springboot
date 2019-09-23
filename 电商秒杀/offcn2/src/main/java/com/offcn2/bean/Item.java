package com.offcn2.bean;

public class Item {
    private Integer id;
    private String title;
    private double price;
    private String description;
    private Integer sales;
    private String img_url;

    public Item(){

    }

    public Item(Integer id, String title, double price, String description, Integer sales, String img_url) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.description = description;
        this.sales = sales;
        this.img_url = img_url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
