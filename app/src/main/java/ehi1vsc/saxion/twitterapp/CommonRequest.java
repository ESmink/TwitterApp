package ehi1vsc.saxion.twitterapp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import ehi1vsc.saxion.twitterapp.Oauth.TwitterOauth;

/**
 * Created by Gijs on 14-6-2016.
 */
public abstract class CommonRequest extends AsyncTask<Object, Object, String> {
    Context context;

    @Override
    protected String doInBackground(Object... params) {
        OAuthRequest request = (OAuthRequest) params[0];
        context = (Context) params[1];
        if (Ref.accessToken == null) {
            return null;
        }
        Model.getInstance().getTwitterService().signRequest(Ref.accessToken, request);
        Response response = request.send();
        if (response.isSuccessful()) {
            String res = response.getBody();
            Log.d("res ", res);
            return res;
        }
        return null;
    }

    protected void onPostExecute(String s) {
        try {
            finished(new JSONObject(s));
        } catch (JSONException | NullPointerException e) {
            new TwitterOauth().execute(context);
        }
    }

    public abstract void finished(JSONObject e);
}
