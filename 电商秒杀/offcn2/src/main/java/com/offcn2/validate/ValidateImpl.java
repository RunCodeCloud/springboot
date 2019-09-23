package com.offcn2.validate;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.naming.spi.InitialContextFactory;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

@Component
public class ValidateImpl implements InitializingBean {

    private Validator validator;
    //实现校验方法并返回校验结果
    public ValidateResult validate(Object bean){
        ValidateResult result = new ValidateResult();
        Set<ConstraintViolation<Object>> constraintViolationSet = validator.validate(bean);
        if(constraintViolationSet.size()>0){
            result.setHasErrors(true);
            constraintViolationSet.forEach(constraintViolation->{
                String errMsg = constraintViolation.getMessage();
                String propertyName = constraintViolation.getPropertyPath().toString();
                result.getErrMsgMap().put(propertyName,errMsg);
            });
        }
        return result;
    }

    @Override
    //InitializingBean初始化 当spring bean初始化完成后，会会回调
    public void afterPropertiesSet() throws Exception {
        //将hibernate 通过工厂的初始化方式，使其实例化    实现java validate接口的校验器
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();

    }
}
