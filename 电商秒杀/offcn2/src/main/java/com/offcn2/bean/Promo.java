package com.offcn2.bean;

import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

public class Promo {
    private Integer id;

    //秒杀活动名称
    private String PromoName;

    //秒杀活动开始时间
    private Timestamp startTime;

    private Timestamp endTime;

    //秒杀活动的适用商品
    private Integer itemId;

    //秒杀活动的商品价格
    private Double promoItemPrice;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPromoName() {
        return PromoName;
    }

    public void setPromoName(String promoName) {
        PromoName = promoName;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Double getPromoItemPrice() {
        return promoItemPrice;
    }

    public void setPromoItemPrice(Double promoItemPrice) {
        this.promoItemPrice = promoItemPrice;
    }

    @Override
    public String toString() {
        return "Promo{" +
                "id=" + id +
                ", PromoName='" + PromoName + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", itemId=" + itemId +
                ", promoItemPrice=" + promoItemPrice +
                '}';
    }
}
