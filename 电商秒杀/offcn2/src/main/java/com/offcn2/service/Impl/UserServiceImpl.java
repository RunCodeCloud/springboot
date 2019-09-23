package com.offcn2.service.Impl;

import com.offcn2.bean.Password;
import com.offcn2.bean.User;
import com.offcn2.error.BusinessException;
import com.offcn2.error.EmBusinessError;
import com.offcn2.mapper.PasswordMapper;
import com.offcn2.mapper.UserMapper;
import com.offcn2.service.UserModel.UserModel;
import com.offcn2.service.UserService;
import com.offcn2.validate.ValidateImpl;
import com.offcn2.validate.ValidateResult;
import org.apache.commons.lang3.StringUtils;
import org.omg.PortableInterceptor.ORBInitInfoPackage.DuplicateName;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    ValidateImpl validateImpl;

    @Resource
    UserMapper userMapper;
    @Resource
    PasswordMapper passwordMapper;

    public UserModel findUserById(int id){

        User user = userMapper.selectUserById(id);
        if(user==null){
            return null;
        }
        Password password = passwordMapper.selectByUserId(user.getId());

       return convertFormDateObject(user,password);
    }

    //注册
    @Override
    @Transactional
    public void register(UserModel userModel) throws BusinessException {
        if(userModel==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
       /* if(StringUtils.isEmpty(userModel.getName())
                ||userModel.getAge()==null
                ||userModel.getGender()==null
                ||StringUtils.isEmpty(userModel.getTelphone())){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }*/

       ValidateResult result = validateImpl.validate(userModel);
       if(result.isHasErrors()){
           throw  new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
       }

        //实现model到dataobject方法
        User user = convertFormModel(userModel);
        try {
            userMapper.insertUser(user);
        }catch (DuplicateKeyException e){
            throw new BusinessException(EmBusinessError.UNKNOWN_ERROR,"手机号已注册");
        }

        userModel.setId(userMapper.selectUserByTelphone(user.getTelphone()).getId());

        Password password = convertPasswordFormModel(userModel);
        passwordMapper.insertPassword(password);
    }

    //登录
    @Override
    public UserModel validateLogin(String telphone, String encryptpassword) throws BusinessException {
        //通过用户手机获取用户信息
        User user = userMapper.selectUserByTelphone(telphone);
        if(user==null){
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
        Password password = passwordMapper.selectByUserId(user.getId());
        UserModel userModel = convertFormDateObject(user,password);
        //比对加密的密码是否和输入的密码相匹配
        if(!StringUtils.equals(encryptpassword,userModel.getPassword())){
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
        return userModel;
    }

    private Password convertPasswordFormModel(UserModel userModel){
        if(userModel==null){
            return null;
        }
        Password password = new Password();
       /* BeanUtils.copyProperties(userModel,password);
        if(password.getUser_id()==null){
            password.setUser_id(userModel.getId());
        }*/
        password.setUser_id(userModel.getId());
        password.setPassword(userModel.getPassword());
        return password;
    }

    private User convertFormModel(UserModel userModel){
        if(userModel==null){
            return null;
        }
        User user = new User();
        BeanUtils.copyProperties(userModel,user);
        if(user.getThird_party_id()==null||"".equals(user.getThird_party_id())){
            user.setThird_party_id("");
        }
        return user;
    }

    private UserModel convertFormDateObject(User user, Password password){

        UserModel userModel = new UserModel();
        if(user==null) {
            return null;
        }
        BeanUtils.copyProperties(user,userModel);
        if(password!=null){
            userModel.setPassword(password.getPassword());
        }
        return userModel;
    }
}
