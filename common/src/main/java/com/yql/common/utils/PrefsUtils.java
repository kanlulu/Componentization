package com.yql.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;

import com.yql.common.base.BaseApplication;
import com.yql.common.bean.User;

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
    public final static String PREFERENCE_FILE_STRING = "wxb";
    private static final String TAG = "PrefsUtils";

    public final static String IS_FIRST = "wxb_first";//是否首次打开App
    public final static String ACCESS_TOKEN = "wxb_access_token"; //令牌
    public final static String MOBILE = "wxb_mobile"; //登录手机号
    public final static String PWD = "wxb_pwd"; //登录密码

    public final static String CUSTOMER_INFO = "wxb_customer_info"; //用户信息，可以用来判断是否登陆

    private final static String SHOW_DIALOG = "wxb_show_dialog";
    private final static String MESSAGE_TIME = "wxb_message_time";//消息时间
    private final static String IS_READ_MESSAGE = "wxb_already_read_message";//消息是否已读
    public final static String USEREVENT = "wxb_user_event"; //用户操作事件信息，获取后发送给后台

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

    public void saveStringByKey(String key, String value) {
        SharedPreferences.Editor edit = preference.edit();
        edit.putString(key, value);
        edit.commit();
    }

    public String getStringByKey(String key) {
        return preference.getString(key, "");
    }

    public void saveIntByKey(String key, int value) {
        SharedPreferences.Editor edit = preference.edit();
        edit.putInt(key, value);
        edit.commit();
    }

    public int getIntByKey(String key) {
        return preference.getInt(key, 0);
    }

    public int getIntByKeyOrDefault(String key, int defaultValue) {
        return preference.getInt(key, defaultValue);
    }

    public void saveLongByKey(String key, long value) {
        SharedPreferences.Editor edit = preference.edit();
        edit.putLong(key, value);
        edit.commit();
    }

    public long getLongByKey(String key) {
        return preference.getLong(key, 0L);
    }

    public boolean getBooleanByKey(String key) {
        return preference.getBoolean(key, true);
    }

    public void saveBooleanByKey(String key, boolean flag) {
        SharedPreferences.Editor edit = preference.edit();
        edit.putBoolean(key, flag);
        edit.commit();
    }

    /**
     * 保存用户基本信息
     *
     * @param loginUser 用户基本信息
     */
    public void saveLoginUser(User loginUser) {
        saveObject(CUSTOMER_INFO, loginUser);
    }

    /**
     * 获取用户基本信息
     *
     * @return 用户基本信息
     */
    public User getLoginUser() {
        Object obj = readObject(CUSTOMER_INFO);
        return (User) obj;
    }

    public void saveObject(String key, Object object) {
        // 创建字节输出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            // 创建对象输出流，并封装字节流
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            // 将对象写入字节流
            oos.writeObject(object);
            // 将字节流编码成base64的字符窜
            String oAuth_Base64 = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
            SharedPreferences.Editor editor = preference.edit();
            editor.putString(key, oAuth_Base64);
            editor.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LogUtils.i(TAG, "存储成功");
    }

    public Object readObject(String key) {
        Object object = null;
        String productBase64 = preference.getString(key, "");
        if (TextUtils.isEmpty(productBase64)) return null;
        //读取字节
//        byte[] base64 = Base64.decodeBase64(productBase64);
        byte[] base64 = Base64.decode(productBase64, Base64.DEFAULT);

        //封装到字节流
        ByteArrayInputStream bais = new ByteArrayInputStream(base64);
        try {
            //再次封装
            ObjectInputStream bis = new ObjectInputStream(bais);
            object = bis.readObject();
            bis.close();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return object;
    }

    public void saveListMap(String key, List<Map<String, String>> datas) {
        JSONArray mJsonArray = new JSONArray();
        for (int i = 0; i < datas.size(); i++) {
            Map<String, String> itemMap = datas.get(i);
            Iterator<Map.Entry<String, String>> iterator = itemMap.entrySet().iterator();

            JSONObject object = new JSONObject();

            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                try {
                    object.put(entry.getKey(), entry.getValue());
                } catch (JSONException e) {

                }
            }
            mJsonArray.put(object);
        }
        SharedPreferences.Editor editor = preference.edit();
        editor.putString(key, mJsonArray.toString());
        editor.commit();
    }

    public List<Map<String, String>> getListMap(String key) {
        List<Map<String, String>> datas = new ArrayList<>();
        String result = preference.getString(key, "");
        try {
            JSONArray array = new JSONArray(result);
            for (int i = 0; i < array.length(); i++) {
                JSONObject itemObject = array.getJSONObject(i);
                Map<String, String> itemMap = new HashMap<>();
                JSONArray names = itemObject.names();
                if (names != null) {
                    for (int j = 0; j < names.length(); j++) {
                        String name = names.getString(j);
                        String value = itemObject.getString(name);
                        itemMap.put(name, value);
                    }
                }
                datas.add(itemMap);
            }
        } catch (JSONException e) {

        }

        return datas;
    }

    public void setIsShowDialog(boolean isShowed) {
        preference.edit().putBoolean(SHOW_DIALOG, isShowed).apply();
    }

    public boolean getIsShowDialog() {
        return preference.getBoolean(SHOW_DIALOG, false);
    }

    public void setMessageTime(Long time) {
        preference.edit().putLong(MESSAGE_TIME, time).apply();
    }

    public Long getMessageTime() {
        return preference.getLong(MESSAGE_TIME, 0L);
    }

    public void setMessageReadStatus(boolean status) {
        preference.edit().putBoolean(IS_READ_MESSAGE, status).apply();
    }

    public boolean getMessageReadStatus() {
        return preference.getBoolean(IS_READ_MESSAGE, true);
    }
}
