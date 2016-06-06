package ehi1vsc.saxion.twitterapp.Oauth;

import android.os.AsyncTask;
import android.util.Log;

import com.github.scribejava.core.model.OAuth1RequestToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import ehi1vsc.saxion.twitterapp.Model;

/**
 * Created by edwin_000 on 23/05/2016.
 */
public class TwitterOauth extends AsyncTask {

    @Override
    protected Object doInBackground(Object[] params) {
        OAuth1RequestToken rToken =  Model.getInstance().getTwitterService().getRequestToken();
        String url = Model.getInstance().getTwitterService().getAuthorizationUrl(rToken);
        Log.d("user url =" , url);
        return null;
    }
}
