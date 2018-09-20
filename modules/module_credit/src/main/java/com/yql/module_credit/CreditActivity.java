package com.yql.module_credit;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yql.common.base.MVPDataBindingBaseActivity;
import com.yql.common.utils.Rx;
import com.yql.common.utils.ToastUtils;
import com.yql.module_baseevent.ARrouterPath.Navigation;
import com.yql.module_baseevent.ARrouterPath.NavigatorPath;
import com.yql.module_baseevent.BusEventBean.Test2Event;
import com.yql.module_baseevent.BusEventBean.TestEvent;
import com.yql.module_credit.databinding.ActivityMainCreditBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

@Route(path = NavigatorPath.MODULE_CREDIT_ACTIVITY)
public class CreditActivity extends MVPDataBindingBaseActivity<CreditContract.Presenter, ActivityMainCreditBinding> implements CreditContract.View {

    @Override
    public int getLayoutId() {
        return R.layout.activity_main_credit;
    }

    @Override
    public void initFirst() {
        bindListener();
    }

    private void bindListener() {
        Rx.click(mViewBinding.ivBack, aVoid -> finish());

        Rx.click(mViewBinding.btnToMain,aVoid -> Navigation.goMainActivity());

        Rx.click(mViewBinding.tvEvent, aVoid -> {
            TestEvent testEvent = new TestEvent("我从Module_Credit来");
            EventBus.getDefault().post(testEvent);
        });

        EventBus.getDefault().register(this);
    }

    @Override
    protected CreditContract.Presenter createPresenter() {
        return new CreditPresenter();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(Test2Event test2Event) {
        ToastUtils.showSquare(test2Event.getResult());
        //移除粘性事件
        EventBus.getDefault().removeStickyEvent(Test2Event.class);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
