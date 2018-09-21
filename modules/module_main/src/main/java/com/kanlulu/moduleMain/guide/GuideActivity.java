package com.kanlulu.moduleMain.guide;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;


import com.alibaba.android.arouter.facade.annotation.Route;
import com.kanlulu.common.base.DataBindingBaseActivity;
import com.kanlulu.common.utils.PrefsUtils;
import com.kanlulu.moduleMain.R;
import com.kanlulu.moduleMain.databinding.ActivityGuideBinding;
import com.kanlulu.module_baseevent.ARrouterPath.Navigation;
import com.kanlulu.module_baseevent.ARrouterPath.NavigatorPath;

import java.util.ArrayList;
import java.util.List;

/**
 */
@Route(path = NavigatorPath.MODULE_MAIN_GUIDE)
public class GuideActivity extends DataBindingBaseActivity<ActivityGuideBinding> implements ViewPager.OnPageChangeListener {

    private List<View> views;
    private ViewPagerAdapter vpAdapter;
    private LayoutInflater inflater;
    private ImageView image1, image2, image3;
    private Button btnGoin;

    @Override
    public int getLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    public void initFirst() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        inflater = LayoutInflater.from(this);
        initViews();
        initPageNum();
    }

    private void initPageNum() {
        mViewBinding.pageNum.setText("");
    }

    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(0, 0);
    }

    private void initViews() {
        //引导页 数据集合
        views = new ArrayList<>();
        View view1 = inflater.inflate(R.layout.views_guide_item, null);
        image1 = (ImageView) view1.findViewById(R.id.iv_guide);
        image1.setImageResource(R.mipmap.guide02);

        View view2 = inflater.inflate(R.layout.views_guide_item, null);
        image2 = (ImageView) view2.findViewById(R.id.iv_guide);
        image2.setImageResource(R.mipmap.guide02);

        View view3 = inflater.inflate(R.layout.views_guide_item, null);
        image3 = (ImageView) view3.findViewById(R.id.iv_guide);
        image3.setImageResource(R.mipmap.guide03);

        btnGoin = (Button) view3.findViewById(R.id.btn_gonow);
        btnGoin.setVisibility(View.VISIBLE);

        views.add(view1);
        views.add(view2);
        views.add(view3);

        vpAdapter = new ViewPagerAdapter(views);

        mViewBinding.viewpager.setAdapter(vpAdapter);
        mViewBinding.viewpager.setOnPageChangeListener(this);
    }

    public class ViewPagerAdapter extends PagerAdapter {

        private List<View> views;

        public ViewPagerAdapter(List<View> views) {
            this.views = views;
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView(views.get(arg1));
        }

        @Override
        public int getCount() {
            return views == null ? 0 : views.size();
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {
            ((ViewPager) arg0).addView(views.get(arg1), 0);
            if (arg1 == views.size() - 1) {
                Button mStart = (Button) arg0.findViewById(R.id.btn_gonow);

                mStart.setOnClickListener(v -> goHome());
            }
            return views.get(arg1);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return (arg0 == arg1);
        }

        private void goHome() {
            Navigation.goMainActivity();
            PrefsUtils.getInstance().saveBooleanByKey(PrefsUtils.IS_FIRST, false);
            overridePendingTransition(android.R.anim.fade_in,
                    android.R.anim.fade_out);
            finish();
        }
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int arg0) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
