package com.codepath.apps.mysimpletweets;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import java.math.BigDecimal;

public class TwitterClient extends OAuthBaseClient {
    public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class;
    public static final String REST_URL = "https://api.twitter.com/1.1";
    public static final String REST_CONSUMER_KEY = "VCwcAIMuW3hhia4GpttjSiVMd";
    public static final String REST_CONSUMER_SECRET = "QFkqV5HIJqMsumwhd8ETgVF6xggfCX5tBoa3Y7TWd1dS1uUIA2";
    public static final String REST_CALLBACK_URL = "oauth://hetsimpletweets";

    public TwitterClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }

    public void getHomeTimeline(String maxId, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("/statuses/home_timeline.json");
        // Can specify query string params directly or through RequestParams.
        RequestParams params = new RequestParams();
        params.put("count", 15);

        if (null != maxId) {
            maxId = new BigDecimal(maxId).subtract(BigDecimal.ONE).toString();//for scrolling and getting older tweets
            params.put("max_id", maxId);
        }

        getClient().get(apiUrl, params, handler);
    }

    public void getAccount(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("account/verify_credentials.json");
        getClient().get(apiUrl, null, handler);
    }

    public void compose(String tweet, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("/statuses/update.json");
        RequestParams params = new RequestParams();
        params.put("status", tweet);
        getClient().post(apiUrl, params, handler);
    }
}