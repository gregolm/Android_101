package com.example.gregolm.android_101.activities;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.gregolm.android_101.R;


public class CreditsActivity extends Activity {

    WebView wv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
        wv = (WebView) findViewById(R.id.webView);
        wv.loadUrl("file:///android_asset/credits.html");
    }
}
