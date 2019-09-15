package com.juggle.juggle.primary.taobao.forumal;

import com.juggle.juggle.framework.common.utils.JsonUtils;
import com.juggle.juggle.framework.data.json.load.Calculator;
import com.juggle.juggle.primary.taobao.model.TaobaoFavourable;
import com.taobao.api.response.TbkDgOptimusMaterialResponse;

import java.util.Collection;

public class CalTaobaoFavourableSnapshot implements Calculator {

    @Override
    public Object calc(Object bean, Collection exts) {
        TaobaoFavourable entity = (TaobaoFavourable) bean;
        TbkDgOptimusMaterialResponse.MapData mapData = JsonUtils.readValue(entity.getSnapshot(), TbkDgOptimusMaterialResponse.MapData.class);
        return mapData;
    }
}
