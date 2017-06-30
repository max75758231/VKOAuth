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

public class WebViewActivity extends Activity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);

        webView = (WebView) findViewById(R.id.web);

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

                    if (null != accessToken) {
                        RetrofitClient.getInstanse().setAccessToken(accessToken);
                        RetrofitClient.getInstanse().setUserId(userId);
                    }

                    Log.d("AAAAAAAAAAAAAAAAAAAAA:", accessToken);
                    Log.d("BBBBBBbb:", String.valueOf(userId));

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
                    RetrofitClient.getInstanse().setAccessToken(accessToken);
                    RetrofitClient.getInstanse().setUserId(userId);
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
