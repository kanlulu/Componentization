package com.yql.common.utils;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.AnimationDrawable;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yql.common.R;
import com.yql.common.tools.ActivityManager;
import com.yql.common.widget.TextPicker;

import java.util.List;

/**
 * 弹框样式集合
 * Created by lingxiaoming on 2017/8/8 0008.
 *
 */

public class DialogUtils {
    private static final String TAG = "DialogUtils";
    private static Dialog dialog;


    public static void showSingleBtnDialog(String title, String content, String btnString, View.OnClickListener onClickListener) {
        Activity activity = getActivity();
        showSingleBtnDialogInActivity(activity, title, content, btnString, onClickListener);
    }

    /**
     * 一个按钮的弹框
     */
    public static void showSingleBtnDialogInActivity(Activity activity, String title, String content, String btnString, View.OnClickListener onClickListener) {
        if (activity == null || activity.isFinishing()) return;
        dismiss();


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
            tv_cancel.setOnClickListener(cancelClick);
            tv_cancel.setTextColor(activity.getResources().getColor(R.color.gray_404b59));
        } else {
            tv_cancel.setOnClickListener(new MyClickListener(onClickListener));
            tv_cancel.setTextColor(activity.getResources().getColor(R.color.red_f9542d));

        }

        dialog.show();
    }

    /**
     * 两个按钮的弹框
     */
    public static void showTwoBtnDialog(String title, String content, String btnLeftString, View.OnClickListener btnLeftOnClickListener, String btnRightString, View.OnClickListener btnRightOnClickListener) {
        Activity activity = getActivity();
        if (activity == null || activity.isFinishing()) return;
        dismiss();


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
            tv_left.setOnClickListener(cancelClick);
        } else {
            tv_left.setOnClickListener(new MyClickListener(btnLeftOnClickListener));
        }

        tv_right.setText(btnRightString);
        if (btnRightOnClickListener == null) {
            tv_right.setOnClickListener(cancelClick);
        } else {
            tv_right.setOnClickListener(new MyClickListener(btnRightOnClickListener));
        }

        dialog.show();
    }

    /**
     * 有选项功能的弹框
     */
    public static void showOptionsDialog(String option1, View.OnClickListener onClickListener1, String option2, View.OnClickListener onClickListener2) {
        Activity activity = getActivity();
        if (activity == null || activity.isFinishing()) return;
        dismiss();


        dialog = new Dialog(activity, R.style.MyDialogStyle);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_options);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView tv_option1 = (TextView) dialog.findViewById(R.id.tv_option1);
        TextView tv_option2 = (TextView) dialog.findViewById(R.id.tv_option2);
        TextView cancel = (TextView) dialog.findViewById(R.id.tv_option3);

        if (TextUtils.isEmpty(option1)) {
            tv_option1.setVisibility(View.GONE);
        } else {
            tv_option1.setVisibility(View.VISIBLE);
            tv_option1.setText(option1);
            tv_option1.setOnClickListener(new MyClickListener(onClickListener1));
        }

        if (TextUtils.isEmpty(option2)) {
            tv_option2.setVisibility(View.GONE);
        } else {
            tv_option2.setVisibility(View.VISIBLE);
            tv_option2.setText(option2);
            tv_option2.setOnClickListener(new MyClickListener(onClickListener2));
        }

        cancel.setOnClickListener((view) -> dismiss());
        dialog.show();
    }

    /**
     * 有选项功能的弹框
     */
    public static void showSingleOptionsDialog(String option1, View.OnClickListener onClickListener1) {
        Activity activity = getActivity();
        if (activity == null || activity.isFinishing()) return;
        dismiss();

        dialog = new Dialog(activity, R.style.MyDialogStyle);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_single_options);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView tv_option1 = (TextView) dialog.findViewById(R.id.tv_option1);
        TextView cancel = (TextView) dialog.findViewById(R.id.tv_option3);

        if (TextUtils.isEmpty(option1)) {
            tv_option1.setVisibility(View.GONE);
        } else {
            tv_option1.setVisibility(View.VISIBLE);
            tv_option1.setText(option1);
            tv_option1.setOnClickListener(new MyClickListener(onClickListener1));
        }
        cancel.setOnClickListener((view) -> dismiss());
        dialog.show();
    }

    /**
     * 底部出来有选项的弹框
     */
    public static void showBottomBtnDialog(List arrayList, String content, TextPicker.OnTextPickerListener onTextPickerListener, TextPicker.OnTextChangedListener onTextChangedListener) {
        Activity activity = getActivity();
        if (activity == null || activity.isFinishing()) return;
        dismiss();

        if (arrayList == null) return;


        dialog = new Dialog(activity, R.style.MyDialogStyle);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.dialog_bottom_button);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置
        window.setWindowAnimations(R.style.bottom_dialog_anim);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        TextPicker tp_text = (TextPicker) dialog.findViewById(R.id.textPicker);
        TextView tv_cancel = (TextView) dialog.findViewById(R.id.tv_cancel);


        tp_text.setItems(arrayList);
        tp_text.setSeletion(arrayList.size() - 1);

        if (!TextUtils.isEmpty(content)) {
            tv_cancel.setText(content);
        }
        tv_cancel.setOnClickListener((view) -> dismiss());

        tp_text.setOnWheelViewListener(onTextPickerListener);
        tp_text.setOnWheelViewChangedListener(onTextChangedListener);
        dialog.show();
    }

    public static void showProgressDialog(String content) {
        Activity activity = getActivity();
        showProgressDialog(activity, content);
    }

    public static void showProgressDialog(Activity activity, String content) {
        if (activity == null || activity.isFinishing()) return;
        dismiss();
        LogUtils.d(TAG, "showProgressDialog:" + content + " activity:" + activity.getClass().getSimpleName());

        dialog = new Dialog(activity, R.style.MyDialogStyle);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_progressbar);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView tv_content = (TextView) dialog.findViewById(R.id.tv_content);


        if (TextUtils.isEmpty(content)) {
            tv_content.setVisibility(View.GONE);
        } else {
            tv_content.setVisibility(View.VISIBLE);
            tv_content.setText(content);
        }

        ImageView imageView = (ImageView) dialog.findViewById(R.id.iv_progress);
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getDrawable();
        animationDrawable.start();

        dialog.show();
    }

    public static void showCommmitContactDialog(Activity activity, String content) {
        if (activity == null || activity.isFinishing()) return;
        dismiss();
        LogUtils.d(TAG, "showProgressDialog:" + content + " activity:" + activity.getClass().getSimpleName());

        dialog = new Dialog(activity, R.style.MyDialogStyle);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_progressbar);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView tv_content = (TextView) dialog.findViewById(R.id.tv_content);


        if (TextUtils.isEmpty(content)) {
            tv_content.setVisibility(View.GONE);
        } else {
            tv_content.setVisibility(View.VISIBLE);
            tv_content.setText(content);
        }

        ImageView imageView = (ImageView) dialog.findViewById(R.id.iv_progress);
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getDrawable();
        animationDrawable.start();

        dialog.show();
    }

    public static Dialog showVersionUpdateDialog() {
        Activity activity = getActivity();
        if (activity == null || activity.isFinishing()) return null;
        dismiss();

        dialog = new Dialog(activity, R.style.MyDialogStyle);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_update);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        dialog.show();
        return dialog;
    }


    private static Activity getActivity() {
        return ActivityManager.getAppManager().currentActivity();
    }

    public static void dismiss() {
        LogUtils.d(TAG, "dismiss");
        if (dialog != null && dialog.isShowing()) {
            try {
                dialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static Dialog getCurrentDialog() {
        return dialog;
    }

    public static boolean isShowing() {
        if (dialog != null) {
            return dialog.isShowing();
        } else {
            return false;
        }
    }

    private static View.OnClickListener cancelClick = (view) -> dismiss();

    private static class MyClickListener implements View.OnClickListener {

        View.OnClickListener outSideClickListener;

        public MyClickListener(View.OnClickListener outSideClickListener) {
            this.outSideClickListener = outSideClickListener;
        }


        @Override
        public void onClick(View view) {
            dismiss();
            outSideClickListener.onClick(view);
        }
    }
}
