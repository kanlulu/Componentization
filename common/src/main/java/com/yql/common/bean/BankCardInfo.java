package com.yql.common.bean;

import java.io.Serializable;

/**
 * Created by kanlulu
 * DATE: 2018/9/1 20:50
 */
public class BankCardInfo  implements Serializable{
    public static final long serialVersionUID = 1L;

    public String bankOrderNo;//		银行卡绑定订单编号。自定义订单编号，主要用于查询银行卡绑定状态
    public int userId;//	借款用户编号
    public int id;//	银行卡编号
    public String mobile;//	绑卡手机号
    public String name;//	绑卡用户姓名
    public String idNumber;//	绑卡用户身份证
    public String bankCardNo;//	银行卡号
    public String bankName;//	银行名称
    public String province;//	所属省份
    public String city;//	所属城市
    public String depositBank;//	开户行，支行名称
    public long createTime;//	连连签约协议号
    public String userToken;//	登录授权Token
    public String requestId;//	绑卡申请编号（用于请求绑卡通道）
    public long applyTime;//	绑卡申请时间
    public int bindStatus;//	0:未绑定; 1:已申请; 2:绑定成功; 3:绑定失败; 4:已解绑   只有已绑定状态的银行卡信息才是有效的银行卡信息
    public long bindTime;//	绑定时间
    public boolean isDefault;//	是否默认银行卡 0:否  1:是
    public BankBaseInfo bank_base_info;//		所属银行基础信息(含限额及银行状态)
}
