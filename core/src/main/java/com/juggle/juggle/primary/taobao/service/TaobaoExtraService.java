package com.juggle.juggle.primary.taobao.service;

import com.juggle.juggle.common.util.TaobaoClientUtil;
import com.juggle.juggle.framework.cache.meta.Cache;
import com.juggle.juggle.framework.common.errors.ServiceException;
import com.juggle.juggle.framework.common.utils.JsonUtils;
import com.juggle.juggle.framework.common.utils.MD5Utils;
import com.juggle.juggle.framework.common.utils.PropertyCopyUtil;
import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.framework.data.service.BaseCRUDService;
import com.juggle.juggle.framework.http.SyncHttpClient;
import com.juggle.juggle.framework.http.resp.impl.TextResponseHandler;
import com.juggle.juggle.primary.Constants;
import com.juggle.juggle.primary.setting.dao.DictionaryDao;
import com.juggle.juggle.primary.setting.model.Dictionary;
import com.juggle.juggle.primary.taobao.dao.*;
import com.juggle.juggle.primary.taobao.dto.SnapshotDto;
import com.juggle.juggle.primary.taobao.dto.TaobaoAlbum;
import com.juggle.juggle.primary.taobao.dto.TaobaoDetaily;
import com.juggle.juggle.primary.taobao.model.*;
import com.taobao.api.domain.NTbkShop;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.*;

@Service
public class TaobaoExtraService extends BaseCRUDService<TaobaoExtra> {
    @Autowired
    private TaobaoExtraDao dao;

    @Override
    protected IRepo<TaobaoExtra> getRepo() {
        return dao;
    }

    @Autowired
    private SyncHttpClient syncHttpClient;

    @Autowired
    private DictionaryDao dictionaryDao;

    @Autowired
    private TaobaoPopularDao taobaoPopularDao;

    @Autowired
    private TaobaoFavourableDao taobaoFavourableDao;

    @Autowired
    private TaobaoGoodsDao taobaoGoodsDao;

    @Autowired
    private TaobaoSelectionDao taobaoSelectionDao;

    @Autowired
    private TaobaoProductDao taobaoProductDao;

    @Autowired
    private TaobaoFlashDao taobaoFlashDao;

    @Cache
    public TaobaoDetaily readDetail(Long id, String sub){
        TaobaoDetaily detaily;
        switch (sub){
            case Constants.TAOBAO_SUB.POPULAR:
                detaily = getPopularDetaily(id,sub);
                break;
            case Constants.TAOBAO_SUB.FAVOURABLE:
                detaily = getFavourableDetaily(id,sub);
                break;
            case Constants.TAOBAO_SUB.GOODS:
                detaily = getGoodsDetaily(id,sub);
                break;
            case Constants.TAOBAO_SUB.SELECTION:
                detaily = getSelectionDetaily(id,sub);
                break;
            case Constants.TAOBAO_SUB.PRODUCT:
                detaily = getProductDetaily(id,sub);
                break;
            case Constants.TAOBAO_SUB.FLASH:
                detaily = getFlashDetaily(id,sub);
                break;
            default:throw new ServiceException(1001,"淘宝商品sub错误");
        }
        return detaily;
    }

    @Cache
    public TaobaoAlbum readAlbum(Long id, String sub){
        TaobaoExtra taobaoExtra = dao.findFirstBySubAndSubId(sub,id);
        if(null==taobaoExtra||StringUtils.isEmpty(taobaoExtra.getImages())){
            Dictionary dictionary = dictionaryDao.findFirstByTypeAndDictKey(Constants.DICTIONARY_TYPE.DINGDANXIA,"DINGDANXIA_API_KEY");
            String snapshot = getSnapshot(id,sub);
            SnapshotDto snapshotDto = JsonUtils.readValue(snapshot,SnapshotDto.class);
            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put("apikey", dictionary.getDictValue());
            hashMap.put("id", snapshotDto.getItemId().toString());
            hashMap.put("signature", MD5Utils.encript(signatureSort(hashMap)));
            TextResponseHandler handler = new TextResponseHandler();
            syncHttpClient.post(URI.create("http://api.tbk.dingdanxia.com/shop/product_get"),hashMap,handler,new HashMap<>());
            String data = handler.getData();
            JSONObject jsonObject = JSONObject.fromObject(data);
            int code = jsonObject.getInt("code");
            if(code==200){
                TaobaoAlbum taobaoAlbum = new TaobaoAlbum();
                List<String> images = new ArrayList<>();
                JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONObject("mobile_desc_info").getJSONObject("desc_list").getJSONArray("desc_fragment");
                for(int i=0;i<jsonArray.size();i++){
                    JSONObject jb = jsonArray.getJSONObject(i);
                    if(!jb.getString("content").contains("spaceball.gif")){
                        images.add(jb.getString("content"));
                    }
                }
                taobaoAlbum.setImages(images);
                if(null==taobaoExtra) {
                    taobaoExtra = new TaobaoExtra();
                }
                taobaoExtra.setSub(sub);
                taobaoExtra.setSubId(id);
                taobaoExtra.setImages(StringUtils.join(images,","));
                createOrUpdate(taobaoExtra);
                return taobaoAlbum;
            }else{
                throw new ServiceException(1001,jsonObject.getString("msg"));
            }
        }else{
            TaobaoAlbum taobaoAlbum = new TaobaoAlbum();
            List<String> images = Arrays.asList(StringUtils.split(taobaoExtra.getImages(),","));
            taobaoAlbum.setImages(images);
            return taobaoAlbum;
        }
    }

    public Map<String,String> tpwdCreate(Long id, String sub){
        TaobaoDetaily detaily;
        switch (sub){
            case Constants.TAOBAO_SUB.POPULAR:
                detaily = getPopularDetaily(id,sub);
                break;
            case Constants.TAOBAO_SUB.GOODS:
                detaily = getGoodsDetaily(id,sub);
                break;
            case Constants.TAOBAO_SUB.FAVOURABLE:
                detaily = getFavourableDetaily(id,sub);
                break;
            case Constants.TAOBAO_SUB.SELECTION:
                detaily = getSelectionDetaily(id,sub);
                break;
            case Constants.TAOBAO_SUB.PRODUCT:
                detaily = getProductDetaily(id,sub);
                break;
            case Constants.TAOBAO_SUB.FLASH:
                detaily = getFlashDetaily(id,sub);
                break;
            default:throw new ServiceException(1001,"淘宝商品sub错误");
        }
        TaobaoClientUtil taobaoClientUtil = new TaobaoClientUtil();
        Map<String,String> map = new HashMap<>();
        String tkl = taobaoClientUtil.grabTpwdCreate(detaily.getTitle(),detaily.getJumpUrl(),detaily.getPictUrl());
        map.put("tkl",tkl);
        map.put("url","http://web.hllaibei.com/share.html?id="+id+"&sub="+sub+"&tkl="+tkl);
        return map;
    }

    private TaobaoDetaily getPopularDetaily(Long id,String sub){
        TaobaoDetaily detaily = new TaobaoDetaily();
        TaobaoPopular taobaoPopular = taobaoPopularDao.findOne(id);
        try{
            PropertyCopyUtil.getInstance().copyProperties(detaily,taobaoPopular);
        }catch (Exception e){
            throw new ServiceException(1001,"PropertyCopyUtil解析失败");
        }
        return extendDetaily(detaily,taobaoPopular.getSnapshot(),sub);
    }

    private TaobaoDetaily getFavourableDetaily(Long id,String sub){
        TaobaoDetaily detaily = new TaobaoDetaily();
        TaobaoFavourable taobaoFavourable = taobaoFavourableDao.findOne(id);
        try{
            PropertyCopyUtil.getInstance().copyProperties(detaily,taobaoFavourable);
        }catch (Exception e){
            throw new ServiceException(1001,"PropertyCopyUtil解析失败");
        }
        return extendDetaily(detaily,taobaoFavourable.getSnapshot(),sub);
    }

    private TaobaoDetaily getGoodsDetaily(Long id,String sub){
        TaobaoDetaily detaily = new TaobaoDetaily();
        TaobaoGoods taobaoGoods = taobaoGoodsDao.findOne(id);
        try{
            PropertyCopyUtil.getInstance().copyProperties(detaily,taobaoGoods);
        }catch (Exception e){
            throw new ServiceException(1001,"PropertyCopyUtil解析失败");
        }
        return extendDetaily(detaily,taobaoGoods.getSnapshot(),sub);
    }

    private TaobaoDetaily getSelectionDetaily(Long id,String sub){
        TaobaoDetaily detaily = new TaobaoDetaily();
        TaobaoSelection taobaoSelection = taobaoSelectionDao.findOne(id);
        try{
            PropertyCopyUtil.getInstance().copyProperties(detaily,taobaoSelection);
        }catch (Exception e){
            throw new ServiceException(1001,"PropertyCopyUtil解析失败");
        }
        return extendDetaily(detaily,taobaoSelection.getSnapshot(),sub);
    }

    private TaobaoDetaily getProductDetaily(Long id,String sub){
        TaobaoDetaily detaily = new TaobaoDetaily();
        TaobaoProduct taobaoProduct = taobaoProductDao.findOne(id);
        try{
            PropertyCopyUtil.getInstance().copyProperties(detaily,taobaoProduct);
        }catch (Exception e){
            throw new ServiceException(1001,"PropertyCopyUtil解析失败");
        }
        return extendDetaily(detaily,taobaoProduct.getSnapshot(),sub);
    }

    private TaobaoDetaily getFlashDetaily(Long id,String sub){
        TaobaoDetaily detaily = new TaobaoDetaily();
        TaobaoFlash taobaoFlash = taobaoFlashDao.findOne(id);
        try{
            PropertyCopyUtil.getInstance().copyProperties(detaily,taobaoFlash);
        }catch (Exception e){
            throw new ServiceException(1001,"PropertyCopyUtil解析失败");
        }
        return extendDetaily(detaily,taobaoFlash.getSnapshot(),sub);
    }

    @Cache
    private TaobaoDetaily extendDetaily(TaobaoDetaily detaily,String snapshot,String sub){
        TaobaoExtra taobaoExtra = dao.findFirstBySubAndSubId(sub,detaily.getId());
        SnapshotDto snapshotDto = JsonUtils.readValue(snapshot,SnapshotDto.class);
        if(null==taobaoExtra||StringUtils.isEmpty(taobaoExtra.getShopPictUrl())){
            TaobaoClientUtil taobaoClientUtil = new TaobaoClientUtil();
            NTbkShop nTbkShop = taobaoClientUtil.grabShopGet(snapshotDto.getSellerId(),snapshotDto.getShopTitle());
            if(null==taobaoExtra){
                taobaoExtra = new TaobaoExtra();
            }
            taobaoExtra.setSub(sub);
            taobaoExtra.setSubId(detaily.getId());
            taobaoExtra.setShopPictUrl(nTbkShop.getPictUrl());
            createOrUpdate(taobaoExtra);
            detaily.setShopPictUrl(nTbkShop.getPictUrl());
            detaily.setSmallImages(snapshotDto.getSmallImages());
        }else{
            detaily.setShopPictUrl(taobaoExtra.getShopPictUrl());
            detaily.setSmallImages(snapshotDto.getSmallImages());
        }
        if(!StringUtils.isEmpty(snapshotDto.getCouponShareUrl())){
            detaily.setJumpUrl(snapshotDto.getCouponShareUrl());
        } else if(!StringUtils.isEmpty(snapshotDto.getCouponClickUrl())){
            detaily.setJumpUrl(snapshotDto.getCouponClickUrl());
        } else if(!StringUtils.isEmpty(snapshotDto.getClickUrl())){
            detaily.setJumpUrl(snapshotDto.getClickUrl());
        } else{
            detaily.setJumpUrl(snapshotDto.getUrl());
        }
        return detaily;
    }

    private static String signatureSort(HashMap<String, String> map){
        String sb = "";
        String[] key = new String[map.size()];
        int index = 0;
        for (String k : map.keySet()) {
            key[index] = k;
            index++;
        }
        Arrays.sort(key);
        for (String s : key) {
            sb += s + "=" + map.get(s) + "&";
        }
        sb = sb.substring(0, sb.length() - 1);
        try {
            sb = URLEncoder.encode(sb, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        sb = sb.replace("%3d", "=").replace("%26", "&");
        return sb;
    }

    private String getSnapshot(Long id,String sub){
        if(sub.equals(Constants.TAOBAO_SUB.FAVOURABLE)){
            TaobaoFavourable favourable = taobaoFavourableDao.findOne(id);
            return favourable.getSnapshot();
        }else if(sub.equals(Constants.TAOBAO_SUB.GOODS)){
            TaobaoGoods goods = taobaoGoodsDao.findOne(id);
            return goods.getSnapshot();
        }else if(sub.equals(Constants.TAOBAO_SUB.PRODUCT)){
            TaobaoProduct taobaoProduct = taobaoProductDao.findOne(id);
            return taobaoProduct.getSnapshot();
        }else if(sub.equals(Constants.TAOBAO_SUB.SELECTION)){
            TaobaoProduct product = taobaoProductDao.findOne(id);
            return product.getSnapshot();
        }else if(sub.equals(Constants.TAOBAO_SUB.POPULAR)){
            TaobaoPopular popular = taobaoPopularDao.findOne(id);
            return popular.getSnapshot();
        } else{
            TaobaoFlash flash = taobaoFlashDao.findOne(id);
            return flash.getSnapshot();
        }
    }
}
