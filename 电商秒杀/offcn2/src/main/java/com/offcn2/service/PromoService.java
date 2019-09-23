package com.offcn2.service;

import com.offcn2.service.UserModel.PromoModel;

public interface PromoService {
    //根据itemId获得即将进行的或正在进行的秒杀活动
    PromoModel getPromoModel(Integer itemId);
}
