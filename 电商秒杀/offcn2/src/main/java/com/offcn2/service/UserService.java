package com.offcn2.service;

import com.offcn2.error.BusinessException;
import com.offcn2.service.UserModel.UserModel;

public interface UserService {
    public UserModel findUserById(int id);

    public void register(UserModel userModel) throws BusinessException;

    //telphone是用户手机号，password是加密后的密码
    public UserModel validateLogin(String telphone,String encryptpassword) throws BusinessException;
}
