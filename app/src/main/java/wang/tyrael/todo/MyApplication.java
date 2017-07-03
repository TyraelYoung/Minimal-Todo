package wang.tyrael.todo;

import android.app.Application;

/**
 * Created by wangchao on 2017/3/29.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MainProcessInitManager.inst.onAppCreate(this);
    }
}
