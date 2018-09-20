package com.yql.common.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;

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

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * 下载合同、协议，并查看
 */
public class DownloadManager {

	public DownloadManager() {
	}

	/**
	 * 下载文件
	 *
	 * @param downloadUrl
	 *            下载地址
	 * @param mFileName
	 *            文件名称(包含后缀)
	 */
	public DownloadManager(String downloadUrl, String mFileName) {
		setDownloadUrl(downloadUrl);
		setmFileName(mFileName);
	}

	public DownloadManager(String downloadUrl, String mSavePath, String mFileName) {
		setDownloadUrl(downloadUrl);
		setmSavePath(mSavePath);
		setmFileName(mFileName);
	}


	/**
	 * 下载文件
	 *
	 * @param downloadUrl
	 *            下载地址
	 * @param mFileName
	 *            文件名称(包含后缀)
	 */
	public DownloadManager(String downloadUrl, String mFileName, boolean allowDuplicate) {
		setDownloadUrl(downloadUrl);
		setmFileName(mFileName);
		setAllowDuplicate(allowDuplicate);
	}

	public DownloadManager(String downloadUrl, String mSavePath, String mFileName, boolean allowDuplicate) {
		setDownloadUrl(downloadUrl);
		setmSavePath(mSavePath);
		setmFileName(mFileName);
		setAllowDuplicate(allowDuplicate);
	}

	/**
	 * 下载保存路径
	 */
	private String mSavePath;

	public String getmSavePath() {
		return mSavePath;
	}

	public void setmSavePath(String mSavePath) {
		this.mSavePath = mSavePath;
	}

	/**
	 * 下载地址
	 */
	private String downloadUrl;
	
	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	
	/**
	 * 允许重复下载。false：不允许，true：允许
	 */
	private boolean allowDuplicate = true;

	public boolean isAllowDuplicate() {
		return allowDuplicate;
	}

	/**
	 * 允许重复下载。false：不允许，true：允许
	 * @param allowDuplicate
	 */
	public void setAllowDuplicate(boolean allowDuplicate) {
		this.allowDuplicate = allowDuplicate;
	}
	
	/**
	 * 保存的文件名称
	 */
	private String mFileName;

	public String getmFileName() {
		return mFileName;
	}

	public void setmFileName(String mFileName) {
		this.mFileName = mFileName;
	}
	
	/**
	 * 是否同步。true:同步，false:异步。默认异步
	 */
	private boolean isSynchronized = false;

	public boolean isSynchronized() {
		return isSynchronized;
	}

	/**
	 * 是否同步。true:同步，false:异步。默认异步
	 * @param isSynchronized
	 */
	public void setSynchronized(boolean isSynchronized) {
		this.isSynchronized = isSynchronized;
	}
	
	private Context mContext = null;

	public Context getmContext() {
		return mContext;
	}

	public void setmContext(Context mContext) {
		this.mContext = mContext;
	}
	
	/**
	 * 是否立即打开。默认false
	 */
	private boolean needOpen = false;

	public boolean isNeedOpen() {
		return needOpen;
	}

	/**
	 * 是否立即打开。默认false
	 * @param needOpen
	 */
	public void setNeedOpen(boolean needOpen) {
		this.needOpen = needOpen;
	}

	/**
	 * 下载apk文件
	 */
	public void downloadFile() {		
		if (isSynchronized) {
			exeDownloadFile();
		} else {
			// 启动新线程下载软件
			new downloadThread().start();
		}
	}

	private DownloadListener downloadListener;
	public void setDisplayListener(DownloadListener downloadListener) {
		this.downloadListener = downloadListener;
	}

	public interface DownloadListener{
		void downloadSuccess(Uri fileUri);
	}

	/**
	 * 下载文件线程
	 *
	 */
	private class downloadThread extends Thread {

		public downloadThread() {

		}

		@Override
		public void run() {
			exeDownloadFile();
		}
	}
	
	private void exeDownloadFile() {
		String savePath = "";
		try {
			// 判断SD卡是否存在，并且是否具有读写权限
			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
				// 获得存储卡的路径
				String sdpath = Environment.getExternalStorageDirectory() + "/";
				savePath = sdpath;
				if (TextUtils.isEmpty(mSavePath)) {
					savePath += "download";
				} else {
					savePath += mSavePath;
				}
				boolean needDownLoad = true;
				if (!allowDuplicate) {
					File f = new File(savePath + "/" + mFileName);
					if (f.exists()) {
						needDownLoad = false;							
					}
				}
				if (needDownLoad) {
					URL url = new URL(downloadUrl);

					HttpURLConnection conn;
					// 判断是http请求还是https请求
					if (url.getProtocol().toLowerCase().equals("https")) {
						final SSLContext sc = SSLContext.getInstance("TLS");
						sc.init(null, new TrustManager[] { new MyTrustManager() }, new SecureRandom());
						HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
						HttpsURLConnection.setDefaultHostnameVerifier(new MyHostnameVerifier());
						conn = (HttpsURLConnection) url.openConnection();
					} else {
						conn = (HttpURLConnection) url.openConnection();
					}

					conn.connect();

					// 创建输入流
					InputStream is = conn.getInputStream();

					File file = new File(savePath);
					// 判断文件目录是否存在
					if (!file.exists()) {
						file.mkdirs();
					}

					File mFile = new File(savePath, mFileName);
					if(!mFile.exists()){//文件是否存在
						mFile.createNewFile();
					}
					FileOutputStream fos = new FileOutputStream(mFile);

					// 写入到文件中
					byte[] buffer = new byte[1024];
					int len = 0;
					while ((len = is.read(buffer)) > 0) {
						fos.write(buffer, 0, len);
					}
					fos.close();
				}
				
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}
		
		if (needOpen) {
			String url = savePath + "/" + mFileName;
			openLocalPdf(mContext, url);
		}
		
	}
	
	
	/**
	 * 打开本地PDF文件
	 * @param mContext
	 * @param url
	 */
	private void openLocalPdf(Context mContext, String url) {
		if (mContext == null || TextUtils.isEmpty(url)) {
			return;
		}
		
		File file = new File(url);
		if (file.exists()) {
//			Intent target = new Intent(Intent.ACTION_VIEW);
//			target.setDataAndType(Uri.fromFile(file), "application/pdf");
//			Intent intent = Intent.createChooser(target, "打开合同");
//			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			mContext.startActivity(intent);
			Uri uri = Uri.fromFile(file);
			if(downloadListener != null){
				downloadListener.downloadSuccess(uri);
			}
		}
	}

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

	class MyHostnameVerifier implements HostnameVerifier {

		@Override
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}

}
