package com.offcn2.response;

public class CommonReturnType {
    //表明对应请求返回的处理结果，success，fail
    private String status;
    //若status=success，则返回对应的json数据
    //若status=fail，则返回对应的错误码格式
    private Object date;

    //定义一个通用的创建方法
    public static CommonReturnType crate(Object result){
        return CommonReturnType.crate(result,"success");
    }

    public static CommonReturnType crate(Object result,String status){
        CommonReturnType common = new CommonReturnType();
        common.setStatus(status);
        common.setDate(result);
        return common;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getDate() {
        return date;
    }

    public void setDate(Object date) {
        this.date = date;
    }
}
