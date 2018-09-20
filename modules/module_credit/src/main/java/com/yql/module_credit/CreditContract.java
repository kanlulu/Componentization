package com.yql.module_credit;

import com.yql.common.base.BasePresenter;
import com.yql.common.base.BaseView;

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
