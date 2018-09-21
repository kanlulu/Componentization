package com.kanlulu.moduleMain.mine;

import com.kanlulu.common.base.BasePresenter;
import com.kanlulu.common.base.BaseView;

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
