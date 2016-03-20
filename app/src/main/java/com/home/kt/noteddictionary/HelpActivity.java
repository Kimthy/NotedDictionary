package com.home.kt.noteddictionary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        WebView localWebView = (WebView) findViewById(R.id.webView);
        localWebView.getSettings().setJavaScriptEnabled(true);
        localWebView.loadUrl("file:///android_asset/html/help.html");
    }
}
