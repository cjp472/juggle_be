package com.juggle.juggle.api.v1.shop;

import com.juggle.juggle.common.data.LongRequest;
import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.api.utils.ApiResponse;
import com.juggle.juggle.framework.data.filter.SimpleSearch;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.shop.model.ShopType;
import com.juggle.juggle.primary.shop.service.ShopTypeService;
import com.juggle.juggle.primary.system.dto.TreeSelectItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/shop/shopType")
public class ShopTypeApi extends BaseApi<ShopType,ShopType,ShopType> {
    @Autowired
    private ShopTypeService service;

    @Override
    protected ICRUDService<ShopType> getService() {
        return service;
    }

    @RequestMapping(value = "/searchTree",method = {RequestMethod.POST})
    public @ResponseBody
    Object searchTree(@RequestBody SimpleSearch search){
        List<ShopType> productTypes = service.getTree(search.getFilters());
        return ApiResponse.make(productTypes);
    }

    @RequestMapping(value = "/searchTreeSelect",method = {RequestMethod.POST})
    public @ResponseBody Object searchTreeSelect(@RequestBody LongRequest req){
        List<TreeSelectItem> treeSelectItems = service.getTreeSelect(req.getId());
        return ApiResponse.make(treeSelectItems);
    }

    @RequestMapping(value = "/{id}/enable",method = {RequestMethod.POST})
    public @ResponseBody Object enable(@PathVariable Long id){
        service.enable(id);
        return ApiResponse.make();
    }

    @RequestMapping(value = "/{id}/disable",method = {RequestMethod.POST})
    public @ResponseBody Object disable(@PathVariable Long id){
        service.disable(id);
        return ApiResponse.make();
    }
}
