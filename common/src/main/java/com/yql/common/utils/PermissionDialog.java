package com.yql.common.utils;

import android.app.Activity;
import android.support.annotation.NonNull;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.SettingService;

/**
 * 申请权限再次提示弹框
 * Created by lingxiaoming on 2017/8/11 0011.
 */

public class PermissionDialog {
    private Rationale mRationale;

    public void showAgainPermission(@NonNull Rationale rationale) {
        DialogUtils.showTwoBtnDialog("未取到权限", "平台得不到您的允许将无法估算出您的信用额度，是否发起重新授权？", "取消", (view) -> {
            DialogUtils.dismiss();
            mRationale.cancel();
        }, "确定", (view) -> {
            DialogUtils.dismiss();
            mRationale.resume();
        });
        this.mRationale = rationale;
    }

    public void showAgainPermission(@NonNull Rationale rationale, String content) {
        DialogUtils.showTwoBtnDialog("提示", content, "取消", (view) -> {
            DialogUtils.dismiss();
            mRationale.cancel();
        }, "确定", (view) -> {
            DialogUtils.dismiss();
            mRationale.resume();
        });
        this.mRationale = rationale;
    }

    public void showAgainPermissionExternal(@NonNull Rationale rationale) {
        DialogUtils.showTwoBtnDialog("未取到权限", "未获得读写外部存储卡权限，您将无法查看合同，是否发起重新授权？", "取消", (view) -> {
            DialogUtils.dismiss();
            mRationale.cancel();
        }, "确定", (view) -> {
            DialogUtils.dismiss();
            mRationale.resume();
        });
        this.mRationale = rationale;
    }

    public void showAgainPermissionExternal2(@NonNull Rationale rationale) {
        DialogUtils.showTwoBtnDialog("未取到权限", "未获得读写外部存储卡权限，您将无法更新应用，是否发起重新授权？", "取消", (view) -> {
            DialogUtils.dismiss();
            mRationale.cancel();
        }, "确定", (view) -> {
            DialogUtils.dismiss();
            mRationale.resume();
        });
        this.mRationale = rationale;
    }

    public void showgoToSetting(Activity activity, String content, int requestCode) {
        SettingService settingService = AndPermission.defineSettingDialog(activity, requestCode);
        DialogUtils.showTwoBtnDialog("提示", content + "\n" +
                "操作路径：设置->应用->稳信钱包->权限", "取消", (view) -> {
            settingService.cancel();
            DialogUtils.dismiss();
        }, "去设置", (view) -> {
            settingService.execute();
            DialogUtils.dismiss();
        });
    }

    public void showgoToSettingReal(Activity activity, String content, int requestCode) {
        SettingService settingService = AndPermission.defineSettingDialog(activity, requestCode);
        DialogUtils.showTwoBtnDialog("提示", content, "取消", (view) -> {
            settingService.cancel();
            DialogUtils.dismiss();
        }, "去设置", (view) -> {
            settingService.execute();
            DialogUtils.dismiss();
        });
    }
}
