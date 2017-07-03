package com.csmall.android;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by wangchao on 2017/4/21.
 */

public class StartActiviyHelper {
    /**
     * 跳转到另一个app
     * @param activity
     * @param packageName
     */
    public static void openOtherApp(Activity activity, String packageName){
        Intent intent = activity.getPackageManager().getLaunchIntentForPackage(packageName);
        if (intent != null){
            activity.startActivity(intent);
        }else {
            ToastUtil.show("手机尚未安装该应用，无法打开");
        }
    }
}
