package ehi1vsc.saxion.twitterapp.Oauth;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by edwin_000 on 23/05/2016.
 */
public abstract class TwitterOauth extends AsyncTask {
    private String stringUrl;
    private String requestMethod;
    ArrayList<String[]> properties = new ArrayList<>();

    public TwitterOauth(String stringUrl, String requestMethod) {
        this.stringUrl = stringUrl;
        this.requestMethod = requestMethod;
    }

    /**
     * params:
     * 0 = String url
     * 1 = String requestmethod(GET | POST)
     * 2 = List<String[2]> properties
     */

    @Override
    protected Object doInBackground(Object[] params) {
        try {
            //setting the header
            HttpsURLConnection con = (HttpsURLConnection) (new URL(stringUrl)).openConnection();
            con.setRequestMethod(requestMethod);
            for (String[] keyValue : properties) {
                con.setRequestProperty(keyValue[0], keyValue[1]);
            }

            Log.d("Response:", con.getResponseCode() + "");

            //reading response
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            System.out.println(response.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
