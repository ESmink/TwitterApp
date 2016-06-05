package ehi1vsc.saxion.twitterapp.Oauth;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by edwin_000 on 23/05/2016.
 */
public abstract class TwitterOauth extends AsyncTask {
    /**
     * params:
     * 0 = String url
     * 1 = String requestmethod(GET | POST)
     * 2 = List<String[2]> properties
     */

    @Override
    protected Object doInBackground(Object[] params) {
        try {
            HttpsURLConnection con = (HttpsURLConnection) (new URL((String) params[0])).openConnection();
            con.setRequestMethod((String) params[1]);
            for (String[] keyValue : (List<String[]>) params[2]) {
                con.setRequestProperty(keyValue[0], keyValue[1]);
            }

            if (params[3] != null){
                con.setDoOutput(true);
                con.setFixedLengthStreamingMode(((byte[]) params[3]).length);
                BufferedOutputStream os = new BufferedOutputStream(con.getOutputStream());
                os.write(((byte[]) params[3]));
                os.close();
            }

            int responsecode = con.getResponseCode();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result

            Log.d("Our response", response.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
