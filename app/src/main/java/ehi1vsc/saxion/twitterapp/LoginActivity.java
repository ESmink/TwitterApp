package ehi1vsc.saxion.twitterapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        WebView web = (WebView) findViewById(R.id.webView);

        Intent intent = getIntent();
        Log.d("Surfing to:", intent.getStringExtra("tokenSite"));
        web.loadUrl(intent.getStringExtra("tokenSite"));
        web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d("heading to ", url);
                if (url.startsWith("https://www.google")) {
                    Uri uri = Uri.parse(url);
                    Ref.vertifier = uri.getQueryParameter("oauth_verifier");
                    new AsyncTask<Object, Object, Object>(){

                        @Override
                        protected Object doInBackground(Object[] params) {
                            Ref.accessToken = Model.getInstance().getTwitterService().getAccessToken(
                                    Ref.requestToken, Ref.vertifier);
                            Log.d("access token:" , Ref.accessToken.getToken());
                            return null;
                        }
                    }.execute();
                    finish();
                }
                return false;
            }
        });
    }
}
