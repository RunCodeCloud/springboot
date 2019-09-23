package com.offcn2.mapper;

import com.offcn2.bean.Order1;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface Order1Mapper {
    void insertOrder(Order1 order1);
}
