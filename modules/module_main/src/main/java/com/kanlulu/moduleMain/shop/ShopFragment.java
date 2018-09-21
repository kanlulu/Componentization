package com.kanlulu.moduleMain.shop;

import com.kanlulu.common.base.MVPDataBindingBaseFragment;
import com.kanlulu.moduleMain.R;
import com.kanlulu.moduleMain.databinding.FragmentShopBinding;

/**
 * Created by kanlulu
 * DATE: 2018/9/19 15:30
 */
public class ShopFragment extends MVPDataBindingBaseFragment<ShopPresenter, FragmentShopBinding> implements ShopContract.View {
    @Override
    protected ShopPresenter createPresenter() {
        return new ShopPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_shop;
    }

    @Override
    public void initFirst() {

    }
}
