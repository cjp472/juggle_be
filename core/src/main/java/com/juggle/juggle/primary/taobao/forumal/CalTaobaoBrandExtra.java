package com.juggle.juggle.primary.taobao.forumal;

import com.juggle.juggle.framework.common.utils.ApplicationUtils;
import com.juggle.juggle.framework.data.json.load.Calculator;
import com.juggle.juggle.primary.taobao.dao.TaobaoProductDao;
import com.juggle.juggle.primary.taobao.dto.TaobaoBrandExtra;
import com.juggle.juggle.primary.taobao.model.TaobaoBrand;

import java.util.Collection;

public class CalTaobaoBrandExtra implements Calculator {

    @Override
    public Object calc(Object bean, Collection exts) {
        TaobaoBrand entity = (TaobaoBrand) bean;
        TaobaoProductDao taobaoProductDao = ApplicationUtils.getBean(TaobaoProductDao.class);
        TaobaoBrandExtra extra = new TaobaoBrandExtra();
        extra.setProductNum(taobaoProductDao.countAllByBrandId(entity.getId()));
        return extra;
    }
}
