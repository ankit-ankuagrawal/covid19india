package com.godavari.appsnest.covid19india.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.godavari.appsnest.covid19india.R;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    public static final String URL = "https://www.covid19india.org";

    private WebView webView;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new MyWebViewClient(this));
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl(URL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.miRefresh:
                refresh();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void refresh() {
        Log.i(LOG_TAG, "refresh");
        webView.post(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl(URL);
            }
        });
    }

    public void startProgressDialog() {
        Log.i(LOG_TAG, "startProgressDialog");
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading_form));
        progressDialog.show();
    }

    public void stopProgressDialog() {
        Log.i(LOG_TAG, "stopProgressDialog");
        if (progressDialog != null) {
            progressDialog.cancel();
        }
    }

    public void noInternetConnection() {
        Log.i(LOG_TAG, "noInternetConnection");
        webView.loadUrl("file:///android_asset/no_internet_connection.html");
        Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
    }
}
