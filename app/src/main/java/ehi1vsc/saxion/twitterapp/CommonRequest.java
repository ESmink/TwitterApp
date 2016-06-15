package ehi1vsc.saxion.twitterapp;

import android.os.AsyncTask;
import android.util.Log;

import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Gijs on 14-6-2016.
 */
public abstract class CommonRequest extends AsyncTask<OAuthRequest, OAuthRequest, String> {

    @Override
    protected String doInBackground(OAuthRequest... params) {
        OAuthRequest request = params[0];
        Model.getInstance().getTwitterService().signRequest(Ref.accessToken, request);
        Response response = request.send();
        if (response.isSuccessful()) {
            String res = response.getBody();
            Log.d("res ", res);
            return res;
        }
        return null;
    }

    @Override
    protected abstract void onPostExecute(String s);
}
