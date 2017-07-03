package com.csmall.android;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;

import com.csmall.log.LogHelper;

/**
 * Created by wangchao on 2016/6/6.
 */
public class ApplicationHolder {
    private static final String TAG = "ApplicationHolder";
    @SuppressLint("StaticFieldLeak")
    private static Application sApplication;

    public static Application getApplication(){
        if(sApplication == null){
            Log.e(TAG, "sApplication == null");
        }
        return sApplication;
    }

    public static void setApplication(Application application) {
        sApplication = application;
    }
}
