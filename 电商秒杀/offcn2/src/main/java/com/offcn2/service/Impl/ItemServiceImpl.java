package com.offcn2.service.Impl;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.offcn2.bean.Item;
import com.offcn2.bean.Stock;
import com.offcn2.error.BusinessException;
import com.offcn2.error.EmBusinessError;
import com.offcn2.mapper.ItemMapper;
import com.offcn2.mapper.StockMapper;
import com.offcn2.service.ItemService;
import com.offcn2.service.PromoService;
import com.offcn2.service.UserModel.ItemModel;
import com.offcn2.service.UserModel.PromoModel;
import com.offcn2.validate.ValidateImpl;
import com.offcn2.validate.ValidateResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.SQLOutput;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {

    @Resource
    PromoService promoService;
    @Autowired
    ValidateImpl validateImpl;
    @Resource
    ItemMapper itemMapper;
    @Resource
    StockMapper stockMapper;

    @Override
    @Transactional
    public ItemModel createItem(ItemModel itemModel) throws BusinessException {

        //校验入参
        ValidateResult result = validateImpl.validate(itemModel);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }

        //转化model-->bean
        Item item = convertItemFromItemModel(itemModel);

        //写入数据库
        itemMapper.insertItem(item);

        itemModel.setId(itemMapper.selectItemByTitle(item.getTitle()).getId());
        Stock stock = convertStockFromItemModel(itemModel);
        stockMapper.insertStock(stock);

        //返回对象
        return this.getItemById(itemModel.getId());
    }

    private Item convertItemFromItemModel(ItemModel itemModel){
        if(itemModel==null){
            return null;
        }
        Item item = new Item();
        BeanUtils.copyProperties(itemModel,item);
        item.setPrice(itemModel.getPrice().doubleValue());
        if(item.getSales()==null){
            item.setSales(0);
        }
        return item;
    }
    private Stock convertStockFromItemModel(ItemModel itemModel){
        if(itemModel==null){
            return null;
        }
        Stock stock = new Stock();
        stock.setStock(itemModel.getStock());
        stock.setItem_id(itemModel.getId());
        return stock;
    }

    //商品列表
    @Override
    public List<ItemModel> listItemModel() {

        List<Item> itemList = itemMapper.ListItem();

        //不懂
        List<ItemModel> itemModelList = itemList.stream().map(item -> {
            Stock stock = stockMapper.selectStockByItemId(item.getId());
            ItemModel itemModel = this.convertFromBean(item,stock);
            return itemModel;
        }).collect(Collectors.toList());

        return itemModelList;
    }
//----------------------------------------------------------------------
    @Override
    public ItemModel getItemById(Integer id) {

        Item item = itemMapper.selectItemById(id);
        if (item==null){
            return null;
        }
        //操作获得库存数量
        Stock stock = stockMapper.selectStockByItemId(item.getId());

        //data-->model
        ItemModel itemModel = convertFromBean(item,stock);

        //获取活动商品信息

        PromoModel promoModel = promoService.getPromoModel(itemModel.getId());
        //使用模型聚合的方式把秒杀商品和秒杀活动聚合在一起
        if(promoModel!=null&&promoModel.getStatus().intValue()!=3) {
            itemModel.setPromoModel(promoModel);
        }
        return itemModel;
    }

    //库存减操作
    @Override
    @Transactional
    public boolean decreaseStock(Integer itemId, Integer amount) {
        int affectedRow = stockMapper.decreaseStock(itemId,amount);
        if(affectedRow>0){
            //更新库存成功
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public void increaseSales(Integer itemId, Integer amount) {
        itemMapper.increaseSales(itemId,amount);
    }

    private ItemModel convertFromBean(Item item,Stock stock){
        ItemModel itemModel = new ItemModel();
        BeanUtils.copyProperties(item,itemModel);
        itemModel.setPrice(new BigDecimal(item.getPrice()));
        itemModel.setStock(stock.getStock());
        return itemModel;
    }
}
