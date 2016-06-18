package ehi1vsc.saxion.twitterapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

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
        }else if(response.getCode() == 401){
            return "[]";
        }
        return null;
    }

    protected void onPostExecute(String s) {
        try {
            finished(new JSONObject(s));
        } catch (JSONException | NullPointerException e) {
            context.startActivity(new Intent(context, LoginActivity.class));
        }
    }

    public abstract void finished(JSONObject e);
}
