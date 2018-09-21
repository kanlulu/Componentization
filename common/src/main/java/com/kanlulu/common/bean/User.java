package com.kanlulu.common.bean;

import android.databinding.BaseObservable;
import android.text.TextUtils;

import com.kanlulu.common.BR;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kanlulu
 * DATE: 2018/9/1 20:48
 */
public class User extends BaseObservable implements Serializable {
    public static final long serialVersionUID = 1L;
    public int id; // 用户编号
    public String mobile;//手机号
    public String mobileProvince;//手机号所属省份
    public String mobileCity;//手机号所属城市
    public int idVerified;//是否已实名认证 0:未实名 1:已实名
    public String name;//真实姓名
    public int sex;//性别 0:男 1:女
    public String idNumber;//身份证号
    public String idNumProvince;//身份证所属省份
    public String idNumCity;//身份证所属城市
    public int age;//	年龄
    public int marriageStatus;//	婚姻状态 0:未婚 1:已婚 2:未知
    public String pwd; // 登录密码
    public String ip; // IP地址
    public boolean basicInfoAuth;//基本资料是否已认证
    public long basicInfoAuthTime;    //基本资料认证时间
    public int familyType; // 亲属关系类型  0:未填写 1:父母 2:兄弟姐妹 3:配偶
    public String familyPhone; // 亲属手机号码
    public int socialType; // 社会关系类型 0:未填写 1:同事 2:朋友 3:其他
    public String socialPhone; // 社会关系人员联系方式
    public String emeContact; // 紧急联系人
    public String emeContactMobile; // 紧急联系人手机号
    public String emeContactRelation; // 紧急联系人与用户关系
    public String gpsProvince; // GPS定位省份
    public String gpsCity; // GPS定位城市
    public String gpsLocation; // GPS具体位置
    public long registTime; // 注册时间
    public long lastLoginTime; // 最后登录时间
    public int inviteUserId;//邀请人用户ID
    public String inviteCode;//邀请码
    public boolean hasCredit;//是否成功授信过。 true：成功授信过 false：未成功授信过
    public long lastCreditTime; // 最后成功授信时间（并非授信申请的生成时间，而是成功给了额度的授信时间）
    public long lastCreditExpiredTime; // 最后成功授信过期时间（一般与授信时间相差90天）
    public double creditTotalAmount;// 授信总额度
    public double creditRemainAmount;// 剩余授信额度
    public String equipType;//设备型号（一般指手机型号）
    public String equipFingerprint;//设备指纹
    public int grayandblack;//是否黑/灰名单用户 0:不是 1：是
    public int hasMobileVerify;//是否通过手机运营商验证(0:未验证 1:验证通过 2:不通过 3:验证中)
    public long mobileVerifyPassTime;//运营商验证通过时间,用于校验运营商验证是否过期，一般30天，可配置。若过期，则需要刷新运营商
    public long mobileVerifyExpiredTime;//运营商验证过期时间(一般与通过时间相差30天)
    public int zhimaCreditCheck;//是否完成芝麻信用认证(0:未验证 1:验证通过 2:验证不通过)
    public long zhimaVerifyPassTime;//芝麻信用认证通过时间
    public int taobaoCheck;//是否完成淘宝认证(0:未验证 1:验证通过 2:验证不通过 3:验证中)
    public long taobaoVerifyPassTime;//淘宝认证通过时间
    public int jingdongCheck;//是否完成京东认证(0:未验证 1:验证通过 2:验证不通过 3:验证中)
    public long jingdongVerifyPassTime;//京东认证通过时间
    public int pbankCreditCheck;//是否完成人行征信认证(0:未验证 1:验证通过 2:验证不通过 3:验证中)
    public long pbankVerifyPassTime;//人行征信认证通过时间
    public String zhimaOpenid;//芝麻认证openId
    public String remark;//备注
    public boolean hasSaveContacts;//通讯录是否已保存 true:已保存  false:未保存
    public List<BankCardInfo> bank_list;//当前用户包含的银行卡信息列表
    public int zmCreditScore;

    public String companyName;//	单位名称
    public String companyAddress;//		单位地址
    public String companyPhone;//	单位联系方式
    public String idCardBackPath;//	身份证背面照地址
    public String idCardPhotoPath;//	身份证正面照地址
    public String socialName;//社会关系人姓名
    public String familyName;//亲属姓名

    public void updateUser(User user) {
        this.id = user.id;
        this.mobile = user.mobile;
        this.mobileProvince = user.mobileProvince;
        this.mobileCity = user.mobileCity;
        this.idVerified = user.idVerified;
        this.name = user.name;
        this.sex = user.sex;
        this.idNumber = user.idNumber;
        this.idNumProvince = user.idNumProvince;
        this.idNumCity = user.idNumCity;
        this.age = user.age;
        this.marriageStatus = user.marriageStatus;
        this.pwd = user.pwd;
        this.ip = user.ip;
        this.basicInfoAuth = user.basicInfoAuth;
        this.basicInfoAuthTime = user.basicInfoAuthTime;
        this.familyType = user.familyType;
        this.familyPhone = user.familyPhone;
        this.socialType = user.socialType;
        this.socialPhone = user.socialPhone;
        this.emeContact = user.emeContact;
        this.emeContactMobile = user.emeContactMobile;
        this.emeContactRelation = user.emeContactRelation;
        this.gpsProvince = user.gpsProvince;
        this.gpsCity = user.gpsCity;
        this.gpsLocation = user.gpsLocation;
        this.registTime = user.registTime;
        this.lastLoginTime = user.lastLoginTime;
        this.inviteUserId = user.inviteUserId;
        this.inviteCode = user.inviteCode;
        this.hasCredit = user.hasCredit;
        this.lastCreditTime = user.lastCreditTime;
        this.lastCreditExpiredTime = user.lastCreditExpiredTime;
        this.creditTotalAmount = user.creditTotalAmount;
        this.creditRemainAmount = user.creditRemainAmount;
        this.equipType = user.equipType;
        this.equipFingerprint = user.equipFingerprint;
        this.hasMobileVerify = user.hasMobileVerify;
        this.mobileVerifyPassTime = user.mobileVerifyPassTime;
        this.mobileVerifyExpiredTime = user.mobileVerifyExpiredTime;
        this.zhimaCreditCheck = user.zhimaCreditCheck;
        this.zhimaVerifyPassTime = user.zhimaVerifyPassTime;
        this.taobaoCheck = user.taobaoCheck;
        this.taobaoVerifyPassTime = user.taobaoVerifyPassTime;
        this.jingdongCheck = user.jingdongCheck;
        this.jingdongVerifyPassTime = user.jingdongVerifyPassTime;
        this.pbankCreditCheck = user.pbankCreditCheck;
        this.pbankVerifyPassTime = user.pbankVerifyPassTime;
        this.zhimaOpenid = user.zhimaOpenid;
        this.remark = user.remark;
        this.hasSaveContacts = user.hasSaveContacts;
        this.bank_list = user.bank_list;
        this.companyName = user.companyName;
        this.companyAddress = user.companyAddress;
        this.companyPhone = user.companyPhone;
        this.idCardBackPath = user.idCardBackPath;
        this.idCardPhotoPath = user.idCardPhotoPath;
        this.grayandblack = user.grayandblack;
        this.socialName = user.socialName;
        this.familyName = user.familyName;
        notifyPropertyChanged(BR._all);
    }

    public String getPassword() {
        return pwd;
    }

    public BankCardInfo getDefaultBankCard() {
        if (bank_list == null) return null;
        for (BankCardInfo bank : bank_list) {
            if (bank.isDefault) {
                return bank;
            }
        }
        return null;
    }

    public String getCompanyAddress1() {
        if (TextUtils.isEmpty(companyAddress)) return "";
        if (!companyAddress.contains("@@@")) return companyAddress;
        String[] strings = companyAddress.split("@@@");
        return strings.length > 1 ? strings[0] : "";
    }

    public String getCompanyAddress2() {
        if (TextUtils.isEmpty(companyAddress)) return "";
        if (!companyAddress.contains("@@@")) return companyAddress;
        String[] strings = companyAddress.split("@@@");
        return strings.length > 1 ? strings[1] : strings[0];
    }

    public String getCompanyPhone1() {
        if (TextUtils.isEmpty(companyPhone)) return "";
        if (!companyPhone.contains("@@@")) return companyPhone;
        String[] strings = companyPhone.split("@@@");
        return strings.length >= 1 ? strings[0] : "";
    }

    public String getCompanyPhone2() {
        if (TextUtils.isEmpty(companyPhone)) return "";
        if (!companyPhone.contains("@@@")) return companyPhone;
        String[] strings = companyPhone.split("@@@");
        return strings.length > 1 ? strings[1] : "";
    }

    public boolean isAllRealName() {
        if (idVerified == 1 && !TextUtils.isEmpty(idCardBackPath) && !TextUtils.isEmpty(idCardPhotoPath))
            return true;
        return false;
    }

    public boolean isNeedBack() {
        if (idVerified == 1 && TextUtils.isEmpty(idCardBackPath))
            return true;
        return false;
    }
}
