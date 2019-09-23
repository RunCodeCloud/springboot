package com.offcn2.service;

import com.offcn2.error.BusinessException;
import com.offcn2.service.UserModel.OrderModel;

public interface OrderService {
    //1.通过前端url上传秒杀id，然后下单接口内校检对应id是否属于对应商品且活动已开始
    //2.直接在下单接口内判断商品是否存在秒杀活动，若存在则以秒杀下单
    OrderModel createOrder(Integer itemId,Integer userId,Integer amount,Integer promoId) throws BusinessException;
}
