package com.juggle.juggle.primary.app.service;

import com.juggle.juggle.framework.common.errors.ServiceException;
import com.juggle.juggle.framework.common.utils.JsonUtils;
import com.juggle.juggle.framework.common.utils.PropertyCopyUtil;
import com.juggle.juggle.framework.data.filter.PageResult;
import com.juggle.juggle.framework.data.filter.PageSearch;
import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.framework.data.service.BaseCRUDService;
import com.juggle.juggle.primary.Constants;
import com.juggle.juggle.primary.account.dao.MemberAccountDao;
import com.juggle.juggle.primary.account.model.MemberAccount;
import com.juggle.juggle.primary.app.dao.InviteRecordDao;
import com.juggle.juggle.primary.app.dao.MemberDao;
import com.juggle.juggle.primary.app.dao.RewardDao;
import com.juggle.juggle.primary.app.dto.*;
import com.juggle.juggle.primary.app.model.InviteRecord;
import com.juggle.juggle.primary.app.model.Member;
import com.juggle.juggle.primary.app.model.Reward;
import com.juggle.juggle.primary.setting.dao.DictionaryDao;
import com.juggle.juggle.primary.setting.model.Dictionary;
import com.juggle.juggle.primary.shop.dao.ShopOrderDao;
import com.juggle.juggle.primary.shop.dto.ShopOrderSnapshot;
import com.juggle.juggle.primary.shop.model.ShopOrder;
import com.juggle.juggle.primary.shop.service.ShopOrderService;
import com.juggle.juggle.primary.taobao.dao.TaobaoOrderDao;
import com.juggle.juggle.primary.taobao.model.TaobaoOrder;
import com.juggle.juggle.primary.taobao.service.TaobaoOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class RewardService extends BaseCRUDService<Reward> {
    @Autowired
    private RewardDao dao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private MemberAccountDao memberAccountDao;

    @Autowired
    private InviteRecordDao inviteRecordDao;

    @Autowired
    private TaobaoOrderDao taobaoOrderDao;

    @Autowired
    private DictionaryDao dictionaryDao;

    @Autowired
    private TaobaoOrderService taobaoOrderService;

    @Autowired
    private ShopOrderDao shopOrderDao;

    @Autowired
    private ShopOrderService shopOrderService;

    @Override
    protected IRepo<Reward> getRepo() {
        return dao;
    }

    @Override
    protected void onCreate(Reward entity) {
        entity.setStatus(Constants.REWARD_STATUS.PEND);
    }

    public RewardCensus getCensus(){
        RewardCensus census = new RewardCensus();
        return census;
    }

    public LastRewardCensus getLastCensus(){
        LastRewardCensus census = new LastRewardCensus();
        return census;
    }

    public PresentRewardCensus getPresentCensus(Date startTime,Date endTime){
        PresentRewardCensus census = new PresentRewardCensus();
        return census;
    }

    public PageResult searchSimplify(PageSearch pageSearch){
        PageResult pageResult = search(pageSearch);
        List<Reward> rewards = pageResult.getRows();
        List<RewardDto> rewardDtos = new ArrayList<>();
        for (Reward reward : rewards) {
            RewardDto rewardDto = new RewardDto();
            try {
                PropertyCopyUtil.getInstance().copyProperties(rewardDto,reward);
                RewardSnapshot snapshot = new RewardSnapshot();
                if(reward.getOrderType().equals(Constants.REWARD_ORDER_TYPE.TAOBAO)){
                       TaobaoOrder order = taobaoOrderService.find(reward.getOrderId());
                       snapshot.setId(order.getId());
                       snapshot.setThumbnail(order.getPictUrl());
                       snapshot.setTitle(order.getTitle());
                       snapshot.setPrice(order.getItemPrice());
                       snapshot.setCount(order.getItemNum());
                 }else{
                       ShopOrder order = shopOrderService.find(reward.getOrderId());
                       ShopOrderSnapshot shopOrderSnapshot = JsonUtils.readValue(order.getSnapshot(),ShopOrderSnapshot.class);
                       snapshot.setId(order.getId());
                       snapshot.setThumbnail(shopOrderSnapshot.getProduct().getThumbnail());
                       snapshot.setTitle(shopOrderSnapshot.getProduct().getName());
                       snapshot.setPrice(order.getPrice());
                       snapshot.setCount(order.getCount());
                   }
                   rewardDto.setSnapshot(snapshot);

                rewardDtos.add(rewardDto);
            }catch (Exception e){
                throw new ServiceException(1001,"PropertyCopyUtil解析失败");
            }
        }
        pageResult.setRows(rewardDtos);
        return pageResult;
    }

    //生成推广奖励记录
    public void generateSpread(){
        Dictionary spreadPrimary = dictionaryDao.findFirstByTypeAndDictKey(Constants.DICTIONARY_TYPE.SYSTEM,"SPREAD_PRIMARY_REWARD");
        Dictionary spreadSecondary = dictionaryDao.findFirstByTypeAndDictKey(Constants.DICTIONARY_TYPE.SYSTEM,"SPREAD_SECONDARY_REWARD");
        List<InviteRecord> inviteRecords = inviteRecordDao.findAllByHandle(false);
        for (InviteRecord inviteRecord : inviteRecords) {
            Member invitee = memberDao.findOne(inviteRecord.getBeInvitedId());
            if(invitee.getGrade().equals(Constants.MEMBER_GRADE.GRADE2)&&taobaoOrderDao.existsByRelationIdAndTkStatus(invitee.getRelationId(),14)){
                TaobaoOrder taobaoOrder = taobaoOrderDao.findFirstByRelationIdAndTkStatus(invitee.getRelationId(),14);
                Member member = memberDao.findOne(inviteRecord.getMemberId());
                if(member.getGrade().equals(Constants.MEMBER_GRADE.GRADE2)){
                    Reward reward = new Reward();
                    reward.setMemberId(inviteRecord.getMemberId());
                    reward.setType(Constants.REWARD_TYPE.SPREAD);
                    reward.setOrderId(taobaoOrder.getId());
                    reward.setOrderType(Constants.REWARD_ORDER_TYPE.TAOBAO);
                    reward.setTeamerLevel(Constants.REWARD_TEAMER_LEVEL.PRIMARY);
                    reward.setTeamerId(invitee.getId());
                    reward.setAmount(new BigDecimal(spreadPrimary.getDictValue()));
                    create(reward);
                }
                if(null!=member.getParentId()){
                    Member parent = memberDao.findOne(member.getParentId());
                    if(parent.getGrade().equals(Constants.MEMBER_GRADE.GRADE2)){
                        Reward reward = new Reward();
                        reward.setMemberId(parent.getId());
                        reward.setType(Constants.REWARD_TYPE.SPREAD);
                        reward.setOrderId(taobaoOrder.getId());
                        reward.setOrderType(Constants.REWARD_ORDER_TYPE.TAOBAO);
                        reward.setTeamerId(invitee.getId());
                        reward.setTeamerLevel(Constants.REWARD_TEAMER_LEVEL.SECONDARY);
                        reward.setAmount(new BigDecimal(spreadSecondary.getDictValue()));
                        create(reward);
                    }
                }
                inviteRecord.setHandle(true);
                inviteRecordDao.save(inviteRecord);
            }
        }
    }

    //生成导购奖励记录
    public void generateGuide(){
        Dictionary rebateGrade1 = dictionaryDao.findFirstByTypeAndDictKey(Constants.DICTIONARY_TYPE.SYSTEM,"REBATE_GRADE1_TAOBAO");
        Dictionary guidePrimary = dictionaryDao.findFirstByTypeAndDictKey(Constants.DICTIONARY_TYPE.SYSTEM,"REBATE_PRIMARY_GUIDE");
        Dictionary guideSecondary = dictionaryDao.findFirstByTypeAndDictKey(Constants.DICTIONARY_TYPE.SYSTEM,"REBATE_SECONDARY_GUIDE");
        List<TaobaoOrder> taobaoOrders = taobaoOrderDao.findAllByHandle(false);
        for (TaobaoOrder taobaoOrder : taobaoOrders) {
            Member member = memberDao.findFirstByRelationId(taobaoOrder.getRelationId());
            Reward reward = new Reward();
            reward.setMemberId(member.getId());
            reward.setType(Constants.REWARD_TYPE.GUIDE);
            reward.setOrderType(Constants.REWARD_ORDER_TYPE.TAOBAO);
            reward.setOrderId(taobaoOrder.getId());
            if(member.getGrade().equals(Constants.MEMBER_GRADE.GRADE1)){
                reward.setAmount(taobaoOrder.getPrimaryReward());
            }else{
                reward.setAmount(taobaoOrder.getSecondaryReward());
            }
            create(reward);
            if(null!=member.getParentId()){
                Member parent = memberDao.findOne(member.getParentId());
                if(parent.getGrade().equals(Constants.MEMBER_GRADE.GRADE2)){
                    reward = new Reward();
                    reward.setMemberId(parent.getId());
                    reward.setType(Constants.REWARD_TYPE.GUIDE);
                    reward.setOrderType(Constants.REWARD_ORDER_TYPE.TAOBAO);
                    reward.setOrderId(taobaoOrder.getId());
                    reward.setTeamerId(member.getId());
                    reward.setTeamerLevel(Constants.REWARD_TEAMER_LEVEL.PRIMARY);
                    reward.setAmount(taobaoOrder.getPrimaryReward().divide(new BigDecimal(rebateGrade1.getDictValue())).multiply(new BigDecimal(guidePrimary.getDictValue())));
                    create(reward);
                }
                if(null!=parent.getParentId()){
                    Member grandpa = memberDao.findOne(parent.getId());
                    if(grandpa.getParentId().equals(Constants.MEMBER_GRADE.GRADE2)){
                        reward = new Reward();
                        reward.setMemberId(grandpa.getId());
                        reward.setType(Constants.REWARD_TYPE.GUIDE);
                        reward.setOrderId(taobaoOrder.getId());
                        reward.setOrderType(Constants.REWARD_ORDER_TYPE.TAOBAO);
                        reward.setTeamerId(member.getId());
                        reward.setTeamerLevel(Constants.REWARD_TEAMER_LEVEL.SECONDARY);
                        reward.setAmount(taobaoOrder.getPrimaryReward().divide(new BigDecimal(rebateGrade1.getDictValue())).multiply(new BigDecimal(guideSecondary.getDictValue())));
                        create(reward);
                    }
                }
            }
            taobaoOrder.setHandle(true);
            taobaoOrderDao.save(taobaoOrder);
        }
    }

    //生成自营奖励记录
    public void generateSale(){
        Dictionary ratio = dictionaryDao.findFirstByTypeAndDictKey(Constants.DICTIONARY_TYPE.SYSTEM,"RATIO_REWARD_SHOP");
        Dictionary rebate = dictionaryDao.findFirstByTypeAndDictKey(Constants.DICTIONARY_TYPE.SYSTEM,"REBATE_REWARD_SHOP");
        Dictionary salePrimary = dictionaryDao.findFirstByTypeAndDictKey(Constants.DICTIONARY_TYPE.SYSTEM,"RATIO_PRIMARY_SALE");
        Dictionary saleSecondary = dictionaryDao.findFirstByTypeAndDictKey(Constants.DICTIONARY_TYPE.SYSTEM,"RATIO_SECONDARY_SALE");
        List<ShopOrder> shopOrders = shopOrderDao.findAllByHandleAndStatus(false);
        for (ShopOrder shopOrder : shopOrders) {
            Member member = memberDao.findOne(shopOrder.getMemberId());
            Reward reward = new Reward();
            reward.setMemberId(member.getId());
            reward.setType(Constants.REWARD_TYPE.SALE);
            reward.setOrderId(shopOrder.getId());
            reward.setOrderType(Constants.REWARD_ORDER_TYPE.SHOP);
            reward.setAmount(shopOrder.getAmount().multiply(new BigDecimal(ratio.getDictValue())).multiply(new BigDecimal(rebate.getDictValue())));
            create(reward);
            if(null!=member.getParentId()){
                Member parent = memberDao.findOne(member.getParentId());
                if(parent.getGrade().equals(Constants.MEMBER_GRADE.GRADE2)){
                    reward = new Reward();
                    reward.setMemberId(parent.getId());
                    reward.setTeamerId(member.getId());
                    reward.setTeamerLevel(Constants.REWARD_TEAMER_LEVEL.PRIMARY);
                    reward.setType(Constants.REWARD_TYPE.SALE);
                    reward.setOrderType(Constants.REWARD_ORDER_TYPE.SHOP);
                    reward.setOrderId(shopOrder.getId());
                    reward.setAmount(shopOrder.getAmount().multiply(new BigDecimal(salePrimary.getDictValue())));
                    create(reward);
                }
                if(null!=parent.getParentId()){
                    Member grandpa = memberDao.findOne(parent.getId());
                    if(parent.getGrade().equals(Constants.MEMBER_GRADE.GRADE2)){
                        reward = new Reward();
                        reward.setMemberId(grandpa.getId());
                        reward.setTeamerId(member.getId());
                        reward.setTeamerLevel(Constants.REWARD_TEAMER_LEVEL.SECONDARY);
                        reward.setOrderType(Constants.REWARD_ORDER_TYPE.SHOP);
                        reward.setType(Constants.REWARD_TYPE.SALE);
                        reward.setOrderId(shopOrder.getId());
                        reward.setAmount(shopOrder.getAmount().multiply(new BigDecimal(saleSecondary.getDictValue())));
                        create(reward);
                    }
                }
            }
            shopOrder.setHandle(true);
            shopOrderDao.save(shopOrder);
        }
    }

    //计算上个月的奖励
    public void paidLastMonth(){
        Date timeMonthStart = getTimeMonthStart();
        List<Reward> rewards = dao.findAllByCreatedTimeLessThanAndStatus(timeMonthStart,Constants.REWARD_STATUS.PEND);
        for (Reward reward : rewards) {
            MemberAccount memberAccount = memberAccountDao.findFirstByMemberId(reward.getMemberId());
            memberAccount.setAmount(memberAccount.getAmount().add(reward.getAmount()));
            reward.setStatus(Constants.REWARD_STATUS.PAID);
            memberAccountDao.save(memberAccount);
            dao.save(reward);

        }
    }

    // 获得本月第一天0点时间
    private Date getTimeMonthStart() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return  cal.getTime();
    }
}
