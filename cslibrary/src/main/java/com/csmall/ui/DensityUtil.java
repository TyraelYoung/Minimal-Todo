package com.csmall.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.csmall.android.ApplicationHolder;

public class DensityUtil {
	  /** 
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
     */  
    public static int px2dip(Context context, float pxValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    }  
    
	public static int dip2px(Context context, float dpValue) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	public static int dip2px(float dpValue) {
		Context context = ApplicationHolder.getApplication();
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	///////////////////////屏幕宽度
	public static int getScreenWidth(){
		return DensityUtil.getScreenSize(ApplicationHolder.getApplication())[0];
	}

	public static int getScreenWidth(Context context){
		return DensityUtil.getScreenSize(context)[0];
	}

	public static int getScreenHeight(Context context){
		return DensityUtil.getScreenSize(context)[1];
	}

	public static int getScreenHeight(){
		return DensityUtil.getScreenSize(ApplicationHolder.getApplication())[1];
	}


	public static int[] getScreenSize(Context context) {
		int[] screenSize = new int[2];
		int measuredWidth;
		int measuredheight;
		Point size = new Point();
		WindowManager w = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

		w.getDefaultDisplay().getSize(size);
		measuredWidth = size.x;
		measuredheight = size.y;
		screenSize[0] = measuredWidth;
		screenSize[1] = measuredheight;

		return screenSize;
	}
	
	
	public static int getDensityDpi(Activity activity){
		DisplayMetrics metric = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
//		width = metric.widthPixels; // 屏幕宽度（像素）
//		height = metric.heightPixels; // 屏幕高度（像素）
//		density = (int) metric.density; // 屏幕密度（0.75 / 1.0 / 1.5）
		//noinspection UnnecessaryLocalVariable
		int densityDpi = metric.densityDpi; // 屏幕密度DPI（120 / 160 / 240）
		
		return densityDpi;
		
	}
	
	
}
