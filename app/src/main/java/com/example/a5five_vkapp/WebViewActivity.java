package com.example.a5five_vkapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.a5five_vkapp.retrofit.RetrofitClient;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebViewActivity extends Activity {

    @BindView(R.id.webview) WebView webView;

    private static final String LOG_TAG = "myLog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);

        ButterKnife.bind(this);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.clearCache(true);

        webView.loadUrl(RetrofitClient.AUTH_URL);
        webView.setVisibility(View.VISIBLE);

        webView.setWebViewClient(new VkWebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                if(url.startsWith(RetrofitClient.REDIRECT_URI)) {

                    url = url.replace('#', '?');
                    final String accessToken = Uri.parse(url).getQueryParameter("access_token");
                    final long userId = Long.valueOf(Uri.parse(url).getQueryParameter("user_id"));

                    if (accessToken != null) {
                        RetrofitClient.getInstance().setAccessToken(accessToken);
                        RetrofitClient.getInstance().setUserId(userId);
                    }

                    Log.d(LOG_TAG, "Access token: " + accessToken);
                    Log.d(LOG_TAG, "User ID: " + String.valueOf(userId));

                    Intent intent = new Intent(getApplicationContext(), LoggedInActivity.class);
                    startActivity(intent);
                }
            }
        });
    }


    class VkWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap icon) {
            super.onPageStarted(view, url, icon);
            parseUrl(url);
        }
    }

    private void parseUrl(String url) {
        try {
            if (url == null) {
                Toast.makeText(getApplicationContext(), "Wrong url", Toast.LENGTH_SHORT).show();
                return;
            }
            if (url.startsWith(RetrofitClient.REDIRECT_URI)) {

                url = url.replace('#', '?');

                final String accessToken = Uri.parse(url).getQueryParameter("access_token");
                final long userId = Long.valueOf(Uri.parse(url).getQueryParameter("user_id"));

                if (null != accessToken) {
                    RetrofitClient.getInstance().setAccessToken(accessToken);
                    RetrofitClient.getInstance().setUserId(userId);
                }

            } else if (url.contains("error")) {
                setResult(RESULT_CANCELED);
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
            setResult(RESULT_CANCELED);
            finish();
        }
    }
}
