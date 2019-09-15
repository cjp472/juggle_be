package com.juggle.juggle.framework.api;

import com.juggle.juggle.framework.api.utils.ApiResponse;
import com.juggle.juggle.framework.common.security.meta.Access;
import com.juggle.juggle.framework.data.entity.Entity;
import com.juggle.juggle.framework.data.filter.PageSearch;
import com.juggle.juggle.framework.data.service.ICRUDService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public abstract class BaseApi<T extends Entity, SAVE, UPDATE> {

    protected abstract ICRUDService<T> getService();

    @Access(":read")
    @RequestMapping(value = "/{id}/read",method = {RequestMethod.POST, RequestMethod.GET})
    public @ResponseBody Object read(@PathVariable Long id){
        return ApiResponse.make(this.getService().find(id));
    }

    @Access(":write")
    @RequestMapping(value = "/create",method = {RequestMethod.POST})
    public @ResponseBody Object create(@RequestBody @Valid SAVE obj){
        return ApiResponse.make(this.getService().create(obj));
    }

    @Access(":write")
    @RequestMapping(value = "/{id}/remove",method = {RequestMethod.POST, RequestMethod.GET})
    public @ResponseBody Object remove(@PathVariable Long id){
        this.getService().delete(id);
        return ApiResponse.make();
    }

    @Access(":write")
    @RequestMapping(value = "/remove",method = {RequestMethod.POST, RequestMethod.GET})
    public @ResponseBody Object remove(@RequestBody List<Long> ids){
        for(Long id:ids){
            this.getService().delete(id);
        }
        return ApiResponse.make();
    }

    @Access(":write")
    @RequestMapping(value = "/{id}/update",method = {RequestMethod.POST})
    public @ResponseBody Object update(@PathVariable Long id, @Valid @RequestBody UPDATE obj){
        return ApiResponse.make(this.getService().update(id, obj));
    }

    @Access(":read")
    @RequestMapping(value = "/search",method = {RequestMethod.POST})
    public @ResponseBody Object search(@RequestBody @Valid PageSearch pageSearch){
        String sort = pageSearch.getSort();
        if(StringUtils.isEmpty(sort)){
            sort = "id desc";
        }else if(!sort.contains("id")){
            sort = sort + ",id desc";
        }
        pageSearch.setSort(sort);
        return ApiResponse.make(this.getService().search(pageSearch));
    }
}
