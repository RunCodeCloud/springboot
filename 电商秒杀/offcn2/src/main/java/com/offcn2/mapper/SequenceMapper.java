package com.offcn2.mapper;

import com.offcn2.bean.Sequence;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SequenceMapper {
    Sequence selectSequenceByName(String name);
    void updateCurrent_value(int value);
}
