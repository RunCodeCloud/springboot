package com.offcn2.controller;

import com.offcn2.controller.viewModel.BaseController;
import com.offcn2.error.BusinessException;
import com.offcn2.error.EmBusinessError;
import com.offcn2.response.CommonReturnType;
import com.offcn2.service.OrderService;
import com.offcn2.service.UserModel.OrderModel;
import com.offcn2.service.UserModel.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@Controller("/order")
@RequestMapping("/order")
@CrossOrigin(allowCredentials="true",allowedHeaders="*")
public class OrderController extends BaseController {

    @Autowired
    OrderService orderService;
    @Autowired
    HttpServletRequest httpServletRequest;

    //封装下单请求
    @RequestMapping(value = "/create",method = {RequestMethod.POST},consumes={CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType createOrder(@RequestParam(name = "promoId",required = false)Integer promoId,
                                        @RequestParam(name = "itemId")Integer itemId ,
                                        @RequestParam(name = "amount")Integer amount) throws BusinessException {
        //验证是否登录
        Boolean isLogin = (Boolean) httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if(isLogin==null||!isLogin.booleanValue()){
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }
        //获取登录信息
        UserModel userModel = (UserModel) httpServletRequest.getSession().getAttribute("LOGIN_USER");
        OrderModel orderModel = orderService.createOrder(itemId,userModel.getId(),amount,promoId);

        return CommonReturnType.crate(null);
    }
}
