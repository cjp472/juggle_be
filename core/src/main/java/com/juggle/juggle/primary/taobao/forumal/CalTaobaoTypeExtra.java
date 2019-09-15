package com.juggle.juggle.primary.taobao.forumal;

import com.juggle.juggle.framework.common.utils.ApplicationUtils;
import com.juggle.juggle.framework.data.json.load.Calculator;
import com.juggle.juggle.primary.taobao.dao.TaobaoBrandDao;
import com.juggle.juggle.primary.taobao.dto.TaobaoTypeExtra;
import com.juggle.juggle.primary.taobao.model.TaobaoCategory;

import java.util.Collection;

public class CalTaobaoTypeExtra implements Calculator {

    @Override
    public Object calc(Object bean, Collection exts) {
        TaobaoCategory entity = (TaobaoCategory) bean;
        TaobaoBrandDao taobaoBrandDao = ApplicationUtils.getBean(TaobaoBrandDao.class);
        TaobaoTypeExtra extra = new TaobaoTypeExtra();
        extra.setBrandNum(taobaoBrandDao.countAllByCategoryId(entity.getId()));
        return extra;
    }
}
