package com.yql.common.utils;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.yql.common.base.BaseApplication;
import com.yql.common.network.ApiConstants;
import com.yql.common.tools.CommonDataManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * 用户行为统计工具类
 * Created by lingxiaoming on 2017/11/14 0014.
 */

public class EventStatisticsUtils {
    private static EventStatisticsUtils mEventStatisticsUtils;

    private static String ChannelName;//渠道名
    private static String MobileInfo;//手机信息

    private static final String MainActivity = "主页";
    private static final String LoginActivity = "登录页";
    private static final String CreditActivity = "实名认证页";
    private static final String BaseInformationActivity = "基本资料认证页";
    private static final String AddBankCardActivity = "添加新银行卡页";
    private static final String BorrowEnsureActivity = "订单确认页";
    private static final String BorrowContractActivity = "电子合同页";
    private static final String RepayEnsureActivity = "还款核对页";

    private static HashMap<String, String> pageClassNameMap;//所有页面类集合
    private static String uuid;//数据唯一识别码，每次上传一批不同的uuid
    private static List<EventBeanBatch> eventBeanBatchList;//保存在pref中的用户操作记录，一般是没有上传的
    private static EventBeanBatch currentEventBeanBatch;//当次用户行为，包括uuid


    private EventStatisticsUtils() {
        initMap();
        ChannelName = MetaUtils.getMetaValue(BaseApplication.getInstance().getApplicationContext(), ApiConstants.UMENG_CHANNEL);
        MobileInfo = DeviceUtils.getMobileInfo();
        uuid = UUID.randomUUID().toString();

        String eventBeanBatchListStr = PrefsUtils.getInstance().getStringByKey(PrefsUtils.USEREVENT);
//        eventBeanBatchList = JSONObject.parseArray(eventBeanBatchListStr, EventBeanBatch.class);
        eventBeanBatchList = new Gson().fromJson(eventBeanBatchListStr, new TypeToken<List<EventBeanBatch>>() {
        }.getType());
        if (eventBeanBatchList == null) {
            eventBeanBatchList = new ArrayList<>();
        }

        currentEventBeanBatch = new EventBeanBatch();
        currentEventBeanBatch.batchNo = uuid;
        currentEventBeanBatch.list = new ArrayList<>();
        eventBeanBatchList.add(currentEventBeanBatch);
    }

    private void initMap() {
        pageClassNameMap = new HashMap<>();
        pageClassNameMap.put("MainActivity", MainActivity);
        pageClassNameMap.put("LoginActivity", LoginActivity);
        pageClassNameMap.put("CreditActivity", CreditActivity);
        pageClassNameMap.put("BaseInformationActivity", BaseInformationActivity);
        pageClassNameMap.put("AddBankCardActivity", AddBankCardActivity);
        pageClassNameMap.put("BorrowEnsureActivity", BorrowEnsureActivity);
        pageClassNameMap.put("BorrowContractActivity", BorrowContractActivity);
        pageClassNameMap.put("ExtensionEnsureActivity", RepayEnsureActivity);
    }

    public static EventStatisticsUtils getInstance() {
        if (mEventStatisticsUtils == null) {
            mEventStatisticsUtils = new EventStatisticsUtils();
        }
        return mEventStatisticsUtils;
    }


    public void event(Activity activity, String eventName) {
        currentEventBeanBatch.list.add(new EventBean(pageClassNameMap.get(activity.getClass().getSimpleName()), eventName));
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
        String eventList = gson.toJson(eventBeanBatchList);
        PrefsUtils.getInstance().saveStringByKey(PrefsUtils.USEREVENT, eventList);
    }

    public static class EventBeanBatch {
        public String batchNo;//批次编号，唯一
        public List<EventBean> list;//包含的用户行为列表
    }


    public static class EventBean {
        public String page;//页面名称
        public String operation;//事件名称
        public long recordTime;//记录时间
        public int userId;//用户id
        public String channel;//渠道名
        public String clientInfo;//手机信息

        public EventBean(String activityName, String eventName) {
            this.page = activityName;
            this.operation = eventName;
            recordTime = System.currentTimeMillis();
            userId = CommonDataManager.getUser() == null ? 0 : CommonDataManager.getUser().id;
            channel = ChannelName;
            clientInfo = MobileInfo;
        }

        public EventBean() {
        }
    }
}
