package com.yql.common.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * @author Lucius
 * @version V1.0
 * @Title 中文名字输入监听
 * @Package com.hyd.wxb.tools
 * @Description
 * @date 2017/10/20 14:45
 */
public class ChineseNameUtil {
    public static class ChineseInputListener implements TextWatcher {
        private EditText editText;

        public ChineseInputListener(EditText editText) {
            this.editText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {//每4个数字分隔开
            if (s == null || s.length() == 0) return;
            StringBuilder sb = new StringBuilder();
            try {
                for (int i = 0; i < s.length(); i++) {
                    if(isChinese(s.charAt(i))){
                        sb.append(s.charAt(i));
                    }
                }

                if (!sb.toString().equals(s.toString())) {
                    editText.setText(sb.toString());
                    editText.setSelection(sb.length());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }

    private static final boolean isChinese(char c) {
        if((c >= 0x4e00)&&(c <= 0x9fbb)) {
            return true;
        }
        return false;
    }
}
