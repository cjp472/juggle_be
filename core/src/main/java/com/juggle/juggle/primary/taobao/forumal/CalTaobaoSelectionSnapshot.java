package com.juggle.juggle.primary.taobao.forumal;

import com.juggle.juggle.framework.common.utils.JsonUtils;
import com.juggle.juggle.framework.data.json.load.Calculator;
import com.juggle.juggle.primary.taobao.model.TaobaoSelection;
import com.taobao.api.domain.UatmTbkItem;

import java.util.Collection;

public class CalTaobaoSelectionSnapshot implements Calculator {

    @Override
    public Object calc(Object bean, Collection exts) {
        TaobaoSelection entity = (TaobaoSelection) bean;
        UatmTbkItem uatmTbkItem = JsonUtils.readValue(entity.getSnapshot(), UatmTbkItem.class);
        return uatmTbkItem;
    }
}
