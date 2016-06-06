package ehi1vsc.saxion.twitterapp.Oauth;

import android.os.AsyncTask;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import ehi1vsc.saxion.twitterapp.MainActivity;
import ehi1vsc.saxion.twitterapp.Tweet.Tweet;
import ehi1vsc.saxion.twitterapp.TweetAdapter;

/**
 * Created by Gijs on 6-6-2016.
 */
public class SearchTweets extends AsyncTask<String, Double, byte[]> {

    String response;
    ArrayList<Tweet> tweets;

    @Override
    protected byte[] doInBackground(String... params) {

        try {

            // prepare request
            URL url = new URL("https://api.twitter.com/1.1/search/tweets.json?q=" + params[1]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // set header
            conn.addRequestProperty("Authorization", "Bearer " + params[0]);

            // Set body
            conn.setDoOutput(true);
            byte[] body = "grant_type=client_credentials".getBytes("UTF-8");
            conn.setFixedLengthStreamingMode(body.length);
            BufferedOutputStream os = new BufferedOutputStream(conn.getOutputStream());
            os.write(body);
            os.close();

            // get tweets
            InputStream inputStream = conn.getInputStream();
            response = IOUtils.toString(inputStream, "UTF-8");
            IOUtils.closeQuietly(inputStream);

            return body;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    @Override
    protected void onPostExecute(byte[] bytes) {
        try {

            JSONObject jsonObject = new JSONObject(response);
            tweets = new ArrayList<>();
            for (int i = 0; i < jsonObject.getJSONArray("statuses").length(); i++) {
                tweets.add(new Tweet(jsonObject.getJSONArray("statuses").getJSONObject(i)));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Tweet> getTweets() {
            return tweets;
    }
}
