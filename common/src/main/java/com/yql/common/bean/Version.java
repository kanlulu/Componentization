package com.yql.common.bean;

import android.databinding.BaseObservable;

import com.yql.common.BR;
/**
 * Created by kanlulu
 * DATE: 2018/9/1 21:23
 */
public class Version extends BaseObservable{
    public int app_code;//app当前版本编号，如：1，依次往上加
    public String app_version;//app当前版本号，如：“1.0.0”
    public String download_url;//app最新版下载地址
    public boolean forced;//强制更新，true（强制）、false（不强制）
    public String version_desc;//版本描述
    public int version_size;//版本大小，如：500000（byte）

    public void updateVersion(Version version){
        this.app_code = version.app_code;
        this.app_version = version.app_version;
        this.download_url = version.download_url;
        this.forced = version.forced;
        this.version_desc = version.version_desc;
        this.version_size = version.version_size;


        notifyPropertyChanged(BR._all);//必须更新下
    }
}
