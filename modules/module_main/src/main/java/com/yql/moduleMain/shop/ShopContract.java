package com.yql.moduleMain.shop;

import com.yql.common.base.BasePresenter;
import com.yql.common.base.BaseView;

/**
 * Created by kanlulu
 * DATE: 2018/9/19 15:22
 */
public interface ShopContract {
    interface View extends BaseView {

    }

    abstract class Presenter extends BasePresenter<View> {

    }
}
