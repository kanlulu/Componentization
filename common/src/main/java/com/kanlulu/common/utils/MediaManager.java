package com.kanlulu.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 媒体类型工具包
 */
public class MediaManager {
    private static final String TAG = "MediaManager";

    public static final int REQUEST_PHOTO = 0;
    /**
     * 存放app部分文件的sd卡目录
     */
    private static final String SD_STORAGE_DIR_NAME = "paydayloan";
    private static final String SAVE_PHONE_NAME_TEMP = "paydayloan_camera_";


    private static Uri cameraPhotoUri;//拍照产生的照片，存放uri

    /**
     * 拍照获取
     */
    public static void getPhotoFromCamera(Activity activity) {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            ToastUtils.showText("请检查您的手机是否有SD卡");
            return;
        }
        String savePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        File savedir = new File(savePath, SD_STORAGE_DIR_NAME);
        if (!savedir.exists()) {// 不能自动创建目录
            savedir.mkdirs();
        }
        // 拍照完成后，临时存放照片的一个路径 ,拍照产生的photo是不能存放在当前apk的file等文件夹中的
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        File out = new File(savedir.getAbsolutePath(), SAVE_PHONE_NAME_TEMP + timeStamp);
        if (out.exists()) {
            out.delete();
        }
        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraPhotoUri = Uri.fromFile(out);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            cameraPhotoUri = FileProvider.getUriForFile(activity, "com.hyd.wxb.fileprovider", out);
            intentCamera.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }

        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, cameraPhotoUri);
        activity.startActivityForResult(intentCamera, REQUEST_PHOTO);
    }

    /**
     * 从手机相册获取
     */
    public static void getPhotoFromAlbum(Activity activity) {
        try {
            Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
            photoPickerIntent.setType("image/*");
            activity.startActivityForResult(photoPickerIntent, REQUEST_PHOTO);
        } catch (Exception e) {
            // 有可能没有系统相册，很难遇到
            ToastUtils.showText("没有找到系统相册");
        }
    }

    public static Uri getActivityResultImageUri(final int requestCode, int resultCode, final Intent data) {
        Uri cropUri = null;
        if (resultCode != Activity.RESULT_OK) {
            return null;
        }
        if (requestCode == REQUEST_PHOTO) {
            if (data == null || data.getData() == null) {// 拍照返回的数据
                cropUri = cameraPhotoUri;
            } else {// 相册返回的数据
                cropUri = data.getData();
                if (cropUri == null) {
                    return null;
                }
            }
        }
        return cropUri;
    }

    /**
     * 读取uri所在的图片
     *
     * @param uri      图片对应的Uri
     * @param mContext 上下文对象
     * @return 获取图像的Bitmap
     */
    public static Bitmap getBitmapFromUri(Uri uri, Context mContext) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), uri);
            Bitmap bitmap1 = resizeImage(bitmap, 720, 1080);
            LogUtils.d(TAG, "bitmap width:"+bitmap.getWidth()+" height:"+bitmap.getHeight());
            LogUtils.d(TAG, "bitmap1 width:"+bitmap1.getWidth()+" height:"+bitmap1.getHeight());
            return bitmap1;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //使用Bitmap加Matrix来缩放
    public static Bitmap resizeImage(Bitmap bitmap, int w, int h) {
        Bitmap BitmapOrg = bitmap;
        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();
        int newWidth = w;
        int newHeight = h;

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // if you want to rotate the Bitmap
        // matrix.postRotate(45);
        Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width, height, matrix, true);
        return resizedBitmap;
    }
}