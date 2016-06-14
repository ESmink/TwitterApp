package ehi1vsc.saxion.twitterapp.Oauth;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import ehi1vsc.saxion.twitterapp.MainActivity;
import ehi1vsc.saxion.twitterapp.Ref;
import ehi1vsc.saxion.twitterapp.Tweet.Tweet;

/**
 * Created by Gijs on 6-6-2016.
 */
public class SearchTweets extends AsyncTask<MainActivity, Double, Object> {

    String response;

    @Override
    protected MainActivity doInBackground(MainActivity... params) {
        try {

            // prepare request
            URL url = new URL("https://api.twitter.com/1.1/search/tweets.json?q=" + params[0].text);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // set header
            conn.addRequestProperty("Authorization", "Bearer " + Ref.bearertoken);

            Log.d("responseCode: ", conn.getResponseCode() + "");

            // Set body
//            conn.setDoOutput(true);
//            byte[] body = "grant_type=client_credentials".getBytes("UTF-8");
//            conn.setFixedLengthStreamingMode(body.length);
//            BufferedOutputStream os = new BufferedOutputStream(conn.getOutputStream());
//            os.write(body);
//            os.close();

            // get tweets
            InputStream inputStream = conn.getInputStream();
            response = IOUtils.toString(inputStream, "UTF-8");
            IOUtils.closeQuietly(inputStream);

            return params[0];

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onPostExecute(Object o) {
        try {


            JSONObject jsonObject = new JSONObject(response);

            ArrayList<Tweet> tweets = new ArrayList<>();
            for (int x = 0; jsonObject.getJSONArray("statuses").length() > x; x++) {
                tweets.add(new Tweet(jsonObject.getJSONArray("statuses").getJSONObject(x)));
            }

            ((MainActivity)o).setSearchTweetsList(tweets);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
