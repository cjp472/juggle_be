package com.juggle.juggle.primary.taobao.forumal;

import com.juggle.juggle.framework.common.utils.JsonUtils;
import com.juggle.juggle.framework.data.json.load.Calculator;
import com.juggle.juggle.primary.taobao.model.TaobaoGoods;
import com.taobao.api.response.TbkDgMaterialOptionalResponse;

import java.util.Collection;

public class CalTaobaoGoodsSnapshot implements Calculator {

    @Override
    public Object calc(Object bean, Collection exts) {
        TaobaoGoods entity = (TaobaoGoods) bean;
        TbkDgMaterialOptionalResponse.MapData mapData = JsonUtils.readValue(entity.getSnapshot(), TbkDgMaterialOptionalResponse.MapData.class);
        return mapData;
    }
}
