package com.offcn2.service;

import com.offcn2.error.BusinessException;
import com.offcn2.service.UserModel.ItemModel;

import java.util.List;

public interface ItemService {

    //创建商品
    ItemModel createItem(ItemModel itemModel) throws BusinessException;

    //商品列表浏览
    List<ItemModel> listItemModel();

    //商品详情浏览
    ItemModel getItemById(Integer id);

    //库存扣减
    boolean decreaseStock(Integer itemId,Integer amount);

    //销量增加
    void increaseSales(Integer itemId,Integer amount);
}
