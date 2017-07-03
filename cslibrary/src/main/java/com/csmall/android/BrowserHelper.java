package com.csmall.android;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.csmall.log.LogHelper;

/**
 * Created by lzs on 2017-04-11.
 */

public class BrowserHelper {

    private static final java.lang.String TAG = "BrowserHelper";

    /**
     * 调用手机浏览器打开url
     * @param url
     */
    public static void openBrowser(Activity activity, String url){
        LogHelper.d(TAG, "openBrowser:" + url);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivity(Intent.createChooser(intent, "请选择浏览器"));
        }
    }

}
