package com.offcn2.service.Impl;

import com.offcn2.bean.Promo;
import com.offcn2.mapper.PromoMapper;
import com.offcn2.service.PromoService;
import com.offcn2.service.UserModel.PromoModel;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class PromoServiceImpl implements PromoService {

    @Resource
    PromoMapper promoMapper;

    //----------------------------------------------------------------------
    @Override
    public PromoModel getPromoModel(Integer itemId) {

        //获取对应商品的秒杀活动信息
        Promo promo = promoMapper.selectByItemId(itemId);

        //将dataobject-->转化为model领域模型
        PromoModel model = convertFromPromo(promo);
        if(model==null){
            return null;
        }
        //判断当前时间秒杀活动即将开始，或正在进行
        if(model.getStartTime().isAfterNow()){
            model.setStatus(1);
        }else if(model.getEndTime().isBeforeNow()){
            model.setStatus(3);
        }else{
            model.setStatus(2);
        }
        return model;
    }

    private PromoModel convertFromPromo(Promo promo) {

        if(promo==null){
            return null;
        }
        PromoModel model = new PromoModel();
        BeanUtils.copyProperties(promo,model);

        model.setPromoItemPrice(new BigDecimal(promo.getPromoItemPrice()));
        model.setStartTime(new DateTime(promo.getStartTime()));
        model.setEndTime(new DateTime(promo.getEndTime()));

        return model;
    }
}
