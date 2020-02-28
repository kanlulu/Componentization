package com.kanlulu.moduleMain;

import android.app.Dialog;
import android.view.*;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.kanlulu.common.base.MainMVPBaseActivity;
import com.kanlulu.common.bean.User;
import com.kanlulu.common.bean.Version;
import com.kanlulu.common.network.ApiConstants;
import com.kanlulu.common.utils.LogUtils;
import com.kanlulu.common.widget.FragmentTabHost;
import com.kanlulu.moduleMain.databinding.ActivityMainBinding;
import com.kanlulu.moduleMain.home.HomeFragment;
import com.kanlulu.moduleMain.mine.MineFragment;
import com.kanlulu.moduleMain.shop.ShopFragment;
import com.kanlulu.module_baseevent.ARrouterPath.NavigatorPath;
import com.kanlulu.module_baseevent.BusEventBean.Test2Event;
import com.kanlulu.module_baseevent.BusEventBean.TestEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

@Route(path = NavigatorPath.MODULE_MAIN_ACTIVITY)
public class MainActivity extends MainMVPBaseActivity<MainContract.Presenter, ActivityMainBinding> implements MainContract.View {
    private FragmentTabHost fragmentTabHost;
    private String[] titles = {"首页", "超市", "我的"};
    private Class[] fragments = {HomeFragment.class, ShopFragment.class, MineFragment.class};
    private int[] tabsDrawables = {R.drawable.rb_money, R.drawable.rb_daochao, R.drawable.rb_huankuan};
    private String[] noShopTitles = {"首页", "我的"};
    private Class[] noShopFragments = {HomeFragment.class, MineFragment.class};
    private int[] noShopTabsDrawables = {R.drawable.rb_money, R.drawable.rb_huankuan};

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected MainContract.Presenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    public void initFirst() {
        setupTabUI();
        bindListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        showFullScreenDialog();
    }

    private void showFullScreenDialog() {
        Dialog dialog = new Dialog(MainActivity.this, R.style.popupDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fragment_home);
        dialog.setCancelable(true);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setGravity(Gravity.CENTER);
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width = ViewGroup.LayoutParams.MATCH_PARENT;
            attributes.height = ViewGroup.LayoutParams.MATCH_PARENT;
            window.setAttributes(attributes);
        }
        dialog.show();
    }

    private void bindListener() {
//        mViewBinding.tvEvent.setOnClickListener(v -> EventBus.getDefault().postSticky(new Test2Event("我是粘性EventBus...")));
        EventBus.getDefault().register(this);
    }

    private void setupTabUI() {
        fragmentTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        fragmentTabHost.setup(this, getSupportFragmentManager(), R.id.container);
        if (ApiConstants.NO_SHOP) {
            for (int i = 0; i < noShopTitles.length; i++) {
                TabHost.TabSpec spec = fragmentTabHost.newTabSpec(i + "").setIndicator(getView(i));
                fragmentTabHost.addTab(spec, noShopFragments[i], null);
            }
        } else {
            for (int i = 0; i < titles.length; i++) {
                TabHost.TabSpec spec = fragmentTabHost.newTabSpec(i + "").setIndicator(getView(i));
                fragmentTabHost.addTab(spec, fragments[i], null);
            }
        }

        fragmentTabHost.setOnTabChangedListener(tabId -> {

        });
        fragmentTabHost.setCurrentTab(0);
    }

    private View getView(int i) {
        //取得布局实例
        View view = View.inflate(this, R.layout.tab_item, null);

        //取得布局对象
        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
        TextView textView = (TextView) view.findViewById(R.id.textview);
        if (ApiConstants.NO_SHOP) {
//            if (i == 1) {
//                mineMessageDot = view.findViewById(R.id.mine_tab_dot);
//            }
            //设置图标
            imageView.setImageResource(noShopTabsDrawables[i]);
        } else {
//            if (i == 2) {
//                mineMessageDot = view.findViewById(R.id.mine_tab_dot);
//            }
            //设置图标
            imageView.setImageResource(tabsDrawables[i]);
        }
        //设置标题
        textView.setText(titles[i]);
        return view;
    }

    public void choiceTab(int tabIndex) {
        if (fragmentTabHost != null) {
            fragmentTabHost.setCurrentTab(tabIndex);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void testEvent(TestEvent testEvent) {
//        mViewBinding.tvEvent.setText(testEvent.getResult());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void test2Event(Test2Event test2Event) {
//        mViewBinding.tvEvent.setText(test2Event.getResult());
    }

    @Override
    public void getVersionSuccess(Version version) {
        LogUtils.e("debug", version.app_code + "");
    }

    @Override
    public void getVersionFailed(String msg) {

    }

    @Override
    public void getUserInfoSuccess(User user) {

    }

    @Override
    public void getUserInfoFailed(String msg) {

    }
}
