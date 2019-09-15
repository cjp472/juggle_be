package com.juggle.juggle.primary;

public class Constants {
    public static class ACCESS_TOKEN_TYPE{
        public static final String USER = "USER";
        public static final String MEMBER = "MEMBER";
    }

    public static class USER_STATUS{
        public static final String NORMAL = "NORMAL";
        public static final String FORBID = "FORBID";
    }

    public static class MEMBER_GRADE{
        public static final String GRADE1 = "GRADE1";
        public static final String GRADE2 = "GRADE2";
    }

    public static class MEMBER_STATUS{
        public static final String NORMAL = "NORMAL";
        public static final String FORBID = "FORBID";
    }

    public static class PAY_WAY{
        public static final String ALIPAY = "ALIPAY";
        public static final String WXPAY = "WXPAY";
    }

    public static class PHONE_BILL_STATUS{
        public static final String OPEN = "OPEN";
        public static final String WAIT = "WAIT";
        public static final String COMPLETE = "COMPLETE";
    }

    public static class WITHDRAW_ACCOUNT_TYPE{
        public static final String ALIPAY = "ALIPAY";
        public static final String BANK = "BANK";
    }

    public static class WITHDRAW_STATUS{
        public static final String PROCESS = "PROCESS";   //审核中
        public static final String PEND = "PEND";   //待打款
        public static final String PAID = "PAID";  //已打款
        public static final String INVALID = "INVALID";  //无效
    }

    public static class SCHEDULE_TASK_TYPE{
        public static final String TIME = "TIME";   //定时任务
        public static final String INTERVAL = "INTERVAL";   //间歇任务
    }

    public static class SCHEDULE_TASK_LAST_SYNC_STATUS{
        public static final String ING = "ING";
        public static final String SUCCESS = "SUCCESS";
        public static final String FAIL = "FAIL";
    }

    public static class SCHEDULE_LOG_STATUS{
        public static final String NEW = "NEW";
        public static final String SUCCESS = "SUCCESS";
        public static final String FAIL = "FAIL";
    }

    public static class ADVERT_PROTOCOL{
        public static final String URL = "URL";
        public static final String TAOBAO = "TAOBAO";
        public static final String API = "API";
        public static final String DIRECTION = "DIRECTION";
    }

    public static class RESOURCE_PROTOCOL{
        public static final String URL = "URL";
        public static final String TAOBAO = "TAOBAO";
        public static final String API = "API";
        public static final String DIRECTION = "DIRECTION";
    }

    public static class APPROVAL_PROCESS_INSTANCE_TYPE{
        public static final String CERT = "CERT";
        public static final String WITHDRAW = "WITHDRAW";
    }

    public static class APPROVAL_PROCESS_STATUS{
        public static final String OPEN = "OPEN";
        public static final String PASS = "PASS";
        public static final String REJECT = "REJECT";
    }

    public static class SMS_CODE_STATUS{
        public static final String VERIFIED = "VERIFIED";
        public static final String UNVERIFIED = "UNVERIFIED";
    }

    public static class GROUP_SMS_STATUS{
        public static final String WAIT = "WAIT";
        public static final String COMPLETE = "COMPLETE";
    }

    public static class PUSH_STATUS{
        public static final String WAIT = "WAIT";
        public static final String COMPLETE = "COMPLETE";
    }

    public static class KEYWORD_TYPE{
        public static final String HOT_SEARCH = "HOT_SEARCH";
    }

    public static class DICTIONARY_TYPE{
        public static final String CMS = "CMS";
        public static final String SYSTEM = "SYSTEM";
        public static final String TAOBAO = "TAOBAO";
        public static final String DINGDANXIA = "DINGDANXIA";
        public static final String OSS = "OSS";
        public static final String SMS = "SMS";
        public static final String WXPAY = "WXPAY";
        public static final String ALIPAY = "ALIPAY";
        public static final String EXPRESS = "EXPRESS";
    }

    public static class SHOP_EXPRESS_SHIPPED_COM{
        public static final String YUNDA = "YUNDA";
        public static final String ZTO = "ZTO";
    }

    public static class SHOP_ORDER_STATUS{
        public static final String OPEN = "OPEN";
        public static final String PAID = "PAID";
        public static final String SHIPPED = "SHIPPED";
        public static final String RECEIVED = "RECEIVED";
        public static final String COMPLETE = "COMPLETE";
        public static final String CLOSED = "CLOSED";
        public static final String CANCEL = "CANCEL";
    }

    public static class SHOP_COUPON_TYPE{
        public static final String REACH = "REACH";
    }

    public static class SHOP_COUPON_STATUS{
        public static final String NORMAL = "NORMAL";
        public static final String FORBID = "FORBID";
    }

    public static class SHOP_COUPON_BIND_STATUS{
        public static final String NORMAL = "NORMAL";
        public static final String USED = "USED";
        public static final String EXPIRED = "EXPIRED";
    }

    public static class REWARD_TYPE{
        public static final String SPREAD = "SPREAD";
        public static final String GUIDE = "GUIDE";
        public static final String SALE = "SALE";
    }

    public static class REWARD_ORDER_TYPE{
        public static final String TAOBAO="TAOBAO";
        public static final String SHOP="SHOP";
    }

    public static class REWARD_STATUS{
        public static final String PEND = "PEND";   //待结算
        public static final String PAID = "PAID";   //已结算
        public static final String REFUND = "REFUND";   //已退款
    }

    public static class REWARD_TEAMER_LEVEL{
        public static final String PRIMARY = "PRIMARY";
        public static final String SECONDARY = "SECONDARY";
    }

    public static class TAOBAO_SELECTION_TYPE{
        public static final String TYPE1 = "TYPE1";
        public static final String TYPE2 = "TYPE2";
        public static final String TYPE3 = "TYPE3";
        public static final String TYPE4 = "TYPE4";
    }

    public static class TAOBAO_FAVOURABLE_TYPE{
        public static final String TYPE1 = "TYPE1";
        public static final String TYPE2 = "TYPE2";
        public static final String TYPE3 = "TYPE3";
        public static final String TYPE4 = "TYPE4";
        public static final String TYPE5 = "TYPE5";
        public static final String TYPE6 = "TYPE6";
        public static final String TYPE7 = "TYPE7";
    }

    public static class TAOBAO_POPULAR_TYPE{
        public static final String TYPE1 = "TYPE1";
        public static final String TYPE2 = "TYPE2";
    }

    public static class TAOBAO_FLASH_RUSH_TIME{
        public static final String ZERO = "00:00";
        public static final String EIGHT = "08:00";
        public static final String TWELVE = "12:00";
        public static final String SIXTEEN = "16:00";
        public static final String TWENTY_ONE = "21:00";
    }

    public static class TAOBAO_SUB{
        public static final String PRODUCT = "PRODUCT";
        public static final String SELECTION = "SELECTION";
        public static final String GOODS = "GOODS";
        public static final String FLASH = "FLASH";
        public static final String FAVOURABLE = "FAVOURABLE";
        public static final String POPULAR = "POPULAR";
    }

    public static class GLOBAL_SEARCH{
        public static final String MEMBER = "MEMBER";
        public static final String SHOP_PRODUCT = "SHOP_PRODUCT";
        public static final String TAOBAO_GOODS = "TAOBAO_GOODS";
        public static final String TAOBAO_PRODUCT = "TAOBAO_PRODUCT";
        public static final String TAOBAO_SELECTION = "TAOBAO_SELECTION";
        public static final String TAOBAO_FAVOURABLE = "TAOBAO_FAVOURABLE";
        public static final String TAOBAO_FLASH = "TAOBAO_FLASH";
        public static final String TAOBAO_POPULAR = "TAOBAO_POPULAR";
        public static final String ARTICLE = "ARTICLE";
    }

    public static class IDS {
        public static final String ADVERT_TYPE = "ADVERT_TYPE";
        public static final String TAOBAO_CATEGORY = "TAOBAO_CATEGORY";
        public static final String COMMUNITY = "COMMUNITY";
        public static final String ARTICLE = "ARTICLE";
        public static final String RESOURCE = "RESOURCE";
        public static final String TAOBAO_TYPE = "TAOBAO_TYPE";
        public static final String SHOP_TYPE = "SHOP_TYPE";
        public static final String SHOP_TAG = "SHOP_TAG";
        public static final int PAD = 6;
        public static final int PAD_L = 8;
    }
}
