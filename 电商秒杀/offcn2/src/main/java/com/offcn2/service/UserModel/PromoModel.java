package com.offcn2.service.UserModel;

import org.joda.time.DateTime;

import java.math.BigDecimal;

public class PromoModel {
    private Integer id;

    //代表秒杀活动  1表示还未开始 2表示正在进行 3表示已结束
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    //秒杀活动名称
    private String PromoName;

    //秒杀活动开始时间
    private DateTime startTime;

    //秒杀活动的适用商品
    private Integer itemId;

    //秒杀活动的商品价格
    private BigDecimal promoItemPrice;

    //秒杀活动结束时间
    private DateTime endTime;

    public DateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(DateTime endTime) {
        this.endTime = endTime;
    }

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

    public DateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(DateTime startTime) {
        this.startTime = startTime;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public BigDecimal getPromoItemPrice() {
        return promoItemPrice;
    }

    public void setPromoItemPrice(BigDecimal promoItemPrice) {
        this.promoItemPrice = promoItemPrice;
    }

    @Override
    public String toString() {
        return "PromoModel{" +
                "id=" + id +
                ", status=" + status +
                ", PromoName='" + PromoName + '\'' +
                ", startTime=" + startTime +
                ", itemId=" + itemId +
                ", promoItemPrice=" + promoItemPrice +
                ", endTime=" + endTime +
                '}';
    }
}
