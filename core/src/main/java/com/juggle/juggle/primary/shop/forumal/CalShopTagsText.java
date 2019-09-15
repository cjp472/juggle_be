package com.juggle.juggle.primary.shop.forumal;

import com.juggle.juggle.framework.common.utils.ApplicationUtils;
import com.juggle.juggle.framework.data.json.load.Calculator;
import com.juggle.juggle.primary.shop.dao.ShopTagDao;
import com.juggle.juggle.primary.shop.model.ShopProduct;
import com.juggle.juggle.primary.shop.model.ShopTag;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CalShopTagsText implements Calculator {

    @Override
    public Object calc(Object bean, Collection exts) {
        ShopProduct entity = (ShopProduct) bean;
        if(!StringUtils.isEmpty(entity.getTags())){
            List<Long> tags;
            tags = Arrays.asList(StringUtils.split(entity.getTags(),",")).stream().map(Long::valueOf).collect(Collectors.toList());
            ShopTagDao shopTagDao = ApplicationUtils.getBean(ShopTagDao.class);
            List<ShopTag> shopTags = shopTagDao.findAllByIdInAndEnabled(tags,true);
            if(null!=shopTags&&shopTags.size()>0){
                List<String> tagNames = new ArrayList<>();
                for (ShopTag shopTag : shopTags) {
                    tagNames.add(shopTag.getName());
                }
                return StringUtils.join(tagNames,",");
            }
        }
        return null;
    }
}
