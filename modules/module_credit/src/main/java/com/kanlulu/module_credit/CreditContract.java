package com.kanlulu.module_credit;

import com.kanlulu.common.base.BasePresenter;
import com.kanlulu.common.base.BaseView;

/**
 * Created by kanlulu
 * DATE: 2018/9/18 13:30
 */
public interface CreditContract {
    interface View extends BaseView{

    }

    abstract class Presenter extends BasePresenter<View>{
        public abstract void applyCommit();
    }
}
