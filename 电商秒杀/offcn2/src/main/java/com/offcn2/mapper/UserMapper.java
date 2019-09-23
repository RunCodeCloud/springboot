package com.offcn2.mapper;

import com.offcn2.bean.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    public User selectUserById(int id);
    public User selectUserByTelphone(String telphone);
    public void insertUser(User user);
}
