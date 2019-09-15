package com.juggle.juggle.schedule;

import com.juggle.juggle.common.util.TaobaoClientUtil;
import com.juggle.juggle.framework.common.errors.ServiceException;
import com.juggle.juggle.framework.common.utils.DateUtils;
import com.juggle.juggle.primary.Constants;
import com.juggle.juggle.primary.app.dao.MemberDao;
import com.juggle.juggle.primary.app.dao.WithdrawDao;
import com.juggle.juggle.primary.app.model.Member;
import com.juggle.juggle.primary.app.model.Withdraw;
import com.juggle.juggle.primary.app.service.MemberService;
import com.juggle.juggle.primary.app.service.RewardService;
import com.juggle.juggle.primary.app.service.WithdrawService;
import com.juggle.juggle.primary.business.dao.GroupSmsDao;
import com.juggle.juggle.primary.business.dao.PushDao;
import com.juggle.juggle.primary.business.model.GroupSms;
import com.juggle.juggle.primary.business.model.Push;
import com.juggle.juggle.primary.business.service.GroupSmsService;
import com.juggle.juggle.primary.business.service.PushService;
import com.juggle.juggle.primary.shop.dao.ShopOrderDao;
import com.juggle.juggle.primary.shop.model.ShopOrder;
import com.juggle.juggle.primary.shop.service.ShopOrderService;
import com.juggle.juggle.primary.taobao.dao.*;
import com.juggle.juggle.primary.taobao.model.TaobaoBrand;
import com.juggle.juggle.primary.taobao.model.TaobaoType;
import com.juggle.juggle.schedule.annotation.SchLog;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class ScheduleExecute {
    @Autowired
    private TaobaoGoodsDao taobaoGoodsDao;

    @Autowired
    private TaobaoBrandDao taobaoBrandDao;

    @Autowired
    private TaobaoProductDao taobaoProductDao;

    @Autowired
    private TaobaoFavourableDao taobaoFavourableDao;

    @Autowired
    private TaobaoSelectionDao taobaoSelectionDao;

    @Autowired
    private TaobaoFlashDao taobaoFlashDao;

    @Autowired
    private TaobaoPopularDao taobaoPopularDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private MemberService memberService;

    @Autowired
    private PushDao pushDao;

    @Autowired
    private PushService pushService;

    @Autowired
    private GroupSmsDao groupSmsDao;

    @Autowired
    private GroupSmsService groupSmsService;

    @Autowired
    private WithdrawDao withdrawDao;

    @Autowired
    private WithdrawService withdrawService;

    @Autowired
    private TaobaoTypeDao taobaoTypeDao;

    @Autowired
    private ShopOrderDao shopOrderDao;

    @Autowired
    private ShopOrderService shopOrderService;

    @Autowired
    private RewardService rewardService;

    @SchLog(name = "TAOBAO_GOODS_SYNC")
    public void executeTaobaoGoodsSync(){
        TaobaoClientUtil taobaoClientUtil = new TaobaoClientUtil();
        taobaoGoodsDao.deleteAllInBatch();
        List<TaobaoType> taobaoTypes = taobaoTypeDao.findAllByParentIdIsNotNullAndEnabled(true);
        for (TaobaoType taobaoType : taobaoTypes) {
            if(!StringUtils.isEmpty(taobaoType.getCat())){
                taobaoClientUtil.grabTaobaoGoods(taobaoType.getCat(),taobaoType.getId());
            }
        }
    }

    @SchLog(name = "TAOBAO_PRODUCT_SYNC")
    public void executeTaobaoProductSync(){
        TaobaoClientUtil taobaoClientUtil = new TaobaoClientUtil();
        List<TaobaoBrand> taobaoBrands = taobaoBrandDao.findAllByEnabled(true);
        taobaoProductDao.deleteAllInBatch();
        for(TaobaoBrand taobaoBrand:taobaoBrands){
            taobaoClientUtil.grabTaobaoProduct(taobaoBrand);
        }
    }

    @SchLog(name = "TAOBAO_FLASH_SYNC")
    public void executeTaobaoFlashSync(){
        TaobaoClientUtil taobaoClientUtil = new TaobaoClientUtil();
        taobaoFlashDao.deleteAllByStartTimeLessThan(DateUtils.start(new Date()));
        Date today = new Date();
        String tomorrow = DateUtils.format(DateUtils.start(DateUtils.next(today)),DateUtils.DATE_FORMAT);
        try{
            Date rushStart = DateUtils.parse(tomorrow + " " + Constants.TAOBAO_FLASH_RUSH_TIME.ZERO,DateUtils.DATETIME_SIMPLY_FORMAT);
            Date rushEnd = DateUtils.parse(tomorrow + " " + Constants.TAOBAO_FLASH_RUSH_TIME.EIGHT,DateUtils.DATETIME_SIMPLY_FORMAT);
            taobaoClientUtil.grabJuTqgGet(rushStart,rushEnd);
            rushStart = DateUtils.parse(tomorrow + " " + Constants.TAOBAO_FLASH_RUSH_TIME.EIGHT,DateUtils.DATETIME_SIMPLY_FORMAT);
            rushEnd = DateUtils.parse(tomorrow + " " + Constants.TAOBAO_FLASH_RUSH_TIME.TWELVE,DateUtils.DATETIME_SIMPLY_FORMAT);
            taobaoClientUtil.grabJuTqgGet(rushStart,rushEnd);
            rushStart = DateUtils.parse(tomorrow + " " + Constants.TAOBAO_FLASH_RUSH_TIME.TWELVE,DateUtils.DATETIME_SIMPLY_FORMAT);
            rushEnd = DateUtils.parse(tomorrow + " " + Constants.TAOBAO_FLASH_RUSH_TIME.SIXTEEN,DateUtils.DATETIME_SIMPLY_FORMAT);
            taobaoClientUtil.grabJuTqgGet(rushStart,rushEnd);
            rushStart = DateUtils.parse(tomorrow + " " + Constants.TAOBAO_FLASH_RUSH_TIME.SIXTEEN,DateUtils.DATETIME_SIMPLY_FORMAT);
            rushEnd = DateUtils.parse(tomorrow + " " + Constants.TAOBAO_FLASH_RUSH_TIME.TWENTY_ONE,DateUtils.DATETIME_SIMPLY_FORMAT);
            taobaoClientUtil.grabJuTqgGet(rushStart,rushEnd);
            rushStart = DateUtils.parse(tomorrow + " "+ Constants.TAOBAO_FLASH_RUSH_TIME.TWELVE,DateUtils.DATETIME_SIMPLY_FORMAT);
            rushEnd = DateUtils.parse(tomorrow + " 23:59",DateUtils.DATETIME_SIMPLY_FORMAT);
            taobaoClientUtil.grabJuTqgGet(rushStart,rushEnd);
        }catch (Throwable t){
            throw new ServiceException(1001,t.getMessage());
        }
    }

    @SchLog(name = "TAOBAO_FLASH_SOLDNUM")
    public void executeTaobaoFlashSoldNum(){
        TaobaoClientUtil taobaoClientUtil = new TaobaoClientUtil();
        taobaoFlashDao.deleteAllByStartTimeLessThan(DateUtils.start(new Date()));
        Date today = new Date();
        String tomorrow = DateUtils.format(DateUtils.start(today),DateUtils.DATE_FORMAT);
        try{
            Date rushStart = DateUtils.parse(tomorrow + " " + Constants.TAOBAO_FLASH_RUSH_TIME.ZERO,DateUtils.DATETIME_SIMPLY_FORMAT);
            Date rushEnd = DateUtils.parse(tomorrow + " " + Constants.TAOBAO_FLASH_RUSH_TIME.EIGHT,DateUtils.DATETIME_SIMPLY_FORMAT);
            taobaoClientUtil.grabJuTqgGetToUpdateSoldNum(rushStart,rushEnd);
            rushEnd = DateUtils.parse(tomorrow + " " + Constants.TAOBAO_FLASH_RUSH_TIME.TWELVE,DateUtils.DATETIME_SIMPLY_FORMAT);
            rushStart = DateUtils.parse(tomorrow + " " + Constants.TAOBAO_FLASH_RUSH_TIME.EIGHT,DateUtils.DATETIME_SIMPLY_FORMAT);
            taobaoClientUtil.grabJuTqgGetToUpdateSoldNum(rushStart,rushEnd);
            rushStart = DateUtils.parse(tomorrow + " " + Constants.TAOBAO_FLASH_RUSH_TIME.TWELVE,DateUtils.DATETIME_SIMPLY_FORMAT);
            rushEnd = DateUtils.parse(tomorrow + " " + Constants.TAOBAO_FLASH_RUSH_TIME.SIXTEEN,DateUtils.DATETIME_SIMPLY_FORMAT);
            taobaoClientUtil.grabJuTqgGetToUpdateSoldNum(rushStart,rushEnd);
            rushStart = DateUtils.parse(tomorrow + " " + Constants.TAOBAO_FLASH_RUSH_TIME.SIXTEEN,DateUtils.DATETIME_SIMPLY_FORMAT);
            rushEnd = DateUtils.parse(tomorrow + " " + Constants.TAOBAO_FLASH_RUSH_TIME.TWENTY_ONE,DateUtils.DATETIME_SIMPLY_FORMAT);
            taobaoClientUtil.grabJuTqgGetToUpdateSoldNum(rushStart,rushEnd);
            rushStart = DateUtils.parse(tomorrow + " "+ Constants.TAOBAO_FLASH_RUSH_TIME.TWELVE,DateUtils.DATETIME_SIMPLY_FORMAT);
            rushEnd = DateUtils.parse(tomorrow + " 23:59",DateUtils.DATETIME_SIMPLY_FORMAT);
            taobaoClientUtil.grabJuTqgGetToUpdateSoldNum(rushStart,rushEnd);
        }catch (Throwable t){
            throw new ServiceException(1001,t.getMessage());
        }
    }

    @SchLog(name = "TAOBAO_SELECTION_SYNC")
    public void executeTaobaoSelectionSync(){
        TaobaoClientUtil taobaoClientUtil = new TaobaoClientUtil();
        taobaoSelectionDao.deleteAllInBatch();
        HashMap<String,Long> hashMap = taobaoClientUtil.grabUatmFavoritesGet();
        taobaoClientUtil.grabUatmFavoritesItemGet(hashMap);
    }

    @SchLog(name = "TAOBAO_FAVOURABLE_SYNC")
    public void executeTaobaoFavourableSync(){
        TaobaoClientUtil taobaoClientUtil = new TaobaoClientUtil();
        taobaoFavourableDao.deleteAllInBatch();
        taobaoClientUtil.grabTaobaoFavourable(Constants.TAOBAO_FAVOURABLE_TYPE.TYPE1);
        taobaoClientUtil.grabTaobaoFavourable(Constants.TAOBAO_FAVOURABLE_TYPE.TYPE2);
        taobaoClientUtil.grabTaobaoFavourable(Constants.TAOBAO_FAVOURABLE_TYPE.TYPE3);
        taobaoClientUtil.grabTaobaoFavourable(Constants.TAOBAO_FAVOURABLE_TYPE.TYPE4);
        taobaoClientUtil.grabTaobaoFavourable(Constants.TAOBAO_FAVOURABLE_TYPE.TYPE5);
        taobaoClientUtil.grabTaobaoFavourable(Constants.TAOBAO_FAVOURABLE_TYPE.TYPE6);
        taobaoClientUtil.grabTaobaoFavourable(Constants.TAOBAO_FAVOURABLE_TYPE.TYPE7);
    }

    @SchLog(name = "TAOBAO_POPULAR_SYNC")
    public void executeTaobaoPopularSync(){
        TaobaoClientUtil taobaoClientUtil = new TaobaoClientUtil();
        taobaoPopularDao.deleteAllInBatch();
        taobaoClientUtil.grabTaobaoPopular(Constants.TAOBAO_POPULAR_TYPE.TYPE1);
        taobaoClientUtil.grabTaobaoPopular(Constants.TAOBAO_POPULAR_TYPE.TYPE2);
    }

    @SchLog(name = "TAOBAO_ORDER_SYNC")
    public void executeTaobaoOrderSync(){
        Date curr = new Date();
        String startTime = DateUtils.format(DateUtils.goPast(curr,10800),DateUtils.DATETIME_FORMAT);
        String endTime =  DateUtils.format(curr,DateUtils.DATETIME_FORMAT);
        TaobaoClientUtil taobaoClientUtil = new TaobaoClientUtil();
        taobaoClientUtil.grabOrderDetailsGet(startTime,endTime);
    }

    @SchLog(name = "TAOBAO_ORDER_SYNC_EX")
    public void executeTaobaoOrderSyncEx(){
        Date curr = new Date();
        Date start = DateUtils.goPast(curr,1296000);
        TaobaoClientUtil taobaoClientUtil = new TaobaoClientUtil();
        for(int i=0;i<1080;i++){
            String startTime =  DateUtils.format(start,DateUtils.DATETIME_FORMAT);
            String endTime =  DateUtils.format(DateUtils.goFuture(curr,10800),DateUtils.DATETIME_FORMAT);
            taobaoClientUtil.grabOrderDetailsGet(startTime,endTime);
            start = DateUtils.goFuture(start,10800);
            i++;
        }
    }

    @SchLog(name = "APP_GROUP_SMS")
    public void executeAppGroupSms(){
        List<GroupSms> groupSmsList = groupSmsDao.findAllByStatus(Constants.GROUP_SMS_STATUS.WAIT);
        for(GroupSms groupSms:groupSmsList){
            groupSmsService.send(groupSms.getId());
        }
    }

    @SchLog(name = "APP_NEWS_PUSH")
    public void executeAppNewsPush(){
        List<Push> pushes = pushDao.findAllByStatus(Constants.PUSH_STATUS.WAIT);
        for(Push push:pushes){
            pushService.push(push.getId());
        }
    }

    @SchLog(name = "SHOP_ORDER_CLOSED")
    public void executeShopOrderClosed(){
        List<ShopOrder> orders = shopOrderDao.findAllByStatusAndCreatedTimeLessThan(Constants.SHOP_ORDER_STATUS.OPEN,DateUtils.goPast(new Date(),43200));
        for (ShopOrder order : orders) {
            order.setStatus(Constants.SHOP_ORDER_STATUS.CLOSED);
            shopOrderService.save(order);
        }
    }

    @SchLog(name = "APP_MEMBER_TERM")
    public void executeAppMemberTerm(){
        List<Member> members = memberDao.findAllByGradeAndTermTimeLessThan(Constants.MEMBER_GRADE.GRADE2,new Date());
        for (Member member : members) {
            member.setGrade(Constants.MEMBER_GRADE.GRADE1);
            member.setTermTime(null);
            memberService.save(member);
        }
    }

    @SchLog(name = "APP_PEND_WITHDRAW")
    public void executeAppPendWithdraw(){
        List<Withdraw> withdraws = withdrawDao.findAllByAccountTypeAndStatusOrderByCreatedTime(Constants.WITHDRAW_ACCOUNT_TYPE.ALIPAY,Constants.WITHDRAW_STATUS.PEND);
        withdrawService.withdrawToAlipay(withdraws);
    }

    @SchLog(name = "APP_REWARD_PAID")
    public void executeAppRewardPaid(){
        Calendar calendar = Calendar.getInstance();
        Date today = new Date();
        calendar.setTime(today);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        //每月25后结算上月奖励
        if(day>25){
            rewardService.paidLastMonth();
        }
    }

    @SchLog(name = "TAOBAO_ORDER_REFUND")
    public void executeTaobaoOrderRefund(){
        TaobaoClientUtil taobaoClientUtil = new TaobaoClientUtil();
        taobaoClientUtil.grabRelationRefund();
    }

    @SchLog(name = "APP_SPREAD_REWARD")
    public void executeAppSpreadReward(){
        rewardService.generateSpread();
    }

    @SchLog(name = "APP_GUIDE_REWARD")
    public void executeAppGuideReward(){
        rewardService.generateGuide();
    }

    @SchLog(name = "APP_SALE_REWARD")
    public void executeAppSaleReward(){
        rewardService.generateSale();
    }
}
