package com.godavari.appsnest.covid19india.activity;

import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.URL;

public class MyWebViewClient extends WebViewClient {

    private static final String LOG_TAG = MyWebViewClient.class.getSimpleName();

    private static final String FORM_RESPONSE_STRING = "formResponse";

    private MainActivity mainActivity;

    public MyWebViewClient(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    // necessary so that, hyperlink thats not open in other application on click
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.i(LOG_TAG, "shouldOverrideUrlLoading: " + url);
        // This line right here is what you're missing.
        // Use the url provided in the method.  It will match the member URL!
        view.loadUrl(url);
        return true;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        Log.i(LOG_TAG, "onPageFinished: " + url);
        super.onPageFinished(view, url);
        mainActivity.stopProgressDialog();
    }

    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        Log.i(LOG_TAG, "onPageStarted, url: " +url);
        super.onPageStarted(view, url,favicon);
        mainActivity.startProgressDialog();
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        Log.i(LOG_TAG, "onReceivedError, errorCode: " + errorCode + ", description: " + description + ", failingUrl: " + failingUrl);
        if (!Utility.isInternetConnected()) {
            mainActivity.noInternetConnection();
        }

        super.onReceivedError(view, errorCode, description, failingUrl);
    }
}
