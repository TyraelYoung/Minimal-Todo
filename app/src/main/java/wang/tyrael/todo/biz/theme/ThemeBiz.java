package wang.tyrael.todo.biz.theme;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.csmall.android.ApplicationHolder;

import wang.tyrael.todo.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2017/7/2 0002.
 */

public class ThemeBiz {
    public static final String THEME_PREFERENCES = "com.avjindersekhon.themepref";

    //keys
    public static final String THEME_SAVED = "com.avjindersekhon.savedtheme";
    public static final String RECREATE_ACTIVITY = "com.avjindersekhon.recreateactivity";

    //values
    public static final String DARKTHEME = "com.avjindersekon.darktheme";
    public static final String LIGHTTHEME = "com.avjindersekon.lighttheme";

    private static Context context = ApplicationHolder.getApplication();

//    public static boolean changeHandled = false;

    public static boolean isChangeHandled(){
        return !context.getSharedPreferences(THEME_PREFERENCES, MODE_PRIVATE).getBoolean(RECREATE_ACTIVITY, false);
    }

    public static void setChangeHandled(){
        SharedPreferences.Editor editor = context.getSharedPreferences(THEME_PREFERENCES, MODE_PRIVATE).edit();
        editor.putBoolean(RECREATE_ACTIVITY, false);
        editor.apply();
    }

    public static void setTheme(String themeId){
        SharedPreferences.Editor editor = context.getSharedPreferences(THEME_PREFERENCES, MODE_PRIVATE).edit();
        editor.putString(THEME_SAVED, themeId);
        editor.apply();
    }

    public static int getStyle(){
        return R.style.CustomStyle_DarkTheme;
//        String theme = context.getSharedPreferences(THEME_PREFERENCES, MODE_PRIVATE).getString(THEME_SAVED, LIGHTTHEME);
//        int mTheme = 0;
//        if(theme.equals(LIGHTTHEME)){
//            mTheme = R.style.CustomStyle_LightTheme;
//        }
//        else{
//            mTheme = R.style.CustomStyle_DarkTheme;
//        }
//        return mTheme;
    }

    public static String getThemeId(){
        return DARKTHEME;
//        return context.getSharedPreferences(THEME_PREFERENCES, MODE_PRIVATE).getString(THEME_SAVED, LIGHTTHEME);
    }
}
