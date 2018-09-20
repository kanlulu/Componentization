package com.yql.common.bean;

import android.databinding.BaseObservable;
import com.yql.common.BR;
/**
 * Created by kanlulu
 * DATE: 2018/9/1 21:24
 */
public class Statistics extends BaseObservable{
    public boolean hasOverdue;//是否有逾期账单 false:无逾期 true:有逾期
    public boolean hasToBeOverdue;//是否有即将逾期的账单 false:无 true:有 （以还款日前推两天为标准，如还款日为13日，则11 12日都符合标准）
    public double remainRepayAmount;//当前用户待还款总金额（无论当前是否是到期日，只要是未还款，就计入该金额）
    public boolean canRaiseQuota;//是否有提额资质 false:无提额资质 true:有提额资质

    public void updateStatistics(Statistics statistics){
        this.hasOverdue = statistics.hasOverdue;
        this.hasToBeOverdue = statistics.hasToBeOverdue;
        this.remainRepayAmount = statistics.remainRepayAmount;
        this.canRaiseQuota = statistics.canRaiseQuota;


        notifyPropertyChanged(BR._all);//必须更新下
    }
}
