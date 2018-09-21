package com.kanlulu.module_login;

import com.kanlulu.common.base.BasePresenter;
import com.kanlulu.common.base.BaseView;
import com.kanlulu.common.bean.User;

/**
 * Created by kanlulu
 * DATE: 2018/9/18 13:15
 */
public interface LoginContract {
    interface View extends BaseView {
        void loginSuccess(User user);

        void loginFailed(String msg);

        void getCodeSuccess();

        void getCodeFailure(String msg);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void login(String name, String verify_code);

        public abstract void getCode(String mobile, int smsTemplateType);

        public abstract void bindPushRegId(String registration_id);
    }
}
