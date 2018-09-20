package com.yql.moduleMain.mine;

import com.yql.common.base.BasePresenter;
import com.yql.common.base.BaseView;

/**
 * Created by kanlulu
 * DATE: 2018/9/19 15:33
 */
public interface MineContract {
    interface View extends BaseView {

    }

    abstract class Presenter extends BasePresenter<View> {

    }
}
