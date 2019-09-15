package com.juggle.juggle.api.v1.setting;

import com.juggle.juggle.common.data.LongRequest;
import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.api.utils.ApiResponse;
import com.juggle.juggle.framework.data.filter.SimpleSearch;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.setting.model.Domain;
import com.juggle.juggle.primary.setting.service.DomainService;
import com.juggle.juggle.primary.system.dto.TreeSelectItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/setting/domain")
public class DomainApi extends BaseApi<Domain,Domain,Domain> {
    @Autowired
    private DomainService service;

    @Override
    protected ICRUDService<Domain> getService() {
        return service;
    }

    @RequestMapping(value = "/searchTree",method = {RequestMethod.POST})
    public @ResponseBody Object searchTree(@RequestBody SimpleSearch search){
        List<Domain> domains = service.getTree(search.getFilters());
        return ApiResponse.make(domains);
    }

    @RequestMapping(value = "/searchTreeSelect",method = {RequestMethod.POST})
    public @ResponseBody Object searchTreeSelect(@RequestBody LongRequest req){
        List<TreeSelectItem> treeSelectItems = service.getTreeSelect(req.getId());
        return ApiResponse.make(treeSelectItems);
    }

    @RequestMapping(value = "/{id}/enable",method = {RequestMethod.POST})
    public @ResponseBody
    Object enable(@PathVariable Long id){
        service.enable(id);
        return ApiResponse.make();
    }

    @RequestMapping(value = "/{id}/disable",method = {RequestMethod.POST})
    public @ResponseBody Object disable(@PathVariable Long id){
        service.disable(id);
        return ApiResponse.make();
    }
}
