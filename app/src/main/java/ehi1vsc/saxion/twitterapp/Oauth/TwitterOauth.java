package ehi1vsc.saxion.twitterapp.Oauth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;

import ehi1vsc.saxion.twitterapp.LoginActivity;
import ehi1vsc.saxion.twitterapp.Model;
import ehi1vsc.saxion.twitterapp.Ref;

/**
 * Created by edwin_000 on 23/05/2016.
 */
public class TwitterOauth extends AsyncTask<Context, Context, Void> {

    @Override
    protected Void doInBackground(Context... params) {
        Ref.requestToken = Model.getInstance().getTwitterService().getRequestToken();
        Intent intent = new Intent(params[0], LoginActivity.class);
        intent.putExtra("tokenSite", Model.getInstance().getTwitterService().getAuthorizationUrl(Ref.requestToken));
        params[0].startActivity(intent);
        return null;
    }
}
