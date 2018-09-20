package com.yql.moduleMain.home;

import com.yql.common.base.MVPDataBindingBaseFragment;
import com.yql.common.bean.Version;
import com.yql.common.utils.LogUtils;
import com.yql.common.utils.Rx;
import com.yql.common.utils.ToastUtils;
import com.yql.moduleMain.R;
import com.yql.moduleMain.databinding.FragmentHomeBinding;
import com.yql.module_baseevent.ARrouterPath.Navigation;

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
