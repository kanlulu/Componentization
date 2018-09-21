package com.kanlulu.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;

import com.kanlulu.common.base.BaseApplication;
import com.kanlulu.common.bean.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by kanlulu
 * DATE: 2018/9/1 20:47
 */
public class PrefsUtils {
    /**
     * 系统配置的配置文件
     */
    public final static String PREFERENCE_FILE_STRING = "moduletest";

    public final static String IS_FIRST = "moduletest_first";//是否首次打开App

    private static PrefsUtils mPrefsUtils;
    private SharedPreferences preference;

    public static PrefsUtils getInstance() {
        if (null == mPrefsUtils) {
            mPrefsUtils = new PrefsUtils(BaseApplication.getInstance());
        }
        return mPrefsUtils;
    }

    private PrefsUtils(Context context) {
        preference = context.getSharedPreferences(PREFERENCE_FILE_STRING, Context.MODE_PRIVATE);
    }


    public boolean getBooleanByKey(String key) {
        return preference.getBoolean(key, true);
    }

    public void saveBooleanByKey(String key, boolean flag) {
        SharedPreferences.Editor edit = preference.edit();
        edit.putBoolean(key, flag);
        edit.commit();
    }
}
