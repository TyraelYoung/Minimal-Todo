package com.csmall.android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;

import com.csmall.log.LogHelper;

import java.util.Timer;
import java.util.TimerTask;

/**
 * web假进度条，前进到90%停止。
 * Created by wangchao on 2015/8/31.
 */
public class WebProgressBar extends ProgressBar {
    //预计打开一个网页10秒完成
    private static final int EXPECT_TIME = 3 * 1000;
    private static final int UPDTATE_PERIOD =  200;

    private static final int PROGRESS_MAX = 100;
    private static final int PROGRESS_FALSE_END = 90;

    //100%以后，等待一秒消失。
    private static final int DURATION_GONE = 1000;

    private volatile boolean mIsFinished;
    private int mFlaseProgress;
    private long mStartTime;

    private Timer mTimer;

    public WebProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        setMax(PROGRESS_MAX);
    }

    /**
     * 可以多次启动
     */
    public void onStart(){
        LogHelper.d("onStart");
        mIsFinished = false;
        if(mTimer != null){
            mTimer.cancel();
        }
        setVisibility(View.VISIBLE);
        mStartTime = System.currentTimeMillis();
        mTimer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if(mIsFinished){
                    post(new Runnable() {
                        @Override
                        public void run() {
                            setProgress(PROGRESS_MAX);
                        }
                    });
                }else{
                    float rate = (System.currentTimeMillis() - mStartTime) / (float)EXPECT_TIME;
                    mFlaseProgress = (int) (PROGRESS_MAX * rate);
                    if(mFlaseProgress < PROGRESS_FALSE_END){
                        post(new Runnable() {
                            @Override
                            public void run() {
                                setProgress(mFlaseProgress);
//                                LogHelper.d("mFlaseProgress:" + mFlaseProgress);
                            }
                        });
                    }else{
                        mTimer.cancel();
                    }
                }
            }
        };
        mTimer.schedule(timerTask, 0, UPDTATE_PERIOD);
    }

    public void onFinish(){
        LogHelper.d("onFinish");
        mIsFinished = true;
        if(mTimer != null){
            mTimer.cancel();
        }
         setProgress(PROGRESS_MAX);
        postDelayed(new Runnable() {
            @Override
            public void run() {
                setVisibility(View.GONE);
            }
        }, DURATION_GONE);
    }
}
