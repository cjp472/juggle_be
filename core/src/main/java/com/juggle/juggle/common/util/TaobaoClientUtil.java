package com.juggle.juggle.common.util;

import com.juggle.juggle.common.data.TaobaoClientParams;
import com.juggle.juggle.framework.common.errors.ServiceException;
import com.juggle.juggle.framework.common.utils.ApplicationUtils;
import com.juggle.juggle.framework.common.utils.DateUtils;
import com.juggle.juggle.framework.common.utils.JsonUtils;
import com.juggle.juggle.primary.Constants;
import com.juggle.juggle.primary.app.dao.MemberDao;
import com.juggle.juggle.primary.app.dao.RewardDao;
import com.juggle.juggle.primary.app.model.Reward;
import com.juggle.juggle.primary.setting.dao.DictionaryDao;
import com.juggle.juggle.primary.setting.dto.RebateRatios;
import com.juggle.juggle.primary.setting.service.DictionaryService;
import com.juggle.juggle.primary.taobao.dao.*;
import com.juggle.juggle.primary.taobao.model.*;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.NTbkShop;
import com.taobao.api.domain.TbkFavorites;
import com.taobao.api.domain.UatmTbkItem;
import com.taobao.api.internal.util.WebUtils;
import com.taobao.api.request.*;
import com.taobao.api.response.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class TaobaoClientUtil {
    private TaobaoClient client;

    private TaobaoClientParams params;

    private String serverUrl = "https://eco.taobao.com/router/rest";

    private String authUrl = "https://oauth.taobao.com/token";

    private String redirectUri = "http://www.hllaibei.com";

    private RebateRatios rebateRatios;

    public TaobaoClientUtil() {
        DictionaryService dictionaryService = ApplicationUtils.getBean(DictionaryService.class);
        this.params = dictionaryService.getTaobaoDictionaries();
        this.rebateRatios = dictionaryService.getTaobaoRebateDictionaries();
        this.client = new DefaultTaobaoClient(serverUrl,params.getAppKey(),params.getAppSecret());
    }

    public void grabTaobaoGoods(String cat,Long typeId){
        int limitPage = 20;
        TaobaoGoodsDao taobaoGoodsDao = ApplicationUtils.getBean(TaobaoGoodsDao.class);
        List<TaobaoGoods> taobaoGoods = new ArrayList<>();
        TbkDgMaterialOptionalRequest req = new TbkDgMaterialOptionalRequest();
        req.setAdzoneId(params.getAdzoneId());
        req.setCat(cat);
        req.setPageSize(10L);
        try {
            TbkDgMaterialOptionalResponse rsp = getMaterialOptional(req);
            if(!rsp.isSuccess()){
                return;
            }
            List<TbkDgMaterialOptionalResponse.MapData> mapDataList = rsp.getResultList();
            taobaoGoods = goodsToList(mapDataList, taobaoGoods, typeId);
            if(taobaoGoods.size()>0){
                taobaoGoodsDao.saveAll(taobaoGoods);
            }
            int totalPage = (int)Math.ceil((double) rsp.getTotalResults() / (double) 10);
            if (totalPage > 1) {
                if(totalPage>limitPage){
                    totalPage = limitPage;
                }
                for (int i = 2; i <= totalPage; i++) {
                    req.setPageNo(Long.valueOf(i));
                    rsp = getMaterialOptional(req);
                    if(!rsp.isSuccess()){
                        break;
                    }
                    mapDataList = rsp.getResultList();
                    taobaoGoods = new ArrayList<>();
                    taobaoGoods = goodsToList(mapDataList, taobaoGoods, typeId);
                    if(taobaoGoods.size()>0){
                        taobaoGoodsDao.saveAll(taobaoGoods);
                    }
                }
            }
        } catch (ApiException ex) {
            throw new ServiceException(1001, "淘宝接口调用失败");
        }
    }

    public void grabTaobaoProduct(TaobaoBrand taobaoBrand) {
        int limitPage = 20;
        TaobaoProductDao taobaoProductDao = ApplicationUtils.getBean(TaobaoProductDao.class);
        List<TaobaoProduct> taobaoProducts = new ArrayList<>();
        TbkDgMaterialOptionalRequest req = new TbkDgMaterialOptionalRequest();
        req.setAdzoneId(params.getAdzoneId());
        req.setQ(taobaoBrand.getName());
        req.setPageSize(100L);
        try {
            TbkDgMaterialOptionalResponse rsp = getMaterialOptional(req);
            if(!rsp.isSuccess()){
                return;
            }
            List<TbkDgMaterialOptionalResponse.MapData> mapDataList = rsp.getResultList();
            taobaoProducts = productToList(mapDataList, taobaoProducts, taobaoBrand);
            if(taobaoProducts.size()>0){
                taobaoProductDao.saveAll(taobaoProducts);
            }
            int totalPage = (int)Math.ceil((double) rsp.getTotalResults() / (double) 100);
            if (totalPage > 1) {
                if(totalPage>limitPage){
                    totalPage = limitPage;
                }
                for (int i = 2; i <= totalPage; i++) {
                    req.setPageNo(Long.valueOf(i));
                    rsp = getMaterialOptional(req);
                    if(!rsp.isSuccess()){
                        break;
                    }
                    mapDataList = rsp.getResultList();
                    taobaoProducts = new ArrayList<>();
                    taobaoProducts = productToList(mapDataList, taobaoProducts, taobaoBrand);
                    if(taobaoProducts.size()>0){
                        taobaoProductDao.saveAll(taobaoProducts);
                    }
                }
            }
        } catch (ApiException ex) {
            throw new ServiceException(1001, "淘宝接口调用失败");
        }
    }

    public void grabTaobaoFavourable(String type) {
        int limitPage = 20;
        DictionaryDao dictionaryDao = ApplicationUtils.getBean(DictionaryDao.class);
        TaobaoFavourableDao taobaoFavourableDao = ApplicationUtils.getBean(TaobaoFavourableDao.class);
        List<TaobaoFavourable> taobaoFavourables = new ArrayList<>();
        TbkDgOptimusMaterialRequest req = new TbkDgOptimusMaterialRequest();
        req.setAdzoneId(params.getAdzoneId());
        req.setMaterialId(Long.valueOf(dictionaryDao.findFirstByTypeAndDictKey(Constants.DICTIONARY_TYPE.TAOBAO,"FAVOURABLE_".concat(type)).getDictValue()));
        req.setPageSize(100L);
        try{
            TbkDgOptimusMaterialResponse rsp = getOptimusMaterial(req);
            if(!rsp.isSuccess()){
                return;
            }
            List<TbkDgOptimusMaterialResponse.MapData> mapDataList = rsp.getResultList();
            taobaoFavourables = mapDataToList(mapDataList, taobaoFavourables, type);
            if(taobaoFavourables.size()>0){
                taobaoFavourableDao.saveAll(taobaoFavourables);
            }
            for (int i = 2; i <= limitPage; i++) {
                req.setPageNo(Long.valueOf(i));
                rsp = getOptimusMaterial(req);
                if(!rsp.isSuccess()){
                    break;
                }
                mapDataList = rsp.getResultList();
                taobaoFavourables = new ArrayList<>();
                taobaoFavourables = mapDataToList(mapDataList, taobaoFavourables, type);
                if(taobaoFavourables.size()>0){
                    taobaoFavourableDao.saveAll(taobaoFavourables);
                }
            }
        }catch (ApiException ex){
            throw new ServiceException(1001,"淘宝接口调用失败");
        }
    }

    public void grabTaobaoPopular(String type){
        int limitPage = 20;
        DictionaryDao dictionaryDao = ApplicationUtils.getBean(DictionaryDao.class);
        TaobaoPopularDao taobaoPopularDao = ApplicationUtils.getBean(TaobaoPopularDao.class);
        List<TaobaoPopular> taobaoPopulars = new ArrayList<>();
        TbkDgMaterialOptionalRequest req = new TbkDgMaterialOptionalRequest();
        req.setAdzoneId(params.getAdzoneId());
        req.setPageSize(100L);
        if(type.equals(Constants.TAOBAO_POPULAR_TYPE.TYPE2)){
            req.setNeedFreeShipment(true);
            req.setStartPrice(5L);
            req.setEndPrice(10L);
        }
        req.setCat(dictionaryDao.findFirstByTypeAndDictKey(Constants.DICTIONARY_TYPE.TAOBAO,"POPULAR_".concat(type)).getDictValue());
        try {
            TbkDgMaterialOptionalResponse rsp = getMaterialOptional(req);
            if(!rsp.isSuccess()){
                return;
            }
            List<TbkDgMaterialOptionalResponse.MapData> mapDataList = rsp.getResultList();
            taobaoPopulars = popularToList(mapDataList, taobaoPopulars, type);
            if(taobaoPopulars.size()>0){
                taobaoPopularDao.saveAll(taobaoPopulars);
            }
            int totalPage = (int)Math.ceil((double) rsp.getTotalResults() / (double) 100);
            if (totalPage > 1) {
                if(totalPage>limitPage){
                    totalPage = limitPage;
                }
                for (int i = 2; i <= totalPage; i++) {
                    req.setPageNo(Long.valueOf(i));
                    rsp = getMaterialOptional(req);
                    if(!rsp.isSuccess()){
                        break;
                    }
                    mapDataList = rsp.getResultList();
                    taobaoPopulars = new ArrayList<>();
                    taobaoPopulars = popularToList(mapDataList, taobaoPopulars, type);
                    if(taobaoPopulars.size()>0){
                        taobaoPopularDao.saveAll(taobaoPopulars);
                    }
                }
            }
        } catch (ApiException ex) {
            throw new ServiceException(1001, "淘宝接口调用失败");
        }
    }

    public void grabJuTqgGet(Date startTime,Date endTime){
        int limitPage = 5;
        TaobaoFlashDao taobaoFlashDao = ApplicationUtils.getBean(TaobaoFlashDao.class);
        TbkJuTqgGetRequest req = new TbkJuTqgGetRequest();
        Long pageNo = Long.valueOf(1);
        req.setAdzoneId(params.getAdzoneId());
        req.setFields("click_url,pic_url,reserve_price,zk_final_price,total_amount,sold_num,title,category_name,start_time,end_time");
        req.setPageSize(40L);
        req.setStartTime(startTime);
        req.setEndTime(endTime);
        List<TaobaoFlash> taobaoFlashes = new ArrayList<>();
        try{
            TbkJuTqgGetResponse rsp = getJuTqgGet(req);
            if(rsp.isSuccess()!=true){
                return;
            }
            List<TbkJuTqgGetResponse.Results> results = rsp.getResults();
            taobaoFlashes = juTqgGetItemToList(results,taobaoFlashes);
            if(taobaoFlashes.size()>0){
                taobaoFlashDao.saveAll(taobaoFlashes);
            }
            while (pageNo.compareTo(Long.valueOf(limitPage))<0) {
                pageNo = pageNo + 1;
                req.setPageNo(Long.valueOf(pageNo));
                rsp = getJuTqgGet(req);
                if(!rsp.isSuccess()){
                    break;
                }
                results = rsp.getResults();
                taobaoFlashes = new ArrayList<>();
                taobaoFlashes = juTqgGetItemToList(results,taobaoFlashes);
                if(taobaoFlashes.size()>0){
                    taobaoFlashDao.saveAll(taobaoFlashes);
                }
            }
        }catch (Exception e){
            throw new ServiceException(1001,"淘宝接口调用失败");
        }
    }

    public void grabJuTqgGetToUpdateSoldNum(Date startTime,Date endTime){
        int limitPage = 5;
        TaobaoFlashDao taobaoFlashDao = ApplicationUtils.getBean(TaobaoFlashDao.class);
        TbkJuTqgGetRequest req = new TbkJuTqgGetRequest();
        Long pageNo = Long.valueOf(1);
        req.setPageSize(40L);
        req.setAdzoneId(params.getAdzoneId());
        req.setFields("num_iid,sold_num");
        req.setStartTime(startTime);
        req.setEndTime(endTime);
        List<TaobaoFlash> taobaoFlashes = new ArrayList<>();
        try{
            TbkJuTqgGetResponse rsp = getJuTqgGet(req);
            if(!rsp.isSuccess()){
                return;
            }
            List<TbkJuTqgGetResponse.Results> results = rsp.getResults();
            taobaoFlashes = juTqgGetItemToList(results,taobaoFlashes);
            if(taobaoFlashes.size()>0){
                taobaoFlashDao.saveAll(taobaoFlashes);
            }
            while (pageNo.compareTo(Long.valueOf(limitPage))<0) {
                pageNo = pageNo + 1;
                req.setPageNo(Long.valueOf(pageNo));
                rsp = getJuTqgGet(req);
                if(!rsp.isSuccess()){
                    break;
                }
                results = rsp.getResults();
                List<TaobaoFlash> flashList = new ArrayList<>();
                for (TbkJuTqgGetResponse.Results result : results) {
                    TaobaoFlash taobaoFlash = taobaoFlashDao.findFirstByItemId(result.getNumIid());
                    if(null!=taobaoFlash){
                        taobaoFlash.setSoldNum(result.getSoldNum().intValue());
                        flashList.add(taobaoFlash);
                    }
                }
                taobaoFlashDao.saveAll(flashList);
            }
        }catch (Exception e){
            throw new ServiceException(1001,"淘宝接口调用失败");
        }
    }

    public void grabOrderDetailsGet(String startTime,String endTime){
        TaobaoOrderDao taobaoOrderDao = ApplicationUtils.getBean(TaobaoOrderDao.class);
        MemberDao memberDao = ApplicationUtils.getBean(MemberDao.class);
        TbkOrderDetailsGetRequest req = new TbkOrderDetailsGetRequest();
        req.setPageSize(100L);
        req.setStartTime(startTime);
        req.setEndTime(endTime);
        req.setPageNo(1L);
        req.setOrderScene(2L);
        List<TaobaoOrder> taobaoOrders;
        boolean hasNext = true;
        try{
            while (hasNext){
                TbkOrderDetailsGetResponse rsp = getOrderDetailsGet(req);
                if(!rsp.isSuccess()){
                    break;
                }
                List<TbkOrderDetailsGetResponse.PublisherOrderDto> publisherOrderDtos = rsp.getData().getResults();
                taobaoOrders = new ArrayList<>();
                taobaoOrders = publisherOrderToList(publisherOrderDtos,taobaoOrders,memberDao);
                if(taobaoOrders.size()>0){
                    taobaoOrderDao.saveAll(taobaoOrders);
                }
                hasNext = rsp.getData().getHasNext();
            }
        }catch (Exception e){
            throw new ServiceException(1001, "淘宝接口调用失败");
        }
    }

    public void grabRelationRefund(){
        TaobaoOrderDao taobaoOrderDao = ApplicationUtils.getBean(TaobaoOrderDao.class);
        RewardDao rewardDao = ApplicationUtils.getBean(RewardDao.class);
        Date today = new Date();
        Date startTime = DateUtils.goPast(today,1296000);
        Long pageNo = 1L;
        TbkRelationRefundRequest req = new TbkRelationRefundRequest();
        TbkRelationRefundRequest.TopApiRefundRptOption option = new TbkRelationRefundRequest.TopApiRefundRptOption();
        option.setPageSize(100L);
        option.setSearchType(1L);
        option.setRefundType(1L);
        option.setStartTime(startTime);
        option.setBizType(1L);
        req.setSearchOption(option);
        try{
            while (true){
                option.setPageNo(pageNo);
                req.setSearchOption(option);
                TbkRelationRefundResponse rsp = client.execute(req);
                if(rsp.isSuccess()){
                    List<TbkRelationRefundResponse.Result> results = rsp.getResult().getData().getResults();
                    for (TbkRelationRefundResponse.Result result : results) {
                        if(result.getRefundStatus().equals(4L)){
                            TaobaoOrder taobaoOrder = taobaoOrderDao.findFirstByTradeId(result.getTbTradeParentId().toString());
                            if(null!=taobaoOrder&&!taobaoOrder.getTkStatus().equals(13)){
                                taobaoOrder.setTkStatus(13);
                                taobaoOrderDao.save(taobaoOrder);
                                List<Reward> rewards = rewardDao.findAllByOrderTypeAndOrderId(Constants.REWARD_ORDER_TYPE.TAOBAO,taobaoOrder.getId());
                                for (Reward reward : rewards) {
                                    reward.setStatus(Constants.REWARD_STATUS.REFUND);
                                    rewardDao.save(reward);
                                }
                            }
                        }
                    }
                    pageNo++;
                }else{
                    break;
                }
            }
        }catch (Exception e){
            throw new ServiceException(1001,"淘宝接口调用失败");
        }
    }

    public String grabAccessToken(String code){
        String url = authUrl;
        Map<String,String> props=new HashMap<>();
        props.put("grant_type","authorization_code");
        props.put("code",code);
        props.put("client_id",params.getAppKey());
        props.put("client_secret",params.getAppSecret());
        props.put("redirect_uri",redirectUri);
        props.put("view","web");
        try{
            String result = WebUtils.doPost(url, props, 30000, 30000);
            JSONObject jsonObject = JSONObject.fromObject(result);
            return jsonObject.getString("access_token");
        }catch(IOException e){
            throw new ServiceException(1001,"淘宝接口调用失败");
        }
    }

    public TbkScPublisherInfoSaveResponse grabPublisherInfoSave(String sessionKey){
        TbkScPublisherInfoSaveRequest req = new TbkScPublisherInfoSaveRequest();
        req.setInviterCode("DYDQW9");
        req.setInfoType(1L);
        try{
            TbkScPublisherInfoSaveResponse rsp = client.execute(req, sessionKey);
            return rsp;
        }catch(Exception e){
            throw new ServiceException(1001,"淘宝接口调用失败");
        }
    }

    public String grabTpwdCreate(String text,String url,String logo){
        TbkTpwdCreateRequest req = new TbkTpwdCreateRequest();
        req.setText(text);
        if(url.startsWith("//")){
            req.setUrl("https:"+url);
        }else{
            req.setUrl(url);
        }
        if(!StringUtils.isEmpty(logo)){
            req.setLogo(logo);
        }
        try{
            TbkTpwdCreateResponse rsp = client.execute(req);
            return rsp.getData().getModel();
        }catch (Exception e){
            throw new ServiceException(1001,"淘宝接口调用失败");
        }
    }

    public HashMap<String,Long> grabUatmFavoritesGet(){
        List<String> favorites = new ArrayList<>();
        favorites.add("热销精品");
        favorites.add("今日推荐");
        favorites.add("日常快消");
        favorites.add("拼团");
        HashMap<String,Long> hashMap = new HashMap<>();
        TbkUatmFavoritesGetRequest req = new TbkUatmFavoritesGetRequest();
        req.setPageNo(1L);
        req.setPageSize(100L);
        req.setFields("favorites_title,favorites_id,type");
        req.setType(-1L);
        try{
            TbkUatmFavoritesGetResponse rsp = client.execute(req);
            List<TbkFavorites> tbkFavorites = rsp.getResults();
            for (TbkFavorites tbkFavorite : tbkFavorites) {
                if(tbkFavorite.getFavoritesTitle().contains("热销精品")){
                    hashMap.put("TYPE1",tbkFavorite.getFavoritesId());
                }else if(tbkFavorite.getFavoritesTitle().contains("今日推荐")){
                    hashMap.put("TYPE2",tbkFavorite.getFavoritesId());
                }else if(tbkFavorite.getFavoritesTitle().contains("日用快消")){
                    hashMap.put("TYPE3",tbkFavorite.getFavoritesId());
                }else if(tbkFavorite.getFavoritesTitle().contains("拼团")){
                    hashMap.put("TYPE4",tbkFavorite.getFavoritesId());
                }else{
                    continue;
                }
                if(hashMap.size()==4){
                    break;
                }
            }
            return hashMap;
        }catch (Exception e){
            throw new ServiceException(1001,"淘宝接口调用失败");
        }
    }

    public void grabUatmFavoritesItemGet(HashMap<String,Long> hashMap){
        TaobaoSelectionDao taobaoSelectionDao = ApplicationUtils.getBean(TaobaoSelectionDao.class);
        TbkUatmFavoritesItemGetRequest req = new TbkUatmFavoritesItemGetRequest();
        req.setPlatform(1L);
        req.setPageSize(100L);
        req.setAdzoneId(params.getAdzoneId());
        req.setPageNo(1L);
        req.setFields("num_iid,coupon_click_url,click_url,title,pict_url,small_images,reserve_price,zk_final_price,user_type,provcity,item_url,seller_id,volume,coupon_info,nick,shop_title,zk_final_price_wap,event_start_time,event_end_time,tk_rate,status,type");
        try{
            Iterator iter = hashMap.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                req.setFavoritesId((Long)entry.getValue());
                TbkUatmFavoritesItemGetResponse rsp = client.execute(req);
                if(!rsp.isSuccess()){
                    break;
                }
                List<UatmTbkItem> uatmTbkItems = rsp.getResults();
                List<TaobaoSelection> selections = new ArrayList<>();
                selections = uatmTbkItemToList(uatmTbkItems,selections,entry.getKey().toString());
                taobaoSelectionDao.saveAll(selections);
            }
        }catch (Exception e){
            throw new ServiceException(1001,"淘宝接口调用失败");
        }
    }

    public TbkItemInfoGetResponse.NTbkItem grabItemInfoGet(Long numIid){
        TaobaoClient client = new DefaultTaobaoClient(serverUrl, params.getAppKey(), params.getAppSecret());
        TbkItemInfoGetRequest req = new TbkItemInfoGetRequest();
        req.setNumIids(numIid.toString());
        try{
            TbkItemInfoGetResponse rsp = client.execute(req);
            TbkItemInfoGetResponse.NTbkItem item = rsp.getResults().get(0);
            return item;
        }catch (Exception e){
            throw new ServiceException(1001,"淘宝接口调用失败");
        }
    }

    public NTbkShop grabShopGet(Long userId,String shopTitle){
        TaobaoClient client = new DefaultTaobaoClient(serverUrl,params.getAppKey(), params.getAppSecret());
        TbkShopGetRequest req = new TbkShopGetRequest();
        req.setFields("user_id,pict_url");
        req.setQ(shopTitle);
        NTbkShop nTbkShop = null;
        try{
            TbkShopGetResponse rsp = client.execute(req);
            List<NTbkShop> shops = rsp.getResults();
            for (NTbkShop shop : shops) {
                if(userId.equals(shop.getUserId())){
                    nTbkShop = shop;
                    break;
                }
            }
            return nTbkShop;
        }catch (Exception e){
            throw new ServiceException(1001,"淘宝接口调用失败");
        }
    }

    private TbkDgMaterialOptionalResponse getMaterialOptional(TbkDgMaterialOptionalRequest req) throws ApiException{
        TbkDgMaterialOptionalResponse rsp = client.execute(req);
        return rsp;
    }

    private TbkDgOptimusMaterialResponse getOptimusMaterial(TbkDgOptimusMaterialRequest req) throws ApiException{
        TbkDgOptimusMaterialResponse rsp = client.execute(req);
        return rsp;
    }

    private TbkJuTqgGetResponse getJuTqgGet(TbkJuTqgGetRequest req) throws ApiException{
        TbkJuTqgGetResponse rsp = client.execute(req);
        return rsp;
    }

    private TbkOrderDetailsGetResponse getOrderDetailsGet(TbkOrderDetailsGetRequest req) throws ApiException{
        TbkOrderDetailsGetResponse rsp = client.execute(req);
        return rsp;
    }

    private List<TaobaoGoods> goodsToList(List<TbkDgMaterialOptionalResponse.MapData> mapDataList,List<TaobaoGoods> taobaoGoods,Long typeId){
        if (null != mapDataList && mapDataList.size() > 0) {
            for (TbkDgMaterialOptionalResponse.MapData mapData : mapDataList) {
                    TaobaoGoods goods = new TaobaoGoods();
                    goods.setTypeId(typeId);
                    goods.setItemId(mapData.getItemId());
                    goods.setTitle(mapData.getTitle());
                    goods.setReservePrice(new BigDecimal(mapData.getReservePrice()));
                    goods.setZkFinalPrice(new BigDecimal(mapData.getZkFinalPrice()));
                    goods.setVolume(mapData.getVolume());
                    goods.setSnapshot(JsonUtils.writeValue(mapData));
                    goods.setPictUrl(mapData.getPictUrl());
                    goods.setUserType(mapData.getUserType().intValue());
                    goods.setPrimaryReward(getMaterialRatioReward(new BigDecimal(mapData.getZkFinalPrice()),new BigDecimal(mapData.getCommissionRate()),rebateRatios.getPrimaryRatio()));
                    goods.setSecondaryReward(getMaterialRatioReward(new BigDecimal(mapData.getZkFinalPrice()),new BigDecimal(mapData.getCommissionRate()),rebateRatios.getSecondaryRatio()));
                    goods.setShopTitle(getShopTitle(mapData.getShopTitle(),mapData.getNick()));
                    if(!StringUtils.isEmpty(mapData.getCouponAmount())){
                        goods.setCouponAmount(new BigDecimal(mapData.getCouponAmount()));
                    }
                    taobaoGoods.add(goods);
                }
        }
        return taobaoGoods;
    }

    private List<TaobaoProduct> productToList(List<TbkDgMaterialOptionalResponse.MapData> mapDataList, List<TaobaoProduct> taobaoProducts, TaobaoBrand taobaoBrand) {
        if (null != mapDataList && mapDataList.size() > 0) {
            for (TbkDgMaterialOptionalResponse.MapData mapData : mapDataList) {
                if (mapData.getShopTitle().equals(taobaoBrand.getStoreName())) {
                    TaobaoProduct taobaoProduct = new TaobaoProduct();
                    taobaoProduct.setCategoryId(taobaoBrand.getCategoryId());
                    taobaoProduct.setBrandId(taobaoBrand.getId());
                    taobaoProduct.setItemId(mapData.getItemId());
                    taobaoProduct.setTitle(mapData.getTitle());
                    taobaoProduct.setReservePrice(new BigDecimal(mapData.getReservePrice()));
                    taobaoProduct.setZkFinalPrice(new BigDecimal(mapData.getZkFinalPrice()));
                    taobaoProduct.setVolume(mapData.getVolume());
                    taobaoProduct.setPrimaryReward(getMaterialRatioReward(new BigDecimal(mapData.getZkFinalPrice()),new BigDecimal(mapData.getCommissionRate()),rebateRatios.getPrimaryRatio()));
                    taobaoProduct.setSecondaryReward(getMaterialRatioReward(new BigDecimal(mapData.getZkFinalPrice()),new BigDecimal(mapData.getCommissionRate()),rebateRatios.getSecondaryRatio()));
                    taobaoProduct.setSnapshot(JsonUtils.writeValue(mapData));
                    taobaoProduct.setPictUrl(mapData.getPictUrl());
                    taobaoProduct.setUserType(mapData.getUserType().intValue());
                    taobaoProduct.setShopTitle(getShopTitle(mapData.getShopTitle(),mapData.getNick()));
                    if(!StringUtils.isEmpty(mapData.getCouponAmount())){
                        taobaoProduct.setCouponAmount(new BigDecimal(mapData.getCouponAmount()));
                    }
                    taobaoProducts.add(taobaoProduct);
                }
            }
        }
        return taobaoProducts;
    }

    private List<TaobaoPopular> popularToList(List<TbkDgMaterialOptionalResponse.MapData> mapDataList, List<TaobaoPopular> taobaoPopulars, String type) {
        if (null != mapDataList && mapDataList.size() > 0) {
            for (TbkDgMaterialOptionalResponse.MapData mapData : mapDataList) {
                    TaobaoPopular taobaoPopular = new TaobaoPopular();
                    taobaoPopular.setType(type);
                    taobaoPopular.setItemId(mapData.getItemId());
                    taobaoPopular.setTitle(mapData.getTitle());
                    taobaoPopular.setZkFinalPrice(new BigDecimal(mapData.getZkFinalPrice()));
                    taobaoPopular.setReservePrice(new BigDecimal(mapData.getReservePrice()));
                    taobaoPopular.setVolume(mapData.getVolume());
                    taobaoPopular.setPrimaryReward(getMaterialRatioReward(new BigDecimal(mapData.getZkFinalPrice()),new BigDecimal(mapData.getCommissionRate()),rebateRatios.getPrimaryRatio()));
                    taobaoPopular.setSecondaryReward(getMaterialRatioReward(new BigDecimal(mapData.getZkFinalPrice()),new BigDecimal(mapData.getCommissionRate()),rebateRatios.getSecondaryRatio()));
                    taobaoPopular.setPictUrl(mapData.getPictUrl());
                    taobaoPopular.setSnapshot(JsonUtils.writeValue(mapData));
                    taobaoPopular.setUserType(mapData.getUserType().intValue());
                    taobaoPopular.setShopTitle(getShopTitle(mapData.getShopTitle(),mapData.getNick()));
                    if(!StringUtils.isEmpty(mapData.getCouponAmount())){
                        taobaoPopular.setCouponAmount(new BigDecimal(mapData.getCouponAmount()));
                    }
                    taobaoPopulars.add(taobaoPopular);
            }
        }
        return taobaoPopulars;
    }

    private List<TaobaoFavourable> mapDataToList(List<TbkDgOptimusMaterialResponse.MapData> mapDataList, List<TaobaoFavourable> taobaoFavourables, String type) {
        if (null != mapDataList && mapDataList.size() > 0) {
            for (TbkDgOptimusMaterialResponse.MapData mapData : mapDataList) {
                TaobaoFavourable taobaoFavourable = new TaobaoFavourable();
                taobaoFavourable.setType(type);
                taobaoFavourable.setItemId(mapData.getItemId());
                taobaoFavourable.setTitle(mapData.getTitle());
                if(!StringUtils.isEmpty(mapData.getReservePrice())){
                    taobaoFavourable.setReservePrice(new BigDecimal(mapData.getReservePrice()));
                }else{
                    taobaoFavourable.setReservePrice(new BigDecimal(mapData.getZkFinalPrice()));
                }
                taobaoFavourable.setZkFinalPrice(new BigDecimal(mapData.getZkFinalPrice()));
                taobaoFavourable.setVolume(mapData.getVolume());
                taobaoFavourable.setSnapshot(JsonUtils.writeValue(mapData));
                taobaoFavourable.setPrimaryReward(getOptimusRatioReward(new BigDecimal(mapData.getZkFinalPrice()),new BigDecimal(mapData.getCommissionRate()),rebateRatios.getPrimaryRatio()));
                taobaoFavourable.setSecondaryReward(getOptimusRatioReward(new BigDecimal(mapData.getZkFinalPrice()),new BigDecimal(mapData.getCommissionRate()),rebateRatios.getSecondaryRatio()));
                taobaoFavourable.setUserType(mapData.getUserType().intValue());
                taobaoFavourable.setPictUrl(mapData.getPictUrl());
                taobaoFavourable.setShopTitle(getShopTitle(mapData.getShopTitle(),mapData.getNick()));
                if(null!=mapData.getCouponAmount()&&mapData.getCouponAmount().intValue()>0){
                    taobaoFavourable.setCouponAmount(BigDecimal.valueOf(mapData.getCouponAmount().intValue()));
                }
                taobaoFavourables.add(taobaoFavourable);
            }
        }
        return taobaoFavourables;
    }

    private List<TaobaoSelection> uatmTbkItemToList(List<UatmTbkItem> uatmTbkItemList,List<TaobaoSelection> taobaoSelections,String type){
        if(null != uatmTbkItemList && uatmTbkItemList.size() > 0){
            for (UatmTbkItem uatmTbkItem : uatmTbkItemList) {
                TaobaoSelection selection = new TaobaoSelection();
                selection.setType(type);
                selection.setItemId(uatmTbkItem.getNumIid());
                selection.setTitle(uatmTbkItem.getTitle());
                selection.setPictUrl(uatmTbkItem.getPictUrl());
                selection.setReservePrice(new BigDecimal(uatmTbkItem.getReservePrice()));
                selection.setZkFinalPrice(new BigDecimal(uatmTbkItem.getZkFinalPrice()));
                selection.setSnapshot(JsonUtils.writeValue(uatmTbkItem));
                selection.setVolume(uatmTbkItem.getVolume());
                selection.setUserType(uatmTbkItem.getUserType().intValue());
                selection.setPrimaryReward(getUatmTbkRatioReward(new BigDecimal(uatmTbkItem.getZkFinalPrice()),new BigDecimal(uatmTbkItem.getTkRate()),rebateRatios.getPrimaryRatio()));
                selection.setSecondaryReward(getUatmTbkRatioReward(new BigDecimal(uatmTbkItem.getZkFinalPrice()),new BigDecimal(uatmTbkItem.getTkRate()),rebateRatios.getPrimaryRatio()));
                selection.setShopTitle(getShopTitle(uatmTbkItem.getShopTitle(),uatmTbkItem.getNick()));
                if(!StringUtils.isEmpty(uatmTbkItem.getCouponInfo())){
                    selection.setCouponAmount(new BigDecimal(uatmTbkItem.getCouponInfo().split("减")[1].split("元")[0]));
                }
                taobaoSelections.add(selection);
            }
        }
        return taobaoSelections;
    }

    private List<TaobaoFlash> juTqgGetItemToList(List<TbkJuTqgGetResponse.Results> results,List<TaobaoFlash> taobaoFlashes) throws ApiException{
        if(null != results && results.size() > 0){
            for (TbkJuTqgGetResponse.Results result : results) {
                TaobaoFlash flash = new TaobaoFlash();
                flash.setItemId(result.getNumIid());
                flash.setTitle(result.getTitle());
                flash.setPictUrl(result.getPicUrl());
                flash.setReservePrice(new BigDecimal(result.getReservePrice()));
                flash.setZkFinalPrice(new BigDecimal(result.getZkFinalPrice()));
                flash.setTotalAmount(result.getTotalAmount().intValue());
                flash.setSoldNum(result.getSoldNum().intValue());
                try{
                    flash.setStartTime(DateUtils.parse(result.getStartTime(),DateUtils.DATETIME_FORMAT));
                    flash.setEndTime(DateUtils.parse(result.getEndTime(),DateUtils.DATETIME_FORMAT));
                    String time = DateUtils.format(flash.getStartTime(),DateUtils.TIME_FORMAT);
                    if(time.compareTo(Constants.TAOBAO_FLASH_RUSH_TIME.ZERO)>=0&&time.compareTo(Constants.TAOBAO_FLASH_RUSH_TIME.EIGHT)<0){
                        flash.setRushTime(Constants.TAOBAO_FLASH_RUSH_TIME.ZERO);
                    }else if(time.compareTo(Constants.TAOBAO_FLASH_RUSH_TIME.EIGHT)>=0&&time.compareTo(Constants.TAOBAO_FLASH_RUSH_TIME.TWELVE)<0){
                        flash.setRushTime(Constants.TAOBAO_FLASH_RUSH_TIME.EIGHT);
                    }else if(time.compareTo(Constants.TAOBAO_FLASH_RUSH_TIME.TWELVE)>=0&&time.compareTo(Constants.TAOBAO_FLASH_RUSH_TIME.SIXTEEN)<0){
                        flash.setRushTime(Constants.TAOBAO_FLASH_RUSH_TIME.TWELVE);
                    }else if(time.compareTo(Constants.TAOBAO_FLASH_RUSH_TIME.SIXTEEN)>=0&&time.compareTo(Constants.TAOBAO_FLASH_RUSH_TIME.TWENTY_ONE)<0){
                        flash.setRushTime(Constants.TAOBAO_FLASH_RUSH_TIME.SIXTEEN);
                    }else{
                        flash.setRushTime(Constants.TAOBAO_FLASH_RUSH_TIME.TWENTY_ONE);
                    }
                }catch (Throwable t){
                    throw new ServiceException(1001,t.getMessage());
                }
                TbkDgMaterialOptionalRequest req = new TbkDgMaterialOptionalRequest();
                req.setAdzoneId(params.getAdzoneId());
                req.setQ(result.getTitle());
                req.setPageSize(100L);
                TbkDgMaterialOptionalResponse.MapData mapData = matchMaterialOptional(req,result.getNumIid());
                if(null != mapData){
                    flash.setSnapshot(JsonUtils.writeValue(mapData));
                    flash.setPrimaryReward(getMaterialRatioReward(new BigDecimal(mapData.getZkFinalPrice()),new BigDecimal(mapData.getCommissionRate()),rebateRatios.getPrimaryRatio()));
                    flash.setSecondaryReward(getMaterialRatioReward(new BigDecimal(mapData.getZkFinalPrice()),new BigDecimal(mapData.getCommissionRate()),rebateRatios.getSecondaryRatio()));
                    flash.setVolume(mapData.getVolume());
                    flash.setUserType(mapData.getUserType().intValue());
                    flash.setShopTitle(getShopTitle(mapData.getShopTitle(),mapData.getNick()));
                    if(!StringUtils.isEmpty(mapData.getCouponAmount())){
                        flash.setCouponAmount(new BigDecimal(mapData.getCouponAmount()));
                    }
                }else{
                    continue;
                }
                taobaoFlashes.add(flash);
            }
        }
        return taobaoFlashes;
    }

    private List<TaobaoOrder> publisherOrderToList(List<TbkOrderDetailsGetResponse.PublisherOrderDto> publisherOrderDtos,List<TaobaoOrder> taobaoOrders,MemberDao memberDao){
        TaobaoOrderDao taobaoOrderDao = ApplicationUtils.getBean(TaobaoOrderDao.class);
        if(null != publisherOrderDtos && publisherOrderDtos.size() > 0){
            for (TbkOrderDetailsGetResponse.PublisherOrderDto orderDto : publisherOrderDtos) {
                if(memberDao.existsByRelationId(orderDto.getRelationId())){
                    TaobaoOrder taobaoOrder = taobaoOrderDao.findFirstByTradeId(orderDto.getTradeId());
                    if(null==taobaoOrder){
                        taobaoOrder = new TaobaoOrder();
                    }
                    taobaoOrder.setRelationId(orderDto.getRelationId());
                    taobaoOrder.setTradeId(orderDto.getTradeId());
                    taobaoOrder.setTitle(orderDto.getItemTitle());
                    taobaoOrder.setPictUrl(orderDto.getItemImg());
                    taobaoOrder.setShopTitle(getShopTitle(orderDto.getSellerShopTitle(),orderDto.getSellerNick()));
                    taobaoOrder.setOrderType(orderDto.getOrderType());
                    taobaoOrder.setItemNum(orderDto.getItemNum().intValue());
                    taobaoOrder.setItemPrice(new BigDecimal(orderDto.getItemPrice()));
                    taobaoOrder.setAlipayTotalPrice(new BigDecimal(orderDto.getAlipayTotalPrice()));
                    taobaoOrder.setSnapshot(JsonUtils.writeValue(orderDto));
                    taobaoOrder.setTkStatus(orderDto.getTkStatus().intValue());
                    taobaoOrder.setPrimaryReward(BigDecimal.valueOf(taobaoOrder.getItemNum()).multiply(taobaoOrder.getItemPrice()).multiply((new BigDecimal(orderDto.getTotalCommissionRate()).multiply(BigDecimal.valueOf(0.01)))).multiply(BigDecimal.valueOf(0.55)).multiply(rebateRatios.getPrimaryRatio()));
                    taobaoOrder.setSecondaryReward(BigDecimal.valueOf(taobaoOrder.getItemNum()).multiply(taobaoOrder.getItemPrice()).multiply((new BigDecimal(orderDto.getTotalCommissionRate()).multiply(BigDecimal.valueOf(0.01)))).multiply(BigDecimal.valueOf(0.55)).multiply(rebateRatios.getSecondaryRatio()));
                    try{
                        taobaoOrder.setTkCreateTime(DateUtils.parse(orderDto.getTkCreateTime(),DateUtils.DATETIME_FORMAT));
                    }catch (Throwable t){
                        throw new ServiceException(1001,"DateUtils时间解析错误");
                    }
                    taobaoOrders.add(taobaoOrder);
                }
            }
        }
        return taobaoOrders;
    }

    private TbkDgMaterialOptionalResponse.MapData matchMaterialOptional(TbkDgMaterialOptionalRequest req,Long itemId){
        try{
            TbkDgMaterialOptionalResponse response = getMaterialOptional(req);
            if(response.isSuccess()){
                TbkDgMaterialOptionalResponse.MapData result = null;
                for (TbkDgMaterialOptionalResponse.MapData mapData : response.getResultList()) {
                    if(mapData.getItemId().equals(itemId)){
                        result = mapData;
                        break;
                    }
                }
                return result;
            }else{
                return null;
            }
        }catch (Exception e){
            return null;
        }
    }

    private BigDecimal getMaterialRatioReward(BigDecimal price,BigDecimal commissionRate,BigDecimal ratio){
        BigDecimal reward = commissionRate.multiply(BigDecimal.valueOf(0.0001)).multiply(price).multiply(BigDecimal.valueOf(0.55)).multiply(ratio);
        return reward;
    }

    private BigDecimal getOptimusRatioReward(BigDecimal price,BigDecimal commissionRate,BigDecimal ratio){
        BigDecimal reward = commissionRate.multiply(BigDecimal.valueOf(0.01)).multiply(price).multiply(BigDecimal.valueOf(0.55)).multiply(ratio);
        return reward;
    }

    private BigDecimal getUatmTbkRatioReward(BigDecimal price,BigDecimal tkRate,BigDecimal ratio){
        BigDecimal reward = tkRate.multiply(BigDecimal.valueOf(0.01)).multiply(price).multiply(BigDecimal.valueOf(0.55)).multiply(ratio);
        return reward;
    }

    private String getShopTitle(String shopTitle,String nick){
        if(!StringUtils.isEmpty(shopTitle)){
            return shopTitle;
        }else{
            return nick;
        }
    }
}
