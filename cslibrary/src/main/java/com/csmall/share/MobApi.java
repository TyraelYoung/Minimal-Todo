package com.csmall.share;

import android.app.Activity;

import com.csmall.android.ApplicationHolder;
import com.csmall.android.ToastUtil;
import com.csmall.log.LogHelper;
import com.csmall.report.ReportManager;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * Created by wangchao on 2015/12/8.
 */
public class MobApi {
    private static final String TAG = "MobApi";
    private static MobApi sMobApi = new MobApi();

    public static MobApi getInstance() {
        return sMobApi;
    }

    private boolean mIsInited;

    private MobApi() {
        init();
    }

    private boolean init() {
        if (mIsInited) {
            return true;
        }
        try {
            ShareSDK.initSDK(ApplicationHolder.getApplication());
            mIsInited = true;
            return true;
        } catch (NullPointerException npe) {
            ToastUtil.show("分享组件初始化失败。请稍后重试。");
            ReportManager.getInstance().reportException(TAG, npe);
            return false;
        }
    }

    public void share(final ShareData data) {
        if(!init()){
            return;
        }
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(data.title);
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(data.url);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(data.content);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        oks.setImageUrl(data.imageUrl);
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(data.url);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
//        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
//        oks.setSiteUrl(data.url);

        // 令编辑页面显示为Dialog模式
//        oks.setDialogMode();
        oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
            @Override
            public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
                if (WechatMoments.NAME.equals(platform.getName())) {
                    // paramsToShare.setShareType(Platform.SHARE_WEBPAGE);
                    String s = null;
                    if (data.title != null) {
                        s = data.title;
                    }
                    if (data.content != null) {
                        if (s != null) {
                            s = s + data.content;
                        } else {
                            s = data.content;
                        }
                    }
                    paramsToShare.setTitle(s);
                }

                if (SinaWeibo.NAME.equals(platform.getName())) {
                    String s = "";
                    if (data.content != null) {
                        s = s + data.content;
                    }
                    if (data.url != null) {
                        s += " " + data.url;
                    }
//                    paramsToShare.setTitle(s);
                    paramsToShare.setText(s);
                }
            }
        });
// 启动分享GUI

        oks.setCallback(new PlatformActionListener() {


            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                LogHelper.d("shared"," onComplete");
                //sharedAppGetIntegration
//                Map<String,String> parame = new HashMap<String, String>();
//                parame.put("event","APP分享");
//                CSHttpHelper.post(UrlHelper.sharedAppGetIntegration, parame, new RequestCallBack<String>() {
//                    @Override
//                    public void onSuccess(ResponseInfo<String> responseInfo) {
//                        LogHelper.d("shared","onSuccess msg "+responseInfo.result);
//                        SharedSuccessModel model = JSONUtils.changeGsonToBean(responseInfo.result,SharedSuccessModel.class);
//                        if (model != null && model.isSuccess() && model.getData() != null){
//                            if (model.getData().getIntegral() < 50){
//                                ToastUtil.show("分享成功！");
//                            }else {
//                                ToastUtil.show(model.getData().getDesc()+" +"+model.getData().getIntegral()+"积分");
//                            }
//
//                        }else {
//                            onFailure(null,"分享失败");
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(HttpException error, String msg) {
//                        ToastUtil.show("分享失败");
//                    }
//                });
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                LogHelper.d("shared"," onError");
            }

            @Override
            public void onCancel(Platform platform, int i) {
                LogHelper.d("shared"," onCancel");
            }
        });
        oks.show(data.activity);
    }

    public void test(Activity activity){
        ShareData shareData = new ShareData();
        shareData.activity = activity;
        shareData.content = "tittle";
        shareData.title = "tittle1";
        share(shareData);
    }
}

