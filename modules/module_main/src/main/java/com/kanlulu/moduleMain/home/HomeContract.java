package com.kanlulu.moduleMain.home;

import com.kanlulu.common.base.BasePresenter;
import com.kanlulu.common.base.BaseView;
import com.kanlulu.common.bean.Version;

/**
 * Created by kanlulu
 * DATE: 2018/9/19 15:07
 */
public interface HomeContract {
    interface View extends BaseView {
        void getVersionSuccess(Version version);

        void getVersionFailed();
    }

    abstract class Presenter extends BasePresenter<View> {
        abstract void getVersion();
    }
}
