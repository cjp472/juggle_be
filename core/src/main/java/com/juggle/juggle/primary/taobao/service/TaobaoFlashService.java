package com.juggle.juggle.primary.taobao.service;

import com.juggle.juggle.common.data.SelectItem;
import com.juggle.juggle.framework.cache.meta.Cache;
import com.juggle.juggle.framework.common.errors.ServiceException;
import com.juggle.juggle.framework.common.utils.DateUtils;
import com.juggle.juggle.framework.common.utils.PropertyCopyUtil;
import com.juggle.juggle.framework.data.filter.PageResult;
import com.juggle.juggle.framework.data.filter.PageSearch;
import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.framework.data.service.BaseCRUDService;
import com.juggle.juggle.primary.Constants;
import com.juggle.juggle.primary.taobao.dao.TaobaoFlashDao;
import com.juggle.juggle.primary.taobao.dto.TaobaoFlashRush;
import com.juggle.juggle.primary.taobao.dto.TaobaoRushSimplify;
import com.juggle.juggle.primary.taobao.model.TaobaoFlash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TaobaoFlashService extends BaseCRUDService<TaobaoFlash> {
    @Autowired
    private TaobaoFlashDao dao;

    @Override
    protected IRepo<TaobaoFlash> getRepo() {
        return dao;
    }

    public List<SelectItem> readAllRush(){
        List<SelectItem> selectItems = new ArrayList<>();
        selectItems.add(new SelectItem(Constants.TAOBAO_FLASH_RUSH_TIME.ZERO,Constants.TAOBAO_FLASH_RUSH_TIME.ZERO));
        selectItems.add(new SelectItem(Constants.TAOBAO_FLASH_RUSH_TIME.EIGHT,Constants.TAOBAO_FLASH_RUSH_TIME.EIGHT));
        selectItems.add(new SelectItem(Constants.TAOBAO_FLASH_RUSH_TIME.TWELVE,Constants.TAOBAO_FLASH_RUSH_TIME.TWELVE));
        selectItems.add(new SelectItem(Constants.TAOBAO_FLASH_RUSH_TIME.SIXTEEN,Constants.TAOBAO_FLASH_RUSH_TIME.SIXTEEN));
        selectItems.add(new SelectItem(Constants.TAOBAO_FLASH_RUSH_TIME.TWENTY_ONE,Constants.TAOBAO_FLASH_RUSH_TIME.TWENTY_ONE));
        return selectItems;
    }

    public List<TaobaoFlashRush> readAllTodayRush(){
        List<TaobaoFlashRush> flashRushes = new ArrayList<>();
        String time = DateUtils.format(new Date(),DateUtils.TIME_FORMAT);
        if(time.compareTo(Constants.TAOBAO_FLASH_RUSH_TIME.EIGHT)<0){
            flashRushes.add(new TaobaoFlashRush(Constants.TAOBAO_FLASH_RUSH_TIME.ZERO,"抢购中"));
            flashRushes.add(new TaobaoFlashRush(Constants.TAOBAO_FLASH_RUSH_TIME.EIGHT,"未开始"));
            flashRushes.add(new TaobaoFlashRush(Constants.TAOBAO_FLASH_RUSH_TIME.TWELVE,"未开始"));
            flashRushes.add(new TaobaoFlashRush(Constants.TAOBAO_FLASH_RUSH_TIME.SIXTEEN,"未开始"));
            flashRushes.add(new TaobaoFlashRush(Constants.TAOBAO_FLASH_RUSH_TIME.TWENTY_ONE,"未开始"));
        }else if(time.compareTo(Constants.TAOBAO_FLASH_RUSH_TIME.TWELVE)<0){
            flashRushes.add(new TaobaoFlashRush(Constants.TAOBAO_FLASH_RUSH_TIME.ZERO,"进行中"));
            flashRushes.add(new TaobaoFlashRush(Constants.TAOBAO_FLASH_RUSH_TIME.EIGHT,"抢购中"));
            flashRushes.add(new TaobaoFlashRush(Constants.TAOBAO_FLASH_RUSH_TIME.TWELVE,"未开始"));
            flashRushes.add(new TaobaoFlashRush(Constants.TAOBAO_FLASH_RUSH_TIME.SIXTEEN,"未开始"));
            flashRushes.add(new TaobaoFlashRush(Constants.TAOBAO_FLASH_RUSH_TIME.TWENTY_ONE,"未开始"));
        }else if(time.compareTo(Constants.TAOBAO_FLASH_RUSH_TIME.SIXTEEN)<0){
            flashRushes.add(new TaobaoFlashRush(Constants.TAOBAO_FLASH_RUSH_TIME.ZERO,"进行中"));
            flashRushes.add(new TaobaoFlashRush(Constants.TAOBAO_FLASH_RUSH_TIME.EIGHT,"进行中"));
            flashRushes.add(new TaobaoFlashRush(Constants.TAOBAO_FLASH_RUSH_TIME.TWELVE,"抢购中"));
            flashRushes.add(new TaobaoFlashRush(Constants.TAOBAO_FLASH_RUSH_TIME.SIXTEEN,"未开始"));
            flashRushes.add(new TaobaoFlashRush(Constants.TAOBAO_FLASH_RUSH_TIME.TWENTY_ONE,"未开始"));
        }else if(time.compareTo(Constants.TAOBAO_FLASH_RUSH_TIME.TWENTY_ONE)<0){
            flashRushes.add(new TaobaoFlashRush(Constants.TAOBAO_FLASH_RUSH_TIME.ZERO,"进行中"));
            flashRushes.add(new TaobaoFlashRush(Constants.TAOBAO_FLASH_RUSH_TIME.EIGHT,"进行中"));
            flashRushes.add(new TaobaoFlashRush(Constants.TAOBAO_FLASH_RUSH_TIME.TWELVE,"进行中"));
            flashRushes.add(new TaobaoFlashRush(Constants.TAOBAO_FLASH_RUSH_TIME.SIXTEEN,"抢购中"));
            flashRushes.add(new TaobaoFlashRush(Constants.TAOBAO_FLASH_RUSH_TIME.TWENTY_ONE,"未开始"));
        }else{
            flashRushes.add(new TaobaoFlashRush(Constants.TAOBAO_FLASH_RUSH_TIME.ZERO,"进行中"));
            flashRushes.add(new TaobaoFlashRush(Constants.TAOBAO_FLASH_RUSH_TIME.EIGHT,"进行中"));
            flashRushes.add(new TaobaoFlashRush(Constants.TAOBAO_FLASH_RUSH_TIME.TWELVE,"进行中"));
            flashRushes.add(new TaobaoFlashRush(Constants.TAOBAO_FLASH_RUSH_TIME.SIXTEEN,"进行中"));
            flashRushes.add(new TaobaoFlashRush(Constants.TAOBAO_FLASH_RUSH_TIME.TWENTY_ONE,"抢购中"));
        }
        return flashRushes;
    }

    @Cache(cacheNull = false)
    public PageResult searchRushSimplify(PageSearch pageSearch){
        PageResult pageResult = search(pageSearch);
        List<TaobaoFlash> products = pageResult.getRows();
        List<TaobaoRushSimplify> productSimplifies = new ArrayList<>();
        for (TaobaoFlash product : products) {
            TaobaoRushSimplify productSimplify = new TaobaoRushSimplify();
            try{
                PropertyCopyUtil.getInstance().copyProperties(productSimplify,product);
            }catch (Exception e){
                throw new ServiceException(1001,"PropertyCopyUtil解析失败");
            }
            productSimplifies.add(productSimplify);
        }
        pageResult.setRows(productSimplifies);
        return pageResult;
    }

    public void switchTop(Long id){
        TaobaoFlash taobaoFlash = dao.findOne(id);
        taobaoFlash.setTop(!taobaoFlash.isTop());
        update(id,taobaoFlash);
    }
}
