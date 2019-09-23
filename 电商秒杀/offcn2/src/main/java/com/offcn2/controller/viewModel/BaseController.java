package com.offcn2.controller.viewModel;

import com.offcn2.error.BusinessException;
import com.offcn2.error.EmBusinessError;
import com.offcn2.response.CommonReturnType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class BaseController {

    public static final String CONTENT_TYPE_FORMED = "application/x-www-form-urlencoded";

    //定义exceptionhanndler解决未被controller吸收掉的exception
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public Object handlerException(HttpServletRequest request, Exception ex){

        Map<String,Object> responseDate = new HashMap<>();
        if(ex instanceof BusinessException){
            BusinessException businessException = (BusinessException)ex;
            responseDate.put("errCode",businessException.getErrCode());
            responseDate.put("errMsg",businessException.getErrMsg());
        }else {
            responseDate.put("errCode", EmBusinessError.USER_NOT_EXIST.getErrCode());
            responseDate.put("errMsg",EmBusinessError.USER_NOT_EXIST.getErrMsg());
        }
        return CommonReturnType.crate(responseDate,"fail");
    }
}
