package com.juggle.juggle.schedule;

import com.juggle.juggle.framework.common.utils.DateUtils;
import com.juggle.juggle.primary.Constants;
import com.juggle.juggle.primary.schedule.model.ScheduleTask;
import com.juggle.juggle.primary.schedule.service.ScheduleTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScheduleConfig {
    @Autowired
    private ScheduleExecute scheduleExecute;

    @Autowired
    private ScheduleTaskService scheduleTaskService;

    @Scheduled(cron = "0 */10 * * * ?")
    public void taobaoGoodsSyncJob(){
        ScheduleTask scheduleTask = scheduleTaskService.findByCode("TAOBAO_GOODS_SYNC");
        boolean allowExecute = checkAllowExecute(scheduleTask);
        if(allowExecute){
            scheduleExecute.executeTaobaoGoodsSync();
        }
    }

    @Scheduled(cron = "0 */10 * * * ?")
    public void taobaoProductSyncJob(){
        ScheduleTask scheduleTask = scheduleTaskService.findByCode("TAOBAO_PRODUCT_SYNC");
        boolean allowExecute = checkAllowExecute(scheduleTask);
        if(allowExecute){
            scheduleExecute.executeTaobaoProductSync();
        }
    }

    @Scheduled(cron = "0 */10 * * * ?")
    public void taobaoFlashSyncJob(){
        ScheduleTask scheduleTask = scheduleTaskService.findByCode("TAOBAO_FLASH_SYNC");
        boolean allowExecute = checkAllowExecute(scheduleTask);
        if(allowExecute){
            scheduleExecute.executeTaobaoFlashSync();
        }
    }

    @Scheduled(cron = "0 */10 * * * ?")
    public void taobaoFlashSoldNumJob(){
        ScheduleTask scheduleTask = scheduleTaskService.findByCode("TAOBAO_FLASH_SOLDNUM");
        boolean allowExecute = checkAllowExecute(scheduleTask);
        if(allowExecute){
            scheduleExecute.executeTaobaoFlashSoldNum();
        }
    }

    @Scheduled(cron = "0 */10 * * * ?")
    public void taobaoSelectionSyncJob(){
        ScheduleTask scheduleTask = scheduleTaskService.findByCode("TAOBAO_SELECTION_SYNC");
        boolean allowExecute = checkAllowExecute(scheduleTask);
        if(allowExecute){
            scheduleExecute.executeTaobaoSelectionSync();
        }
    }

    @Scheduled(cron = "0 */10 * * * ?")
    public void taobaoFavourableSyncJob(){
        ScheduleTask scheduleTask = scheduleTaskService.findByCode("TAOBAO_FAVOURABLE_SYNC");
        boolean allowExecute = checkAllowExecute(scheduleTask);
        if(allowExecute){
            scheduleExecute.executeTaobaoFavourableSync();
        }
    }

    @Scheduled(cron = "0 */10 * * * ?")
    public void taobaoPopularSyncJob(){
        ScheduleTask scheduleTask = scheduleTaskService.findByCode("TAOBAO_POPULAR_SYNC");
        boolean allowExecute = checkAllowExecute(scheduleTask);
        if(allowExecute){
            scheduleExecute.executeTaobaoPopularSync();
        }
    }

    @Scheduled(cron = "0 */10 * * * ?")
    public void taobaoOrderSyncJob(){
        ScheduleTask scheduleTask = scheduleTaskService.findByCode("TAOBAO_ORDER_SYNC");
        boolean allowExecute = checkAllowExecute(scheduleTask);
        if(allowExecute){
            scheduleExecute.executeTaobaoOrderSync();
        }
    }

    @Scheduled(cron = "0 */10 * * * ?")
    public void taobaoOrderSyncEx(){
        ScheduleTask scheduleTask = scheduleTaskService.findByCode("TAOBAO_ORDER_SYNC_EX");
        boolean allowExecute = checkAllowExecute(scheduleTask);
        if(allowExecute){
            scheduleExecute.executeTaobaoOrderSyncEx();
        }
    }

    @Scheduled(cron = "0 */10 * * * ?")
    public void appGroupSmsJob(){
        ScheduleTask scheduleTask = scheduleTaskService.findByCode("APP_GROUP_SMS");
        boolean allowExecute = checkAllowExecute(scheduleTask);
        if(allowExecute){
            scheduleExecute.executeAppGroupSms();
        }
    }

    @Scheduled(cron = "0 */10 * * * ?")
    public void appNewsPushJob(){
        ScheduleTask scheduleTask = scheduleTaskService.findByCode("APP_NEWS_PUSH");
        boolean allowExecute = checkAllowExecute(scheduleTask);
        if(allowExecute){
            scheduleExecute.executeAppNewsPush();
        }
    }

    @Scheduled(cron = "0 */10 * * * ?")
    public void shopOrderClosedJob(){
        ScheduleTask scheduleTask = scheduleTaskService.findByCode("SHOP_ORDER_CLOSED");
        boolean allowExecute = checkAllowExecute(scheduleTask);
        if(allowExecute){
            scheduleExecute.executeShopOrderClosed();
        }
    }

    @Scheduled(cron = "0 */10 * * * ?")
    public void appMemberTermJob(){
        ScheduleTask scheduleTask = scheduleTaskService.findByCode("APP_MEMBER_TERM");
        boolean allowExecute = checkAllowExecute(scheduleTask);
        if(allowExecute){
            scheduleExecute.executeAppMemberTerm();
        }
    }

    @Scheduled(cron = "0 */10 * * * ?")
    public void appPendWithdrawJob(){
        ScheduleTask scheduleTask = scheduleTaskService.findByCode("APP_PEND_WITHDRAW");
        boolean allowExecute = checkAllowExecute(scheduleTask);
        if(allowExecute){
            scheduleExecute.executeAppPendWithdraw();
        }
    }

    @Scheduled(cron = "0 */10 * * * ?")
    public void appRewardPaidJob(){
        ScheduleTask scheduleTask = scheduleTaskService.findByCode("APP_REWARD_PAID");
        boolean allowExecute = checkAllowExecute(scheduleTask);
        if(allowExecute){
            scheduleExecute.executeAppRewardPaid();
        }
    }

    @Scheduled(cron = "0 */10 * * * ?")
    public void taobaoOrderRefund(){
        ScheduleTask scheduleTask = scheduleTaskService.findByCode("TAOBAO_ORDER_REFUND");
        boolean allowExecute = checkAllowExecute(scheduleTask);
        if(allowExecute){
            scheduleExecute.executeTaobaoOrderRefund();
        }
    }

    @Scheduled(cron = "0 */10 * * * ?")
    public void appSpreadReward(){
        ScheduleTask scheduleTask = scheduleTaskService.findByCode("APP_SPREAD_REWARD");
        boolean allowExecute = checkAllowExecute(scheduleTask);
        if(allowExecute){
            scheduleExecute.executeAppSpreadReward();
        }
    }

    @Scheduled(cron = "0 */10 * * * ?")
    public void appGuideReward(){
        ScheduleTask scheduleTask = scheduleTaskService.findByCode("APP_GUIDE_REWARD");
        boolean allowExecute = checkAllowExecute(scheduleTask);
        if(allowExecute){
            scheduleExecute.executeAppGuideReward();
        }
    }

    @Scheduled(cron = "0 */10 * * * ?")
    public void appSaleReward(){
        ScheduleTask scheduleTask = scheduleTaskService.findByCode("APP_SALE_REWARD");
        boolean allowExecute = checkAllowExecute(scheduleTask);
        if(allowExecute){
            scheduleExecute.executeAppSaleReward();
        }
    }

    private boolean checkAllowExecute(ScheduleTask scheduleTask){
        if(scheduleTask.getType().equals(Constants.SCHEDULE_TASK_TYPE.INTERVAL)){
            Date currTime = new Date();
            Date resumeTime = null;
            if(null!=scheduleTask){
                if(scheduleTask.getTaskInterval()>0){
                    resumeTime = DateUtils.goFuture(scheduleTask.getLastSyncTime(),scheduleTask.getTaskInterval()*60);
                }
                if(scheduleTask.isEnabled() && null != resumeTime && currTime.compareTo(resumeTime)>0){
                    return true;
                }
            }
        }else{
            Date currTime = new Date();
            String datetime = DateUtils.format(currTime,DateUtils.DATE_FORMAT) +" "+scheduleTask.getTaskTime();
            try{
                Date taskTime = DateUtils.parse(datetime,DateUtils.DATETIME_FORMAT);
                Date lastTime = scheduleTask.getLastSyncTime();
                int diff = DateUtils.dayDiff(currTime,lastTime);
                if(scheduleTask.isEnabled() && currTime.compareTo(taskTime)>=0 && diff>=1){
                    return true;
                }
            }catch (Throwable e){
                return false;
            }
        }
        return false;
    }
}
