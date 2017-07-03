package com.csmall.net.ordinary;

import java.util.Map;

/**
 * Created by wangchao on 2017/3/7.
 */

public class RequestData {
    public String method = "GET";
    public String url;
    public Map<String, String> headers;
    public RequestListener listener;

    public Map<String, String> params;
    public String body;

    public RequestData copy(){
        RequestData data = new RequestData();
        data.listener = this.listener;
        data.body = this.body;
        data.headers = this.headers;
        data.method = this.method;
        data.params = this.params;
        data.url = this.url;
        return data;
    }
}
