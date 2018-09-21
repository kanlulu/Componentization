package com.kanlulu.moduleMain;

import com.kanlulu.common.base.BasePresenter;
import com.kanlulu.common.base.BaseView;
import com.kanlulu.common.bean.User;
import com.kanlulu.common.bean.Version;

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
