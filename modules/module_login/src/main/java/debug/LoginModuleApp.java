package debug;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kanlulu.common.BuildConfig;
import com.kanlulu.common.base.BaseApplication;

/**
 * Created by kanlulu
 * DATE: 2018/9/8 11:22
 */
public class LoginModuleApp extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG_ENABLE) {  // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
    }

}
