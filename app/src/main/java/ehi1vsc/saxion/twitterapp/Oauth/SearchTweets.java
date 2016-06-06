package ehi1vsc.saxion.twitterapp.Oauth;

import android.os.AsyncTask;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Gijs on 6-6-2016.
 */
public class SearchTweets extends AsyncTask<String, Double, byte[]> {

    String response;

    @Override
    protected byte[] doInBackground(String... params) {

        try {
            // prepare request
            URL url = new URL("https://api.twitter.com/1.1/search/tweets.json?q=" + params[1]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.addRequestProperty("Authorization", "Bearer " + params[0]);

            // get tweets
            InputStream inputStream = conn.getInputStream();
            response = IOUtils.toString(inputStream, "UTF-8");
            IOUtils.closeQuietly(inputStream);
            
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }
}
