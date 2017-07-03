package com.csmall.net;

import okhttp3.HttpUrl;

/**
 * Created by wangchao on 2017/3/2.
 */

public class UrlCheck {
    /**
     * 有些url不完整
     * @param url
     * @return
     */
    public static String check(String url){
        if(url.startsWith("//")){
            return String.format("https:%s", url);
        }
        return url;
    }

    public static boolean isUrl(String url){
        if (url == null) return false;

        // Silently replace web socket URLs with HTTP URLs.
        if (url.regionMatches(true, 0, "ws:", 0, 3)) {
            url = "http:" + url.substring(3);
        } else if (url.regionMatches(true, 0, "wss:", 0, 4)) {
            url = "https:" + url.substring(4);
        }

        HttpUrl parsed = HttpUrl.parse(url);
        if (parsed == null) return  false;
        return true;
    }
}
