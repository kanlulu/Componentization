package com.yql.common.utils;

import android.content.Context;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yql.common.R;
import com.yql.common.base.BaseApplication;

/**
 * Created by kanlulu
 * DATE: 2018/9/1 21:44
 */
public class ToastUtils {
    private static Context context;
    private static Toast toast;

    public static void cancel() {
        if (toast != null) {
            toast.cancel();
        }
    }

    /**
     * 文字toast
     */
    public static void showText(String s) {
        try {
            showTextWithIcon(s, 0);
        }catch (Exception e){
            LogUtils.e("debug",e.getMessage());
        }
    }

    /**
     * 图标+文字toast
     *
     * @param msg
     */
    public static void showTextWithIcon(String msg, int iconId) {
        if (msg == null) {
            return;
        }

        cancel();

        if (context == null) {
            context = BaseApplication.getInstance().getApplicationContext();
        }
        View layout = LayoutInflater.from(context).inflate(R.layout.toast_black, null);
        if(iconId > 0){
            ImageView imageView = (ImageView)layout.findViewById(R.id.iv_icon);
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageResource(iconId);
        }else {
//            layout.findViewById(R.id.iv_icon).setVisibility(View.GONE);
        }
        TextView tv_content = (TextView) layout.findViewById(R.id.tv_content);
        tv_content.setText(Html.fromHtml(msg.toString()));

        toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    public static void showTextWithIcon(String msg, int iconId,int gravity,int offset) {
        if (msg == null) {
            return;
        }

        cancel();

        if (context == null) {
            context = BaseApplication.getInstance().getApplicationContext();
        }
        View layout = LayoutInflater.from(context).inflate(R.layout.toast_black, null);
        if(iconId > 0){
            ImageView imageView = (ImageView)layout.findViewById(R.id.iv_icon);
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageResource(iconId);
        }else {
//            layout.findViewById(R.id.iv_icon).setVisibility(View.GONE);
        }
        TextView tv_content = (TextView) layout.findViewById(R.id.tv_content);
        tv_content.setText(Html.fromHtml(msg.toString()));

        toast = new Toast(context);
        toast.setGravity(gravity, 0, offset);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    /**
     * 正方形黑块提示
     *
     * @param msg
     *
     */
    public static void showSquare(String msg) {
        if (msg == null) {
            return;
        }

        cancel();
        if (context == null) {
            context = BaseApplication.getInstance().getApplicationContext();
        }
        View layout = LayoutInflater.from(context).inflate(R.layout.toast_square, null);
        TextView tv_content = (TextView) layout.findViewById(R.id.tv_content);
        tv_content.setText(Html.fromHtml(msg.toString()));

        toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}
