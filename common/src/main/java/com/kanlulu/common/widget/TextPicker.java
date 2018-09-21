package com.kanlulu.common.widget;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;


import com.kanlulu.common.R;
import com.kanlulu.common.utils.PixelUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 数组选择器
 * Created by lingxiaoming on 2017/8/11 0011.
 */

public class TextPicker extends ScrollView {
    private Context context;
    private LinearLayout views;
    private List<String> items;


    private static final int SCROLL_DIRECTION_UP = 0;
    private static final int SCROLL_DIRECTION_DOWN = 1;
    public static final int OFF_SET_DEFAULT = 1;
    int offset = OFF_SET_DEFAULT; // 偏移量（需要在最前面和最后面补全）
    private int scrollDirection = -1;
    int itemHeight = 0;
    int displayItemCount = 2 * offset + 1; // 每页显示的数量
    int initialY;
    Runnable scrollerTask;
    int selectedIndex = 1;
    int newCheck = 50;
    Paint paint;
    int viewWidth;


    public TextPicker(Context context) {
        super(context);
        init(context);
    }

    public TextPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TextPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setVerticalFadingEdgeEnabled(false);
        setVerticalScrollBarEnabled(false);
        this.context = context;
        setOverScrollMode(OVER_SCROLL_NEVER);

        views = new LinearLayout(context);
        views.setOrientation(LinearLayout.VERTICAL);
        addView(views);

        scrollerTask = new Runnable() {

            public void run() {

                int newY = getScrollY();
                if (initialY - newY == 0) { // stopped
                    final int remainder = initialY % itemHeight;
                    final int divided = initialY / itemHeight;
                    if (remainder == 0) {
                        selectedIndex = divided + offset;

                        onSelectedCallBack();
                    } else {
                        if (remainder > itemHeight / 2) {
                            post(new Runnable() {
                                @Override
                                public void run() {
                                    smoothScrollTo(0, initialY - remainder + itemHeight);
                                    selectedIndex = divided + offset + 1;
                                    onSelectedCallBack();
                                }
                            });
                        } else {
                            post(new Runnable() {
                                @Override
                                public void run() {
                                    smoothScrollTo(0, initialY - remainder);
                                    selectedIndex = divided + offset;
                                    onSelectedCallBack();
                                }
                            });
                        }


                    }


                } else {
                    initialY = getScrollY();
                    postDelayed(scrollerTask, newCheck);
                }
            }
        };
    }

    public void startScrollerTask() {
        initialY = getScrollY();
        postDelayed(scrollerTask, newCheck);
    }

    private List<String> getItems() {
        return items;
    }

    public void setItems(List<String> list) {
        if (null == items) {
            items = new ArrayList<String>();
        }
        items.clear();
        items.addAll(list);

        // 前面和后面补全
        for (int i = 0; i < offset; i++) {
            items.add(0, "");
            items.add("");
        }

        initData();

    }

    private void initData() {
        displayItemCount = offset * 2 + 1;

        for (String item : items) {
            views.addView(createView(item));
        }

        refreshItemView(0);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        refreshItemView(t);
        if (t > oldt) {
//            Log.d(TAG, "向下滚动");
            scrollDirection = SCROLL_DIRECTION_DOWN;
        } else {
//            Log.d(TAG, "向上滚动");
            scrollDirection = SCROLL_DIRECTION_UP;

        }
    }

    private TextView createView(String item) {
        TextView tv = new TextView(context);
        tv.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tv.setSingleLine(true);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tv.setText(item);
        tv.setGravity(Gravity.CENTER);
        int padding = (PixelUtils.dp2px(context, 15));
        tv.setPadding(padding, padding, padding, padding);
        if (0 == itemHeight) {
            itemHeight = getViewMeasuredHeight(tv);
            views.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, itemHeight * displayItemCount));
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) this.getLayoutParams();
            this.setLayoutParams(new LinearLayout.LayoutParams(lp.width, itemHeight * displayItemCount));
        }
        return tv;
    }

    private int getViewMeasuredHeight(View view) {
        int width = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        view.measure(width, expandSpec);
        return view.getMeasuredHeight();
    }

    private void refreshItemView(int y) {
        int position = y / itemHeight + offset;
        int remainder = y % itemHeight;
        int divided = y / itemHeight;

        if (remainder == 0) {
            position = divided + offset;
        } else {
            if (remainder > itemHeight / 2) {
                position = divided + offset + 1;
            }
        }

        int childSize = views.getChildCount();
        for (int i = 0; i < childSize; i++) {
            TextView itemView = (TextView) views.getChildAt(i);
            if (null == itemView) {
                return;
            }
            if (position == i) {
                itemView.setTextColor(getResources().getColor(R.color.gray_404b59));
                itemView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
                itemView.setAlpha(1);
                itemView.setBackgroundResource(R.drawable.selector_white);
                itemView.setClickable(true);
                itemView.setOnClickListener(view -> onClickedCallBack());
            } else {
                itemView.setTextColor(getResources().getColor(R.color.gray_404b59));
                itemView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                itemView.setAlpha(0.8f);
                itemView.setBackgroundColor(0x00000000);
                itemView.setClickable(false);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            startScrollerTask();
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public void fling(int velocityY) {
        super.fling(velocityY / 3);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = w;
        setBackgroundDrawable(null);
    }

    /**
     * 选中回调
     */
    private void onSelectedCallBack() {
        if (null != onTextChangedListener) {
            onTextChangedListener.onTextChanged(selectedIndex, items.get(selectedIndex));
        }
    }

    /**
     * 点击回调
     */
    private void onClickedCallBack() {
        if (null != onTextPickerListener) {
            onTextPickerListener.onClicked(selectedIndex, items.get(selectedIndex));
        }
    }

    public void setSeletion(int position) {
        final int p = position;
        selectedIndex = p + offset;
        this.post(new Runnable() {
            @Override
            public void run() {
                smoothScrollTo(0, p * itemHeight);
            }
        });

    }

    public String getSeletedItem() {
        return items.get(selectedIndex);
    }

    public int getSeletedIndex() {
        return selectedIndex - offset;
    }

    private OnTextPickerListener onTextPickerListener;

    private OnTextChangedListener onTextChangedListener;

    public OnTextPickerListener getOnTextPickerListener() {
        return onTextPickerListener;
    }

    public void setOnWheelViewListener(OnTextPickerListener onTextPickerListener) {
        this.onTextPickerListener = onTextPickerListener;
    }

    public void setOnWheelViewChangedListener(OnTextChangedListener onTextChangedListener) {
        this.onTextChangedListener = onTextChangedListener;
    }

    public interface OnTextPickerListener {
        //        void onSelected(int selectedIndex, String item);//滚动后显示的选项，不是点击后事件
        void onClicked(int selectedIndex, String item);//显示，然后点击了该项
    }

    public interface OnTextChangedListener {
        void onTextChanged(int selectedIndex, String item);
    }
}
