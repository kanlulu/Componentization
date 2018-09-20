package com.yql.common.bean;


import android.databinding.BaseObservable;

import com.yql.common.BR;

/**
 * Created by kanlulu
 * DATE: 2018/9/1 21:18
 */
public class SupportBanksInfo extends BaseObservable {
    public String support_banks;//支持银行名称，如“工商银行、中国银行。”

    public void updateSupportBanks(SupportBanksInfo info){
        this.support_banks = info.support_banks;
        notifyPropertyChanged(BR._all);//必须更新下
    }

    public String getBanks() {
        return support_banks;
    }
}
