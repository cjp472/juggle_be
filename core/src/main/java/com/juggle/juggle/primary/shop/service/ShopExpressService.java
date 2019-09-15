package com.juggle.juggle.primary.shop.service;

import com.juggle.juggle.framework.common.errors.ServiceException;
import com.juggle.juggle.framework.common.utils.DateUtils;
import com.juggle.juggle.framework.common.utils.HttpUtils;
import com.juggle.juggle.framework.common.utils.JsonUtils;
import com.juggle.juggle.framework.common.utils.PropertyCopyUtil;
import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.framework.data.service.BaseCRUDService;
import com.juggle.juggle.primary.Constants;
import com.juggle.juggle.primary.setting.model.Dictionary;
import com.juggle.juggle.primary.setting.service.DictionaryService;
import com.juggle.juggle.primary.shop.dao.ShopExpressDao;
import com.juggle.juggle.primary.shop.dto.ShopExpressDto;
import com.juggle.juggle.primary.shop.model.ShopExpress;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class ShopExpressService extends BaseCRUDService<ShopExpress> {
    @Autowired
    private ShopExpressDao dao;

    @Autowired
    private DictionaryService dictionaryService;

    @Override
    protected IRepo<ShopExpress> getRepo() {
        return dao;
    }

    public ShopExpressDto getExpress(Long orderId){
        ShopExpress shopExpress = dao.findFirstByOrderId(orderId);
        ShopExpressDto shopExpressDto = new ShopExpressDto();
        if(null!=shopExpress&&(new Date()).after(DateUtils.goFuture(shopExpress.getUpdatedTime(),1800))){
            String snapshot = getSnapshot(shopExpress.getShippedNo(),shopExpress.getShippedCom());
            if(!StringUtils.isEmpty(snapshot)){
                shopExpress.setSnapshot(snapshot);
                shopExpress = update(shopExpress.getId(),shopExpress);
                try {
                    PropertyCopyUtil.getInstance().copyPropertiesWithIgnores(shopExpressDto,shopExpress,"snapshot");
                    shopExpressDto.setSnapshot(JsonUtils.readValue(shopExpress.getSnapshot(),Object.class));
                }catch (Exception e){
                    throw new ServiceException(1001,"PropertyCopyUtil解析失败");
                }
            }
        }else if(null!=shopExpress&&null!=shopExpress.getSnapshot()){
            try {
                PropertyCopyUtil.getInstance().copyPropertiesWithIgnores(shopExpressDto,shopExpress,"snapshot");
                shopExpressDto.setSnapshot(JsonUtils.readValue(shopExpress.getSnapshot(),Object.class));
            }catch (Exception e){
                throw new ServiceException(1001,"PropertyCopyUtil解析失败");
            }
        }
        return shopExpressDto;
    }

    private String getSnapshot(String shippedNo,String shippedCom){
        Dictionary dictionary = dictionaryService.getFirstByTypeAndDictKey(Constants.DICTIONARY_TYPE.EXPRESS,"EXPRESS_APPCODE");
        String host = "https://wuliu.market.alicloudapi.com";
        String path = "/kdi";
        String method = "GET";
        String appcode = dictionary.getDictValue();
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<>();
        querys.put("no", shippedNo);
        querys.put("type", shippedCom);
        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            return EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            return null;
        }
    }
}
