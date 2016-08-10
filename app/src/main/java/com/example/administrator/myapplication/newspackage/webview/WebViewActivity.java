package com.example.administrator.myapplication.newspackage.webview;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.common.base.BaseActivity;

/**
 * Created by zhanghao on 2016/8/10.
 */
public class WebViewActivity extends BaseActivity {

    private WebView webView;
    private TextView tvTitle;
    private ImageView btnBack;

    private String webTitle;
    private String url;

    @Override
    protected void setMainContentView() {
        setContentView(R.layout.activity_webview);
    }

    @Override
    protected void initData() {
        webTitle = getIntent().getStringExtra("title");
        url = getIntent().getStringExtra("url");
    }

    @Override
    protected void initView() {
        webView = (WebView) findViewById(R.id.webview);
        tvTitle = (TextView) findViewById(R.id.title);
        btnBack = (ImageView) findViewById(R.id.back);
        btnBack.setOnClickListener(this);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                webView.loadUrl(url);
                return false;
            }
        });

        setData();
    }

    private void setData() {
        tvTitle.setText(webTitle);
        webView.loadUrl(url);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }
}
