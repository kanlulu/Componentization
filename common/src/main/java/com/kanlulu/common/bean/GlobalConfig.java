package com.kanlulu.common.bean;

import android.text.TextUtils;

/**
 * Created by kanlulu
 * DATE: 2018/9/1 21:57
 */
public class GlobalConfig {
    public static final String TOTALAMOUNT          = "TotalAmount";            //最大借款额度
    public static final String TOTALPERIOD          = "TotalPeriod";            //最大借款期限
    public static final String DAYRATE              = "DayRate";                //借款日利率
    public static final String TECHNICALSERVICEFEE  = "TechnicalServiceFee";    //技术服务费
    public static final String OVERDUERATE          = "OverdueRate";            //逾期日率
    public static final String OVERDUEMANAGEFEE     = "OverdueManageFee";       //逾期管理费
    public static final String MINBORROWAMOUNT      = "MinBorrowAmount";        //最小借款额度
    public static final String MINBORROWPERIOD      = "MinBorrowPeriod";        //最小借款期限
    public static final String MAXBANKCOUNT         = "MaxBankCount";           //银行卡最大绑定数量
    public static final String DEFAULTFACEVERIFYTYPE= "DefaultFaceVerifyType";  //默认人脸识别方式 1:Face++ 2:支付宝
    public static final String CONTACTSLIMIT        = "ContactsLimit";          //最小通讯录人数
    public static final String BORROWCYCLE          = "BorrowCycle";            //借款周期设置，例如"10,20"，表示有10天，20天两种周期
    public static final String POSTLOANPHONE        = "PostLoanPhone";          //贷后电话
    public static final String SERVICEPHONE         = "ServicePhone";           //客服电话
    public static final String LOANDEPOSIT          = "FirstLoanDeposit";       //首借保证金费率
    public static final String EXTENDFEERATE        = "ExtendFeeRate";          //展期服务费
    public static final String SERVICEQQ            = "ServiceQQ";              //qq群
    public static final String SERVICEWECHAT        = "ServiceWeChat";          //微信公众号
    public static final String DISABLEYZPAY        = "disableYZPay";        //关闭汇潮支付


    public int id;//配置编号
    public String name;//配置名称 参考配置集合（待完成）
    public String value;//配置值
    public String description;//说明

    public boolean isMyWant(String target) {
        return TextUtils.equals(name, target);
    }
}
