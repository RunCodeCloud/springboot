package com.offcn2.mapper;

import com.offcn2.bean.Item;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ItemMapper {
    void insertItem(Item item);
    Item selectItemByTitle(String title);
    Item selectItemById(int id);
    List<Item> ListItem();
    void increaseSales(Integer itemId,Integer amount);
}
