package wang.tyrael.todo;

import android.app.Application;

import com.csmall.LibInitData;
import com.csmall.LibInitManager;
import com.csmall.android.IMainProcessInitManager;
import com.csmall.mail.IMailConfig;

/**
 * Created by wangchao on 2017/4/1.
 */

public class MainProcessInitManager implements IMainProcessInitManager {
    public static MainProcessInitManager inst = new MainProcessInitManager();

    @Override
    public void onAppCreate(Application app) {
        LibInitData data =new LibInitData();
        data.app = app;
        data.buglyAppId = "4077ac6992";
        data.mailConfig = new IMailConfig() {
            @Override
            public String getAccount() {
                return "1544713292@qq.com";
            }

            @Override
            public String getSmtp() {
                return "smtp.qq.com";
            }

            @Override
            public String getPassword() {
                return "bxmjzfpktlrgjibc";
            }
        };
        LibInitManager.initOnAppCreate(data);
    }

    @Override
    public void onMainActivityCreate() {

    }

    @Override
    public void onIdle() {

    }
}
