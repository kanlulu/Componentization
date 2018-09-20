package com.yql.moduleMain;

import com.yql.common.base.BasePresenter;
import com.yql.common.base.BaseView;
import com.yql.common.bean.User;
import com.yql.common.bean.Version;

/**
 * 主页面p,v集合
 */

public interface MainContract {
    interface View extends BaseView {
        void getVersionSuccess(Version version);

        void getVersionFailed(String msg);

        void getUserInfoSuccess(User user);

        void getUserInfoFailed(String msg);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getVersion();//服务端最新版本信息

        public abstract void getUserInfo();//获取用户信息，跟登录获取的用户信息一致
    }
}
