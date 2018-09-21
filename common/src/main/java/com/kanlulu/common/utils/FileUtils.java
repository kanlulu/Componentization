package com.kanlulu.common.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 文件相关工具类
 * Created by lingxiaoming on 2017/9/12 0012.
 */

public class FileUtils {
    public static void WriteStringToFile(String filePath, byte[] content) {
        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            fos.write(content);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeImageToFile(byte[] imgData) {
        Bitmap idcardBmp = BitmapFactory.decodeByteArray(imgData, 0, imgData.length);
        File file = new File(Environment.getExternalStorageDirectory(), "idcard_test.png");
        try {
            file.createNewFile();
            FileUtils.WriteStringToFile(file.getPath(), imgData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
