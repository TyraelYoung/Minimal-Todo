package com.csmall.net;

import com.csmall.log.LogHelper;
import com.csmall.ui.DensityUtil;

/**
 * Created by wangchao on 2015/10/17.
 */
public class ImageUrlHelper {
    public static final String IMAGE_ROOT = "http://img3.51ydgymall.cn/";
    private static final String TAG = "ImageUrlHelper";

    /**
     * 如果不完整，则添加。否则直接返回
     * @param url
     * @param width
     * @param height
     * @return
     */
    public static String checkUrl(String url, int width, int height) {
        if(width == 0){
            width = DensityUtil.getScreenWidth();
        }
        if(height == 0){
            height = DensityUtil.getScreenHeight();
        }
        return LoadImgHaveHead(url, width, height, 90);
    }

    /**
     *
     * @param url
     * @param square 正方形边长
     * @return
     */
    public static String checkUrl(String url, int square) {
        if(square == 0){
            square = DensityUtil.getScreenWidth();
        }
        return LoadImgHaveHead(url, square, square, 90);
    }

    public static String checkUrl(String url){
        return checkUrl(url, 0);
    }

    public static String LoadImgHaveHead(String url, int width, int height,
                                         int quality) {

        String imgQuality;

        if (height > 0 && quality > 0) {
            imgQuality = "/app/" + width + "_" + height + "_" + quality;
        } else {
            imgQuality = "/app/" + width;
        }

        if (url != null && url.trim().length() > 0) {
            boolean ishave = url.startsWith("http://") ||  url.startsWith("https://");
            if (ishave) {
                return url;
            } else {
                if (url.lastIndexOf("/") > 1) {
                    url = new StringBuffer(url).insert(url.lastIndexOf("/"),
                            imgQuality).toString();

                }
                url = "http://img3.51ydgymall.cn/"+url;
                //url = "https://img3.csmall.com/" + url;
            }
        }
        LogHelper.i(TAG, "url:" + url);
        return url;
    }

    /**
     * 图片问题
     *
     * @param url
     * @return
     */
    public static String LoadImgHaveHead(String url) {

        if (url != null && url.trim().length() > 0) {
            boolean ishave = url.startsWith("http://") || url.startsWith("https://");
            if (ishave) {
                return url;
            } else {
                url = ImageUrlHelper.IMAGE_ROOT + url;
            }
        }
        LogHelper.i(TAG, "url:" + url);
        return url;
    }
}
