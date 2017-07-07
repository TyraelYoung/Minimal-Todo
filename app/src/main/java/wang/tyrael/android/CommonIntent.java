package wang.tyrael.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.AlarmClock;

import com.csmall.android.ApplicationHolder;
import com.csmall.android.ToastUtil;

/**
 * Created by tyraelwang on 2017/7/7 0007.
 */

public class CommonIntent {
    public static void activityAlarm(Activity from, String msg) {
        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
        intent.putExtra(AlarmClock.EXTRA_MESSAGE, msg);
        intent.putExtra(AlarmClock.EXTRA_SKIP_UI, false);
        if (intent.resolveActivity(from.getPackageManager()) != null) {
            from.startActivity(intent);
        } else {
            ToastUtil.show("您的手机上没有安装闹钟应用");
        }
    }
}
