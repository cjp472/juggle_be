package com.juggle.juggle.api.v1.schedule;

import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.api.utils.ApiResponse;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.schedule.model.ScheduleTask;
import com.juggle.juggle.primary.schedule.service.ScheduleTaskService;
import com.juggle.juggle.schedule.ScheduleExecute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/schedule/scheduleTask")
public class ScheduleTaskApi extends BaseApi<ScheduleTask,ScheduleTask,ScheduleTask> {
    @Autowired
    private ScheduleTaskService service;

    @Autowired
    private ScheduleExecute scheduleExecute;

    @Override
    protected ICRUDService<ScheduleTask> getService() {
        return service;
    }

    @RequestMapping(value = "/{id}/execute")
    public @ResponseBody Object execute(@PathVariable Long id){
        ScheduleTask scheduleTask = service.find(id);
        if(scheduleTask.getCode().equals("TAOBAO_GOODS_SYNC")){
            scheduleExecute.executeTaobaoGoodsSync();
        }else if(scheduleTask.getCode().equals("TAOBAO_PRODUCT_SYNC")){
            scheduleExecute.executeTaobaoProductSync();
        }else if(scheduleTask.getCode().equals("TAOBAO_FLASH_SYNC")){
            scheduleExecute.executeTaobaoFlashSync();
        }else if(scheduleTask.getCode().equals("TAOBAO_FLASH_SOLDNUM")){
            scheduleExecute.executeTaobaoFlashSoldNum();
        }else if(scheduleTask.getCode().equals("TAOBAO_SELECTION_SYNC")){
            scheduleExecute.executeTaobaoSelectionSync();
        }else if(scheduleTask.getCode().equals("TAOBAO_FAVOURABLE_SYNC")){
            scheduleExecute.executeTaobaoFavourableSync();
        }else if(scheduleTask.getCode().equals("TAOBAO_POPULAR_SYNC")){
            scheduleExecute.executeTaobaoPopularSync();
        }else if(scheduleTask.getCode().equals("TAOBAO_ORDER_SYNC")){
            scheduleExecute.executeTaobaoOrderSync();
        }else if(scheduleTask.getCode().equals("TAOBAO_ORDER_SYNC_EX")){
            scheduleExecute.executeTaobaoOrderSyncEx();
        }else if(scheduleTask.getCode().equals("APP_GROUP_SMS")){
            scheduleExecute.executeAppGroupSms();
        }else if(scheduleTask.getCode().equals("APP_NEWS_PUSH")){
            scheduleExecute.executeAppNewsPush();
        }else if(scheduleTask.getCode().equals("SHOP_ORDER_CLOSED")){
            scheduleExecute.executeShopOrderClosed();
        }else if(scheduleTask.getCode().equals("APP_MEMBER_TERM")){
            scheduleExecute.executeAppMemberTerm();
        }else if(scheduleTask.getCode().equals("APP_PEND_WITHDRAW")){
            scheduleExecute.executeAppPendWithdraw();
        }else if(scheduleTask.getCode().equals("APP_REWARD_PAID")){
            scheduleExecute.executeAppRewardPaid();
        }else if(scheduleTask.getCode().equals("TAOBAO_ORDER_REFUND")){
            scheduleExecute.executeTaobaoOrderRefund();
        }else if(scheduleTask.getCode().equals("APP_SPREAD_REWARD")){
            scheduleExecute.executeAppSpreadReward();
        }else if(scheduleTask.getCode().equals("APP_GUIDE_REWARD")){
            scheduleExecute.executeAppGuideReward();
        }else if(scheduleTask.getCode().equals("APP_SALE_REWARD")){
            scheduleExecute.executeAppSaleReward();
        }
        return ApiResponse.make();
    }
}
