package com.offcn2.controller;

import com.offcn2.controller.viewModel.BaseController;
import com.offcn2.controller.viewModel.ItemVo;
import com.offcn2.error.BusinessException;
import com.offcn2.response.CommonReturnType;
import com.offcn2.service.Impl.ItemServiceImpl;
import com.offcn2.service.ItemService;
import com.offcn2.service.UserModel.ItemModel;
import com.offcn2.service.UserService;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.ibatis.annotations.Param;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller("/item")
@RequestMapping("/item")
@CrossOrigin(allowCredentials="true",allowedHeaders="*")
public class ItemController extends BaseController {

    //创建商品
   @Autowired
    ItemService itemService;
    @RequestMapping(value = "/create",method = {RequestMethod.POST},consumes={CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType createItemVo(@RequestParam(name = "title")String title,
                                         @RequestParam(name = "price")BigDecimal price,
                                         @RequestParam(name = "stock")Integer stock,
                                         @RequestParam(name = "description")String description,
                                         @RequestParam(name = "img_url")String img_url) throws BusinessException {
        //封装service请求用来创建商品
        ItemModel itemModel = new ItemModel();
        itemModel.setTitle(title);
        itemModel.setPrice(price);
        itemModel.setStock(stock);
        itemModel.setDescription(description);
        itemModel.setImg_url(img_url);

        ItemModel itemModelReturn = itemService.createItem(itemModel);

        ItemVo itemVo = convertItemVoFromItemModel(itemModelReturn);
        return CommonReturnType.crate(itemVo);
    }
    //浏览商品
    @RequestMapping(value = "/getItem",method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType getItem(@RequestParam(name = "id")Integer id){

        ItemModel itemModelReturn = itemService.getItemById(id);
        ItemVo itemVo = convertItemVoFromItemModel(itemModelReturn);

        return CommonReturnType.crate(itemVo);
    }

    //商品列表页面浏览
    @RequestMapping(value = "/list",method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType getListItem(){
        List<ItemModel> itemModelList = itemService.listItemModel();

        List<ItemVo> itemVoList = itemModelList.stream().map(itemModel -> {
            ItemVo itemVo = convertItemVoFromItemModel(itemModel);
            return itemVo;
        }).collect(Collectors.toList());
       /* --------------------------写到这的分割线-----------------------*/
        return CommonReturnType.crate(itemVoList);
    }

    private ItemVo convertItemVoFromItemModel(ItemModel itemModel){
        if(itemModel==null){
            return null;
        }
        ItemVo itemVo = new ItemVo();
        BeanUtils.copyProperties(itemModel,itemVo);

        if(itemModel.getPromoModel()!=null){
            //有正在进行或即将进行的秒杀活动
            itemVo.setPromoStatus(itemModel.getPromoModel().getStatus());
            itemVo.setPromoPrice(itemModel.getPromoModel().getPromoItemPrice());
            itemVo.setStartTime(itemModel.getPromoModel().getStartTime().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")));
            itemVo.setPromoId(itemModel.getPromoModel().getId());
        }else {
            itemVo.setPromoStatus(0);
        }

        return itemVo;
    }
}
