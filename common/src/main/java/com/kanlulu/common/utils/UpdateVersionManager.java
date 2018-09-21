package com.kanlulu.common.utils;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kanlulu.common.R;
import com.kanlulu.common.base.BaseApplication;
import com.kanlulu.common.bean.Version;
import com.kanlulu.common.tools.ActivityManager;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionNo;
import com.yanzhenjie.permission.PermissionYes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * 版本更新
 * Created by lingxiaoming on 2017/9/12 0012.
 */

public class UpdateVersionManager {
    private static Dialog dialog;
    private Version version;
    private Activity activity;
    private String mAppName = "kanlulu_module.apk" ;
    private ProgressBar mPrgressBar;
    private TextView mTextViewProgress;

    private static final int DOWNLOAD = 1;//下载中
    private static final int DOWNLOAD_FINISH = 2;//下载结束
    private static final int DOWNLOAD_FAILED = 3;//下载失败
    private boolean cancelUpdate = false;

    private String mSavePath;//本地保存apk目录

    public UpdateVersionManager(Version version, Activity activity) {
        this.version = version;
        this.activity = activity;
    }

    public void checkUpdate() {
        if (version.app_code > AppVersionUtils.getVersionCode()) {
            if (version.forced) {
                showMustUpdateDialog();//必须更新弹框
            } else {
                showChoiceUpdateDialog();//可选是否更新弹框
            }
        }
    }

    private void showMustUpdateDialog() {
        showSingleBtnDialogInActivity(getActivity(), "发现新版本",version.version_desc, "马上升级", downloadNewVersionListener);
        if(dialog != null)  dialog.setCancelable(false);
    }

    private void showChoiceUpdateDialog() {
        showTwoBtnDialog("发现新版本",version.version_desc, "取消", null, "升级", downloadNewVersionListener);
    }


    private static Activity getActivity() {
        return ActivityManager.getAppManager().currentActivity();
    }

    /**
     * 两个按钮的弹框
     */
    public  void showTwoBtnDialog(String title, String content, String btnLeftString, View.OnClickListener btnLeftOnClickListener, String btnRightString, View.OnClickListener btnRightOnClickListener) {
        Activity activity = getActivity();
        if (activity == null || activity.isFinishing()) return;
//        dismiss();
        dialog = new Dialog(activity, R.style.MyDialogStyle);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_two_button);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView tv_title = (TextView) dialog.findViewById(R.id.tv_title);
        TextView tv_content = (TextView) dialog.findViewById(R.id.tv_content);
        TextView tv_left = (TextView) dialog.findViewById(R.id.tv_left);
        TextView tv_right = (TextView) dialog.findViewById(R.id.tv_right);

        if (TextUtils.isEmpty(title)) {
            tv_title.setVisibility(View.GONE);
        } else {
            tv_title.setVisibility(View.VISIBLE);
            tv_title.setText(title);
        }

        if (TextUtils.isEmpty(content)) {
            tv_content.setVisibility(View.GONE);
        } else {
            tv_content.setVisibility(View.VISIBLE);
            tv_content.setText(Html.fromHtml(content));
        }

        tv_left.setText(btnLeftString);
        if (btnLeftOnClickListener == null) {
            tv_left.setOnClickListener(v -> dialog.dismiss());
        } else {
            tv_left.setOnClickListener(downloadNewVersionListener);
        }

        tv_right.setText(btnRightString);
        if (btnRightOnClickListener == null) {
            tv_right.setOnClickListener(v -> dialog.dismiss());
        } else {
            tv_right.setOnClickListener(downloadNewVersionListener);
        }

        dialog.show();
    }

    public  void showSingleBtnDialogInActivity(Activity activity, String title, String content, String btnString, View.OnClickListener onClickListener) {
        if (activity == null || activity.isFinishing()) return;

        dialog = new Dialog(activity, R.style.MyDialogStyle);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_single_button);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView tv_title = (TextView) dialog.findViewById(R.id.tv_title);
        TextView tv_content = (TextView) dialog.findViewById(R.id.tv_content);
        TextView tv_cancel = (TextView) dialog.findViewById(R.id.tv_cancel);

        if (TextUtils.isEmpty(title)) {
            tv_title.setVisibility(View.GONE);
        } else {
            tv_title.setVisibility(View.VISIBLE);
            tv_title.setText(title);
        }

        if (TextUtils.isEmpty(content)) {
            tv_content.setVisibility(View.GONE);
        } else {
            tv_content.setVisibility(View.VISIBLE);
            tv_content.setText(Html.fromHtml(content));
        }

        tv_cancel.setText(btnString);
        if (onClickListener == null) {
            tv_cancel.setOnClickListener(v -> dialog.dismiss());
            tv_cancel.setTextColor(activity.getResources().getColor(R.color.gray_404b59));
        } else {
            tv_cancel.setOnClickListener(downloadNewVersionListener);
            tv_cancel.setTextColor(activity.getResources().getColor(R.color.red_f9542d));

        }

        dialog.show();
    }

    View.OnClickListener downloadNewVersionListener = (view) -> {
        AndPermission.with(activity)
                .requestCode(103)
                .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .rationale((requestCode, rationale) -> new PermissionDialog().showAgainPermissionExternal2(rationale))
                .callback(this)
                .start();
    };

    @PermissionYes(103)
    private void getPermissionYes(List<String> grantedPermissions) {
        new DownloadApkThread().start();
    }

    @PermissionNo(103)
    private void getPermissionNo(List<String> grantedPermissions) {
        if (AndPermission.hasAlwaysDeniedPermission(activity, grantedPermissions)) {
            new PermissionDialog().showgoToSetting(activity, "版本更新功能需要您的读写外部存储权限。", 103);
        } else {
            ToastUtils.showText("获取读写SD卡权限失败，将不能更新新版本");
        }
    }

    class DownloadApkThread extends Thread {
        @Override
        public void run() {
            boolean downloadSucced = false;
            HttpURLConnection conn = null;
            try {
                // 判断SD卡是否存在，并且是否具有读写权限
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) && version.version_size > 0) {
                    // 获得存储卡的路径
                    String sdpath = Environment.getExternalStorageDirectory() + "/";
                    mSavePath = sdpath + "download";
                    URL url = new URL(version.download_url); // 使用服务器返回的地址

                    // 判断是http请求还是https请求
                    if (url.getProtocol().toLowerCase().equals("https")) {
                        final SSLContext sc = SSLContext.getInstance("TLS");
                        sc.init(null, new TrustManager[]{new MyTrustManager()}, new SecureRandom());
                        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
                        HttpsURLConnection.setDefaultHostnameVerifier(new MyHostnameVerifier());
                        conn = (HttpsURLConnection) url.openConnection();
                        //conn.setDoOutput(true);
                        //conn.setDoInput(true);
                    } else {
                        conn = (HttpURLConnection) url.openConnection();
                    }

                    // 创建连接
                    // HttpURLConnection conn = (HttpURLConnection)
                    // url.openConnection();
                    conn.connect();
                    int contentLength = conn.getContentLength();
                    int status = conn.getResponseCode();
                    if(status!=HttpURLConnection.HTTP_OK) {
                        mHandler.sendEmptyMessage(DOWNLOAD_FAILED);
                        return;
                    }

                    // 创建输入流
                    InputStream is = conn.getInputStream();

                    File file = new File(mSavePath);
                    // 判断文件目录是否存在
                    if (!file.exists()) {
                        file.mkdir();
                    }

                    LogUtils.d("UpdateManager", "创建下载文件夹" + file.exists());

                    File apkFile = new File(mSavePath, mAppName);
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    FileOutputStream fos = new FileOutputStream(apkFile);
                    int count = 0;

                    // 缓存
                    byte buf[] = new byte[1024];
                    // 写入到文件中
                    do {
                        int numread = is.read(buf);
                        count += numread;
                        // 计算进度条位置
                        int progress = (int) (((float) count / contentLength) * 100);
                        Message msg = new Message();
                        msg.obj = progress;
                        msg.what = DOWNLOAD;
                        // 更新进度
                        mHandler.sendMessage(msg);
                        if (numread <= 0) {
                            // 下载完成
                            mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
                            break;
                        }
                        // 写入文件
                        fos.write(buf, 0, numread);
                    } while (!cancelUpdate);// 点击取消就停止下载.
                    fos.close();
                    is.close();
                    if (!cancelUpdate)
                        downloadSucced = true;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            } finally {
                if(conn != null)
                    conn.disconnect();
            }
            // 取消下载对话框显示
//            downDialog.dismiss();
            if (!downloadSucced) {// 下载失败提示
                mHandler.sendEmptyMessage(DOWNLOAD_FAILED);
            }
        }
    }

    private WeakHandler mHandler = new WeakHandler(message -> {
        switch (message.what) {
            // 正在下载
            case DOWNLOAD:
                int progress = (int) message.obj;
                // 设置进度条位置
                if(mPrgressBar == null){
                    Dialog dialog = DialogUtils.showVersionUpdateDialog();
                    if(dialog != null){
                        dialog.setCancelable(false);
                        mPrgressBar = (ProgressBar) dialog.findViewById(R.id.progressbar_update);
                        mTextViewProgress = (TextView) dialog.findViewById(R.id.tv_content);
                    }
                }else {
                    mPrgressBar.setProgress(progress);
                    mTextViewProgress.setText(progress+"%");
                }
                break;
            // 下载失败
            case DOWNLOAD_FAILED:
                showDownloadFailedDialog();
                break;
            // 安装文件
            case DOWNLOAD_FINISH:
                DialogUtils.dismiss();
                installApk();
                break;
            default:
                break;
        }
        return false;
    });

    /**
     * 显示软件下载失败对话框
     */
    private void showDownloadFailedDialog() {
        DialogUtils.showSingleBtnDialog("系统提示", "下载失败，请到对应商店或官网下载", "确定", view -> {
            ActivityManager.getAppManager().AppExit(BaseApplication.getInstance().getApplicationContext());
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        });
    }

    /**
     * 安装APK文件
     */
    private void installApk() {
        File apkfile = new File(mSavePath, mAppName);
        if (!apkfile.exists()) {
            return;
        }
        // 通过Intent安装APK文件
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data;
        // 判断版本大于等于7.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            data = FileProvider.getUriForFile(BaseApplication.getInstance().getApplicationContext(), "com.kanlulu.moduletest.fileprovider", apkfile);
            // 给目标应用一个临时授权
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            data = Uri.fromFile(apkfile);
        }

        intent.setDataAndType(data, "application/vnd.android.package-archive");
        activity.startActivity(intent);
        ActivityManager.getAppManager().AppExit(BaseApplication.getInstance().getApplicationContext());
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    // 证书信任管理器类
    class MyTrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }

    // 提供用于安全套接字包的类
    class MyHostnameVerifier implements HostnameVerifier {

        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }
}
