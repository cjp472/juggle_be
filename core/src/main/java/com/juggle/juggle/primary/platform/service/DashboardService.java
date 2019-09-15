package com.juggle.juggle.primary.platform.service;

import com.juggle.juggle.primary.Constants;
import com.juggle.juggle.primary.app.dao.MemberDao;
import com.juggle.juggle.primary.cms.dao.ArticleDao;
import com.juggle.juggle.primary.platform.data.DashboardCensus;
import com.juggle.juggle.primary.platform.data.GlobalSearch;
import com.juggle.juggle.primary.shop.dao.ShopOrderDao;
import com.juggle.juggle.primary.shop.dao.ShopProductDao;
import com.juggle.juggle.primary.taobao.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {
    @Autowired
    private MemberDao memberDao;

    @Autowired
    private ShopOrderDao shopOrderDao;

    @Autowired
    private ShopProductDao shopProductDao;

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private TaobaoProductDao taobaoProductDao;

    @Autowired
    private TaobaoFavourableDao taobaoFavourableDao;

    @Autowired
    private TaobaoFlashDao taobaoFlashDao;

    @Autowired
    private TaobaoGoodsDao taobaoGoodsDao;

    @Autowired
    private TaobaoPopularDao taobaoPopularDao;

    @Autowired
    private TaobaoSelectionDao taobaoSelectionDao;

    public GlobalSearch globalSearch(String keyword){
        GlobalSearch globalSearch = new GlobalSearch();
        if(memberDao.existsByNickNameLike("%"+keyword+"%")){
            globalSearch.setType(Constants.GLOBAL_SEARCH.MEMBER);
            globalSearch.setField("nickName");
        }else if(memberDao.existsByMobileLike("%"+keyword+"%")){
            globalSearch.setType(Constants.GLOBAL_SEARCH.MEMBER);
            globalSearch.setField("mobile");
        }else if(taobaoGoodsDao.existsByTitleLike("%"+keyword+"%")){
            globalSearch.setType(Constants.GLOBAL_SEARCH.TAOBAO_GOODS);
            globalSearch.setField("title");
        }else if(taobaoProductDao.existsByTitleLike("%"+keyword+"%")){
            globalSearch.setType(Constants.GLOBAL_SEARCH.TAOBAO_PRODUCT);
            globalSearch.setField("title");
        }else if(taobaoSelectionDao.existsByTitleLike("%"+keyword+"%")){
            globalSearch.setType(Constants.GLOBAL_SEARCH.TAOBAO_SELECTION);
            globalSearch.setField("title");
        }else if(taobaoFavourableDao.existsByTitleLike("%"+keyword+"%")){
            globalSearch.setType(Constants.GLOBAL_SEARCH.TAOBAO_FAVOURABLE);
            globalSearch.setField("title");
        }else if(taobaoFlashDao.existsByTitleLike("%"+keyword+"%")){
            globalSearch.setType(Constants.GLOBAL_SEARCH.TAOBAO_FLASH);
            globalSearch.setField("title");
        }else if(taobaoPopularDao.existsByTitleLike("%"+keyword+"%")){
            globalSearch.setType(Constants.GLOBAL_SEARCH.TAOBAO_POPULAR);
            globalSearch.setField("title");
        }else if(shopProductDao.existsByNameLike("%"+keyword+"%")){
            globalSearch.setType(Constants.GLOBAL_SEARCH.SHOP_PRODUCT);
            globalSearch.setField("name");
        }else if(articleDao.existsByTitleLike("%"+keyword+"%")){
            globalSearch.setType(Constants.GLOBAL_SEARCH.ARTICLE);
            globalSearch.setField("title");
        }else if(articleDao.existsByBriefLike("%"+keyword+"%")){
            globalSearch.setType(Constants.GLOBAL_SEARCH.ARTICLE);
            globalSearch.setField("brief");
        }
        return globalSearch;
    }

    public DashboardCensus getCensus(){
        DashboardCensus dashboardCensus = new DashboardCensus();
        dashboardCensus.setMemberNum(memberDao.count());
        dashboardCensus.setShopOrderAmount(shopOrderDao.sumAllOrdersAmount());
        dashboardCensus.setShopOrderNum(shopOrderDao.countAllOrders());
        dashboardCensus.setTaobaoProductNum(taobaoProductDao.count()+taobaoFavourableDao.count()+taobaoFlashDao.count()+taobaoGoodsDao.count()+taobaoPopularDao.count()+taobaoSelectionDao.count());
        return dashboardCensus;
    }
}
