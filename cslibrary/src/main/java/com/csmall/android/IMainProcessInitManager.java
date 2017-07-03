package com.csmall.android;

import android.app.Application;

/**
 * Created by wangchao on 2017/4/1.
 */

public interface IMainProcessInitManager {
    void onAppCreate(Application app);
    void onMainActivityCreate();
    void onIdle();
}
