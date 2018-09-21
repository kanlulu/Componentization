package com.kanlulu.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * 能直接显示进度的seekbar
 * Created by lingxiaoming on 2017/8/1 0001.
 */

public class ProgressSeekBar extends RelativeLayout {
    private int space = 1;//每两个相邻刻度之间的间隔
    private String unit = "";//刻度显示 单位(元、天)

    public ProgressSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProgressSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private TextView textView;
    private SeekBar seekBar;
    private SeekBar.OnSeekBarChangeListener onSeekBarChangeListener;
    private int textViewPaddingLeft = 0;

    private void setText(String str) {
        textView.setText(str);
    }

    private void setMarginLeftForTextView(int progress) {
        if (seekBar != null && textView != null) {
            LayoutParams layoutParams = (LayoutParams) textView.getLayoutParams();
            int width = seekBar.getWidth() - seekBar.getPaddingLeft() - seekBar.getPaddingRight();
            layoutParams.leftMargin = (int) (((float) progress / seekBar.getMax()) * width);

            layoutParams.leftMargin += seekBar.getPaddingRight() - textView.getWidth() / 2 + textViewPaddingLeft;
            if(layoutParams.leftMargin + textView.getWidth() > width){
                layoutParams.leftMargin = width - textView.getWidth() + seekBar.getPaddingRight();
            }
            if(layoutParams.leftMargin < 0){
                layoutParams.leftMargin = 0;
            }

            setText(progress * space + unit);
            textView.setLayoutParams(layoutParams);
        }
    }

    public void setProgress(int process) {
        if (seekBar != null) {
            seekBar.setProgress(process/space);
            setMarginLeftForTextView(process/space);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {//防止初始化的时候，getWidth为0导致progress显示有问题
        super.onWindowFocusChanged(hasWindowFocus);
        if(hasWindowFocus) setMarginLeftForTextView(getProgress());
    }



    public int getProgress(){
        if(seekBar != null){
            return seekBar.getProgress();
        }else return 0;
    }

    public int getMax(){
        if(seekBar != null){
            return seekBar.getMax();
        }else return 0;
    }

    public void setMax(int max){
        if (seekBar != null) {
            seekBar.setMax(max/space);
        }
    }

    public void setSpace(int space){
        this.space = space;
    }

    public void setUnit(String unit){
        this.unit = unit;
    }


    public void setEnabled(boolean enabled) {
        if (seekBar != null) {
            seekBar.setEnabled(enabled);
        }
    }

    public void initSeekBar() {
        textView = (TextView) getChildAt(0);
        seekBar = (SeekBar) getChildAt(1);


        textViewPaddingLeft = ((LayoutParams) textView.getLayoutParams()).leftMargin;
        if (seekBar != null && textView != null) {
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    setMarginLeftForTextView(progress);
                    if (onSeekBarChangeListener != null) {
                        onSeekBarChangeListener.onProgressChanged(seekBar, progress, fromUser);
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    if (onSeekBarChangeListener != null) {
                        onSeekBarChangeListener.onStartTrackingTouch(seekBar);
                    }

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    if (onSeekBarChangeListener != null) {
                        onSeekBarChangeListener.onStopTrackingTouch(seekBar);
                    }
                }
            });
        }
    }

    public void setMySeekBarChangeListener(SeekBar.OnSeekBarChangeListener onSeekBarChangeListener) {
        this.onSeekBarChangeListener = onSeekBarChangeListener;
    }
}
