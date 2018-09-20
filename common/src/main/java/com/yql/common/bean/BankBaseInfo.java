package com.yql.common.bean;

import java.io.Serializable;

/**
 * Created by kanlulu
 * DATE: 2018/9/1 20:51
 */
public class BankBaseInfo implements Serializable{
    public static final long serialVersionUID = 1L;

    public String bankCode;//银行编号
    public String bankName;//银行名称
    public String bankIcon;//	银行图标路径，如/public/img/bankIcon/gongshang.png
    public int cardType;//卡类型 2-借记卡 3-信用卡
    public int singleAmt;//单笔支付限额,单位 元 整数数字
    public int dayAmt;//单日支付限额,单位 元 整数数字
    public int monthAmt;//单月支付限额,单位 元 整数数字
    public int bankStatus;//银行状态 0正常 2维护中
    public long createTime;//	创建时间
    public long updateTime;//	更新时间
    public boolean isDelete;//是否已下架 false:未下架 true:下架 银行是否可用以此为准（以方便控制银行的可用状态）
    public long deleteTime;//	上架下架更新时间
}
