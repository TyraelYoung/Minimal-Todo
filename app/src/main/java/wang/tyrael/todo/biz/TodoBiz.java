package wang.tyrael.todo.biz;

import android.content.Context;
import android.content.SharedPreferences;

import com.csmall.android.ApplicationHolder;

/**
 * Created by tyraelwang on 2017/7/3 0003.
 */

public class TodoBiz {
    public static final String SHARED_PREF_DATA_SET_CHANGED = "com.avjindersekhon.datasetchanged";
    public static final String CHANGE_OCCURED = "com.avjinder.changeoccured";

    private static Context context = ApplicationHolder.getApplication();

    public static void setDataChanged(){

    }

    public static void setDataChangeHandled(){
        //处理通知发生的数据改变
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_DATA_SET_CHANGED, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(CHANGE_OCCURED, false);
        editor.apply();
    }
}
