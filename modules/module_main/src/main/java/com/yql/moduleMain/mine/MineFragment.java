package com.yql.moduleMain.mine;

import com.yql.common.base.MVPDataBindingBaseFragment;
import com.yql.moduleMain.R;
import com.yql.moduleMain.databinding.FragmentMineBinding;

/**
 * Created by kanlulu
 * DATE: 2018/9/19 15:36
 */
public class MineFragment extends MVPDataBindingBaseFragment<MinePresenter,FragmentMineBinding> implements MineContract.View{

    @Override
    protected MinePresenter createPresenter() {
        return new MinePresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initFirst() {

    }
}
