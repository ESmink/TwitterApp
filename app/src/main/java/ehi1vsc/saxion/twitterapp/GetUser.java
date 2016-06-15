package ehi1vsc.saxion.twitterapp;

import android.os.AsyncTask;
import android.util.Log;

import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;

/**
 * Created by Gijs on 14-6-2016.
 */
public class GetUser extends AsyncTask<Void, Void, String> {

    @Override
    protected String doInBackground(Void... params) {
        
        OAuthRequest request = new OAuthRequest(Verb.GET,
                "https://api.twitter.com/1.1/account/verify_credentials.json",
                Model.getInstance().getTwitterService());

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
    protected void onPostExecute(String s) {
        UserActivity.setUser(s);
    }
}
