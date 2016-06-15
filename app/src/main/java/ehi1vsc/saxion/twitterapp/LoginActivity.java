package ehi1vsc.saxion.twitterapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Verb;

import org.json.JSONObject;

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
                if (url.startsWith("https://www.google")) {
                    Uri uri = Uri.parse(url);
                    Ref.verifier = uri.getQueryParameter("oauth_verifier");
                    new AsyncTask<Object, Object, Object>(){

                        @Override
                        protected Object doInBackground(Object[] params) {
                            Ref.accessToken = Model.getInstance().getTwitterService().getAccessToken(
                                    Ref.requestToken, Ref.verifier);
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Object o) {
                            OAuthRequest request = new OAuthRequest(Verb.GET,
                                    "https://api.twitter.com/1.1/account/verify_credentials.json",
                                    Model.getInstance().getTwitterService());
                            new CommonRequest() {
                                @Override
                                public void finished(JSONObject e) {
                                    Ref.currentUser = Model.getInstance().addUser(new User(e));
                                }
                            }.execute(request, getBaseContext());
                        }
                    }.execute();
                    finish();
                }
                return false;
            }
        });
    }
}
