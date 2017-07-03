package com.csmall;

import com.csmall.android.ApplicationHolder;
import com.csmall.crash.BuglyApi;
import com.csmall.log.LogHelper;
import com.csmall.mail.MailSender;
import com.csmall.report.ReportManager;
import com.csmall.share.MobApi;

/**
 * Created by wangchao on 2017/3/29.
 */

public class LibInitManager {

    public static void initOnAppCreate(LibInitData data){
        ApplicationHolder.setApplication(data.app);
        LogHelper.init();

        BuglyApi.initOnAppCreate(data.buglyAppId);
        ReportManager.initOnAppCreate();
        MailSender.setMailConfig(data.mailConfig);
        MobApi.getInstance();
    }
}
