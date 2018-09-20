package com.yql.common.tools;

import android.text.TextUtils;

import com.yql.common.bean.Statistics;
import com.yql.common.bean.SupportBanksInfo;
import com.yql.common.bean.User;
import com.yql.common.bean.Version;
import com.yql.common.utils.PrefsUtils;

/**
 * Created by kanlulu
 * DATE: 2018/9/1 20:45
 */
public class CommonDataManager {
    private static String token;
    private static String mobile;
    private static String pwd;

    private static int maxBorrowMoney = 5000;//最大借款额度
    private static int maxBorrowDay = 30;//最大借款期限
    private static float dayRate = 0.0005F;//借款日利率
    private static float serviceFee = 0.0075F;//技术服务费
    private static float overRate = 0.0005F;//逾期日率
    private static float overManagerFee = 0.0095F;//逾期管理费
    private static float minBorrowAmount = 500F;//最小借款额度
    private static float minBorrowDay = 7F;//最小借款期限
    private static int maxBankCount = 3;//银行卡最大绑定数量
    private static int defaultFaceAuth = 1;//默认人脸识别方式 1:Face++ 2:支付宝
    private static int minContacts = 10;//通讯录最少人数
    private static int borrowCycle[] = new int[]{20};//借款周期设置，例如"10,20"，表示有10天，20天两种周期
    private static String ServicePhone = "0571-87859758";
    private static String PostLoanPhone = "0571-86721931";
    private static float FirstLoanDeposit = 0.05f;
    private static float ExtendFeeRate = 0.003F;//展期服务费
    private static String ServiceQQ = "819705340";
    private static String ServiceWeChat = "稳信金融";
    private static boolean disableYZPay = false;//关闭汇潮支付

    private static User user = new User();
    private static SupportBanksInfo supportBanksInfo = new SupportBanksInfo(); // 获取支持银行信息
    private static Version version;//app服务器最新版本信息
    private static Statistics statistics = new Statistics();//是否有逾期或即将逾期账单，待还金额

    public synchronized static String getToken() {
        if (TextUtils.isEmpty(token)) {
            token = PrefsUtils.getInstance().getStringByKey(PrefsUtils.ACCESS_TOKEN);
        }
        return token;
    }

    public synchronized static String getMobile() {
        if (TextUtils.isEmpty(mobile)) {
            mobile = PrefsUtils.getInstance().getStringByKey(PrefsUtils.MOBILE);
        }
        return mobile;
    }

    public synchronized static String getPwd() {
        if (TextUtils.isEmpty(pwd)) {
            pwd = PrefsUtils.getInstance().getStringByKey(PrefsUtils.PWD);
        }
        return pwd;
    }

    public synchronized static float getDayRate() {
        return dayRate;
    }

    public synchronized static int getMaxBorrowMoney() {
        return maxBorrowMoney;
    }

    public synchronized static int getMaxBorrowDay() {
        return maxBorrowDay;
    }

    public synchronized static User getUser() {
        if (user == null || user.id == 0) {
            user = PrefsUtils.getInstance().getLoginUser();
        }
        return user;
    }

    public static float getServiceFee() {
        return serviceFee;
    }


    public static float getOverRate() {
        return overRate;
    }

    public static Version getVersion() {
        return version;
    }

    public static SupportBanksInfo getSupportBanksInfo() {
        return supportBanksInfo;
    }

    public static Statistics getStatistics() {
        return statistics;
    }

    public static float getOverManagerFee() {
        return overManagerFee;
    }

    public static float getMinBorrowAmount() {
        return minBorrowAmount;
    }

    public static float getMinBorrowDay() {
        return minBorrowDay;
    }

    public static int getMaxBankCount() {
        return maxBankCount;
    }

    public static int getDefaultFaceAuth() {
        return defaultFaceAuth;
    }

    public static int getMinContacts() {
        return minContacts;
    }

    public static void setStatistics(Statistics statistics) {//所有属性赋值，避免对象替换databinding失效
        if (statistics == null) statistics = new Statistics();
        if (CommonDataManager.statistics != null) {
            CommonDataManager.statistics.updateStatistics(statistics);
        } else {
            CommonDataManager.statistics = statistics;
        }
    }


    public static int[] getBorrowCycle() {
        return borrowCycle;
    }

    public static void setServiceFee(float serviceFee) {
        CommonDataManager.serviceFee = serviceFee;
    }

    public synchronized static void setToken(String token) {
        CommonDataManager.token = token;
        PrefsUtils.getInstance().saveStringByKey(PrefsUtils.ACCESS_TOKEN, token);
    }

    public synchronized static void setMobile(String mobile) {
        CommonDataManager.mobile = mobile;
        PrefsUtils.getInstance().saveStringByKey(PrefsUtils.MOBILE, mobile);
    }

    public synchronized static void setPwd(String pwd) {
        CommonDataManager.pwd = pwd;
        PrefsUtils.getInstance().saveStringByKey(PrefsUtils.PWD, pwd);
    }

    public synchronized static void setDayRate(float dayRate) {
        CommonDataManager.dayRate = dayRate;
    }

    public synchronized static void setMaxBorrowMoney(int maxBorrowMoney) {
        CommonDataManager.maxBorrowMoney = maxBorrowMoney;
    }

    public synchronized static void setMaxBorrowDay(int maxBorrowDay) {
        CommonDataManager.maxBorrowDay = maxBorrowDay;
    }

    public synchronized static void setUser(User user) {
        if (user == null) user = new User();
        if (CommonDataManager.user != null) {
            CommonDataManager.user.updateUser(user);
        } else {
            CommonDataManager.user = user;
        }
        PrefsUtils.getInstance().saveLoginUser(user);
    }

    public synchronized static void setVersion(Version version) {
        if (CommonDataManager.version != null && version != null) {
            CommonDataManager.version.updateVersion(version);
        } else {
            CommonDataManager.version = version;
        }
    }

    public synchronized static void setSupportBanks(SupportBanksInfo supportBanksInfo) {
        if (CommonDataManager.supportBanksInfo != null && supportBanksInfo != null) {
            CommonDataManager.supportBanksInfo.updateSupportBanks(supportBanksInfo);
        } else {
            CommonDataManager.supportBanksInfo = supportBanksInfo;
        }
    }

    public static void setOverRate(float overRate) {
        CommonDataManager.overRate = overRate;
    }

    public static void setOverManagerFee(float overManagerFee) {
        CommonDataManager.overManagerFee = overManagerFee;
    }

    public static void setMinBorrowAmount(float minBorrowAmount) {
        CommonDataManager.minBorrowAmount = minBorrowAmount;
    }

    public static void setMinBorrowDay(float minBorrowDay) {
        CommonDataManager.minBorrowDay = minBorrowDay;
    }

    // 银行卡最大绑定数量
    public static void setMaxBankCount(int maxBankCount) {
        CommonDataManager.maxBankCount = maxBankCount;
    }

    //默认人脸识别方式 1:Face++ 2:支付宝
    public static void setDefaultFaceAuth(int defaultFaceAuth) {
        CommonDataManager.defaultFaceAuth = defaultFaceAuth;
    }

    //最少通讯录人数
    public static void setMinContacts(int minContacts) {
        CommonDataManager.minContacts = minContacts;
    }

    //借款周期设置，例如"10,20"，表示有10天，20天两种周期
    public static void setBorrowCycle(String borrowCycle) {
        String[] arrs = borrowCycle.split(",");
        int[] ints = new int[arrs.length];
        for (int i = 0; i < arrs.length; i++) {
            ints[i] = Integer.parseInt(arrs[i]);
        }
        CommonDataManager.borrowCycle = ints;
    }

    public static int getMaxBorrowCycleDay() {
        int tempMax = 0;
        for (int i : borrowCycle) {
            if (i > tempMax) {
                tempMax = i;
            }
        }
        return tempMax;
    }

    public static String getServicePhone() {
        return ServicePhone;
    }

    public static float getExtendFeeRate() {
        return ExtendFeeRate;
    }

    public static void setServicePhone(String servicePhone) {
        ServicePhone = servicePhone;
    }

    public static void setExtendFeeRate(float extendFeeRate) {
        ExtendFeeRate = extendFeeRate;
    }

    public static String getPostLoanPhone() {
        return PostLoanPhone;
    }

    public static void setPostLoanPhone(String postLoanPhone) {
        PostLoanPhone = postLoanPhone;
    }

    public static float getFirstLoanDeposit() {
        return FirstLoanDeposit;
    }

    public static void setFirstLoanDeposit(float firstLoanDeposit) {
        FirstLoanDeposit = firstLoanDeposit;
    }

    public static String getServiceWeChat() {
        return ServiceWeChat;
    }

    public static void setServiceWeChat(String serviceWeChat) {
        ServiceWeChat = serviceWeChat;
    }

    public static String getServiceQQ() {
        return ServiceQQ;
    }

    public static void setServiceQQ(String serviceQQ) {
        ServiceQQ = serviceQQ;
    }

    public static boolean isDisableYZPay() {
        return disableYZPay;
    }

    public static void setDisableYZPay(boolean disableYZPay) {
        CommonDataManager.disableYZPay = disableYZPay;
    }
}
