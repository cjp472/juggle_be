package com.juggle.juggle.api.v1.taobao;

import com.juggle.juggle.common.data.LongRequest;
import com.juggle.juggle.common.data.SelectItem;
import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.api.utils.ApiResponse;
import com.juggle.juggle.framework.data.filter.SimpleSearch;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.system.dto.TreeSelectItem;
import com.juggle.juggle.primary.taobao.model.TaobaoType;
import com.juggle.juggle.primary.taobao.service.TaobaoTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/taobao/taobaoType")
public class TaobaoTypeApi extends BaseApi<TaobaoType,TaobaoType, TaobaoType> {
    @Autowired
    private TaobaoTypeService service;

    @Override
    protected ICRUDService<TaobaoType> getService() {
        return service;
    }

    @RequestMapping(value = "/readAllRootEnabled")
    public @ResponseBody Object readAllRootEnabled(){
        List<SelectItem> selectItems = service.readAllRootEnabled();
        return ApiResponse.make(selectItems);
    }

    @RequestMapping(value = "/readAllEnabled/{parentId}")
    public @ResponseBody Object readAllEnabled(@PathVariable Long parentId){
        List<SelectItem> selectItems = service.readAllEnabled(parentId);
        return ApiResponse.make(selectItems);
    }

    @RequestMapping(value = "/searchTree",method = {RequestMethod.POST})
    public @ResponseBody Object searchTree(@RequestBody SimpleSearch search){
        List<TaobaoType> productTypes = service.getTree(search.getFilters());
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
