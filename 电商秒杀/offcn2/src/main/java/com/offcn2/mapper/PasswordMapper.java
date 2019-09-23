package com.offcn2.mapper;

import com.offcn2.bean.Password;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PasswordMapper {
    public Password selectByUserId(int id);
    public void insertPassword(Password password);
}
