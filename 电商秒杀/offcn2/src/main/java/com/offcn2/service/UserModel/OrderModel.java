package com.offcn2.service.UserModel;

import java.math.BigDecimal;

public class OrderModel {
    //订单号
    private String id;
    //商品id
    private Integer itemId;
    //用户id
    private Integer userId;
    //购买商品的单价   promoId若非空则为秒杀价格下单
    private BigDecimal itemPrice;
    //购买金额   订单的价格  ==总价
    private BigDecimal orderPrice;
    //购买数量
    private Integer amount;

    //promoId若非空则为秒杀方式下单
    private Integer promoId;

    public Integer getPromoId() {
        return promoId;
    }

    public void setPromoId(Integer promoId) {
        this.promoId = promoId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "OrderModel{" +
                "id='" + id + '\'' +
                ", itemId=" + itemId +
                ", userId=" + userId +
                ", itemPrice=" + itemPrice +
                ", orderPrice=" + orderPrice +
                ", amount=" + amount +
                ", promoId=" + promoId +
                '}';
    }
}
