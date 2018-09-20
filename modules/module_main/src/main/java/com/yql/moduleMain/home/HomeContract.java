package com.yql.moduleMain.home;

import com.yql.common.base.BasePresenter;
import com.yql.common.base.BaseView;
import com.yql.common.bean.Version;

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
