package com.kanlulu.module_login;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.kanlulu.common.base.MVPDataBindingBaseActivity;
import com.kanlulu.common.utils.Rx;
import com.kanlulu.module_baseevent.ARrouterPath.Navigation;
import com.kanlulu.module_baseevent.ARrouterPath.NavigatorPath;
import com.kanlulu.module_baseevent.BusEventBean.TestEvent;
import com.kanlulu.module_login.databinding.ActivityLoginBinding;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by kanlulu
 * DATE: 2018/9/8 11:31
 */
@Route(path = NavigatorPath.MODULE_LOGIN_ACTIVITY)
public class LoginActivity extends MVPDataBindingBaseActivity<LoginContract.Presenter, ActivityLoginBinding> implements LoginContract {

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initFirst() {
        Rx.click(mViewBinding.ivBack, aVoid -> finish());

        Rx.click(mViewBinding.btnToCredit, aVoid -> Navigation.goCreditActivity());

        Rx.click(mViewBinding.tvEvent, 1000L, aVoid -> {
            TestEvent testEvent = new TestEvent("我从Module_Login来");
            EventBus.getDefault().post(testEvent);
        });
    }

    @Override
    protected Presenter createPresenter() {
        return new LoginPresenter();
    }
}
