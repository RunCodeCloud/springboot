package com.offcn2.controller;

import com.alibaba.druid.util.StringUtils;
import com.offcn2.controller.viewModel.BaseController;
import com.offcn2.controller.viewModel.UserVo;
import com.offcn2.error.BusinessException;
import com.offcn2.error.EmBusinessError;
import com.offcn2.response.CommonReturnType;
import com.offcn2.service.UserModel.UserModel;
import com.offcn2.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

@RestController
@RequestMapping("/user")
@CrossOrigin(allowCredentials="true",allowedHeaders="*")
public class UserController extends BaseController {

    @Autowired
    UserService userService;

    @Autowired
    HttpServletRequest httpServletRequest;

    @RequestMapping(value = "/getotp",method = {RequestMethod.POST},consumes={CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType getOtp(@RequestParam(name="telphone")String telphone){
        //需要按照一定规则生成otp验证码
        int randomInt = new Random().nextInt(99999); //[0,99999)
        randomInt += 10000; //[10000,109999)
        String otpCode = String.valueOf(randomInt);
        //将otp验证码同时和用户手机号关联,使用httpSession的方式绑定用户手机号与otp验证码
        httpServletRequest.getSession().setAttribute(telphone,otpCode);
        //otp验证码通过短信通道发送给用户
        System.out.println("telphone = "+telphone+" & otpCode = "+otpCode);

        return CommonReturnType.crate(null);
    }
    //用户注册功能
    @RequestMapping(value = "/register",method = {RequestMethod.POST},consumes={CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType register(@RequestParam(name = "telphone")String telphone,
                                     @RequestParam(name = "otpCode")String otpCode,
                                     @RequestParam(name = "name")String name,
                                     @RequestParam(name = "age")Integer age,
                                     @RequestParam(name = "gender")Integer gender,
                                     @RequestParam(name = "password")String password ) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        //验证手机号和用户的otpcode相符合
        String sessionOtpCode = (String) httpServletRequest.getSession().getAttribute(telphone);
        if(!StringUtils.equals(otpCode,sessionOtpCode)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"短信验证码不符合");
        }
        //用户注册流程
        UserModel userModel = new UserModel();
        userModel.setName(name);
        userModel.setGender(gender);
        userModel.setAge(age);
        userModel.setTelphone(telphone);
        userModel.setRegister_mode("byphone");
        userModel.setPassword(this.EncodeByMD5(password));

        userService.register(userModel);

        return CommonReturnType.crate(null);
    }
    //用户登录模块
    //用户注册功能
    @RequestMapping(value = "/login",method = {RequestMethod.POST},consumes={CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType login(@RequestParam(name = "telphone")String telphone,
                                  @RequestParam(name = "password")String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        //入参校验
        if(org.apache.commons.lang3.StringUtils.isEmpty(telphone)||
                org.apache.commons.lang3.StringUtils.isEmpty(password)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        //用户登录服务
        UserModel userModel = userService.validateLogin(telphone,this.EncodeByMD5(password));
        //将登录凭证加入到用户登录成功的session凭证中
        this.httpServletRequest.getSession().setAttribute("IS_LOGIN",true);
        this.httpServletRequest.getSession().setAttribute("LOGIN_USER",userModel);
        return CommonReturnType.crate(null);
    }

    public String EncodeByMD5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //确定计算方式
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64 = new BASE64Encoder();
        //加密字符串
        String newstr = base64.encode(md5.digest(str.getBytes("utf-8")));
        return newstr;
    }

    @RequestMapping("/get")
    public CommonReturnType getUser(@RequestParam(name="id")Integer id) throws BusinessException{

        UserModel userModel = userService.findUserById(id);

        if(userModel==null){
            userModel.setPassword("111");
           // throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }

        //将核心领域模型对象转化为可供ui使用的viewobject
        UserVo userVo = convertFromModel(userModel);
        //返回通用对象
       return  CommonReturnType.crate(userVo);
    }

    private UserVo convertFromModel(UserModel userModel){
        if(userModel==null){
            return null;
        }
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(userModel,userVo);
        return userVo;
    }
}
