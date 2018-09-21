package com.kanlulu.moduleMain.home;

import com.kanlulu.common.base.MVPDataBindingBaseFragment;
import com.kanlulu.common.bean.Version;
import com.kanlulu.common.utils.LogUtils;
import com.kanlulu.common.utils.Rx;
import com.kanlulu.common.utils.ToastUtils;
import com.kanlulu.moduleMain.R;
import com.kanlulu.moduleMain.databinding.FragmentHomeBinding;
import com.kanlulu.module_baseevent.ARrouterPath.Navigation;

/**
 * Created by kanlulu
 * DATE: 2018/9/19 15:09
 */
public class HomeFragment extends MVPDataBindingBaseFragment<HomePresenter, FragmentHomeBinding> implements HomeContract.View {

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initFirst() {
        //点击事件监听
        bindListener();
    }

    private void bindListener() {
        Rx.click(mViewBinding.btnCredit, aVoid -> Navigation.goCreditActivity());

        Rx.click(mViewBinding.btnLogin, aVoid -> Navigation.goLoginActivity());

        Rx.click(mViewBinding.btnCheckVersion, aVoid -> mPresenter.getVersion());
    }

    @Override
    public void getVersionSuccess(Version version) {
        LogUtils.e("debug", version.app_version);
        ToastUtils.showText("最新版本:".concat(version.app_version));
    }

    @Override
    public void getVersionFailed() {

    }
}
