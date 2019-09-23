package com.offcn2.mapper;

import com.offcn2.bean.Stock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StockMapper {
    void insertStock(Stock stock);
    Stock selectStockByItemId(int id);
    int decreaseStock(@Param("itemId") Integer itemId, @Param("amount") Integer amount);
}
