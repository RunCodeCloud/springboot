package com.offcn2.mapper;

import com.offcn2.bean.Promo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PromoMapper {
    Promo selectByItemId(Integer itemId);
}
