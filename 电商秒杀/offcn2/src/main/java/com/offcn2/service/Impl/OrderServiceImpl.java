package com.offcn2.service.Impl;

import com.offcn2.bean.Order1;
import com.offcn2.bean.Sequence;
import com.offcn2.error.BusinessException;
import com.offcn2.error.EmBusinessError;
import com.offcn2.mapper.Order1Mapper;
import com.offcn2.mapper.SequenceMapper;
import com.offcn2.service.ItemService;
import com.offcn2.service.OrderService;
import com.offcn2.service.UserModel.ItemModel;
import com.offcn2.service.UserModel.OrderModel;
import com.offcn2.service.UserModel.UserModel;
import com.offcn2.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    ItemService itemService;
    @Resource
    UserService userService;
    @Resource
    Order1Mapper orderMapper;
    @Resource
    SequenceMapper sequenceMapper;

    @Override
    @Transactional
    public OrderModel createOrder(Integer itemId, Integer userId, Integer amount,Integer promoId) throws BusinessException {
        //1.检验下单状态，下单商品是否存在，用户是否存在，购买数量是否正确

        ItemModel itemModel = itemService.getItemById(itemId);
        if(itemModel==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"商品不存在");
        }
        UserModel userModel = userService.findUserById(userId);
        if(userModel==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"用户信息不正确");
        }
        if(amount<=0||amount>99){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"数量信息不正确");
        }

        //校验活动信息
        if(promoId!=null){
            //1.校验活动信息是否存在这个适用商品
            if(itemId!=itemModel.getPromoModel().getItemId()){
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"活动信息不正确");
            }else if(itemModel.getPromoModel().getStatus().intValue()!=2){
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"活动还未开始");
            }
        }

        //2.落单减库存  （支付减库存）
        boolean result = itemService.decreaseStock(itemId,amount);
        if(!result){
            throw new BusinessException(EmBusinessError.STOCK_NOT_ENOUGH);
        }
        //3.订单入库

        OrderModel orderModel = new OrderModel();
        orderModel.setItemId(itemId);
        orderModel.setUserId(userId);
        orderModel.setAmount(amount);

        if(promoId!=null){
            orderModel.setPromoId(promoId);
            orderModel.setItemPrice(itemModel.getPromoModel().getPromoItemPrice());
        }else {
            orderModel.setItemPrice(itemModel.getPrice());
        }
        orderModel.setOrderPrice(orderModel.getItemPrice().multiply(new BigDecimal(amount)));//计算方式？？？？

        //生成交易流水号
        orderModel.setId(generateOrderNo());
        Order1 order = convertFromOrderModel(orderModel);

        orderMapper.insertOrder(order);

        //加上商品的销量
        itemService.increaseSales(itemId,amount);

        //返回前端
        return orderModel;
    }
    //为什么不能用private
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    String generateOrderNo(){
        StringBuilder stringBuilder = new StringBuilder();
        //订单号16位
        //前8位时间信息，年月日
        LocalDateTime now = LocalDateTime.now();
        String nowDate = now.format(DateTimeFormatter.BASIC_ISO_DATE).replace("-","");
        stringBuilder.append(nowDate);
        //中间6位为自增序列
        Sequence sequence = sequenceMapper.selectSequenceByName("order");
        sequenceMapper.updateCurrent_value(sequence.getCurrent_value()+sequence.getStep());
        int value = sequence.getCurrent_value();
        String str = String.valueOf(value);
        for(int i=0;i<6-str.length();i++){
            stringBuilder.append("0");
        }
        stringBuilder.append(str);
        //最后两位为分库分表位
        stringBuilder.append("00");
        return stringBuilder.toString();
    }

    private Order1 convertFromOrderModel(OrderModel orderModel){
        if (orderModel==null){
            return null;
        }
        Order1 order = new Order1();
        BeanUtils.copyProperties(orderModel,order);
        order.setItemPrice(orderModel.getItemPrice().doubleValue());
        order.setOrderPrice(orderModel.getOrderPrice().doubleValue());
        return order;
    }
}
