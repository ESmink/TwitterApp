package ehi1vsc.saxion.twitterapp.Oauth;

import android.os.AsyncTask;
import android.util.Base64;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Gijs on 31-5-2016.
 */
public class BearerToken extends AsyncTask<String, Double, byte[]>{

    private final static String API_KEY = "b5NRVMTKd9Y6JXjRlIAOGBnkG";
    private final static String API_SECRET = "ye1OOYSifhEjvUsR1adfJs4vLyO1Qi7XSRB96wNtHpTP6kGO5q";
    private final static String CHARSET_UTF_8 = "UTF-8";
    private String bearerToken;

    @Override
    protected byte[] doInBackground(String... params) {
        byte[] body = new byte[0];
        try {

            // Prepare request
            URL url = new URL("https://api.twitter.com/oauth2/token");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            // Encode API key and secret
            String authString = URLEncoder.encode(API_KEY, CHARSET_UTF_8) + ":" + URLEncoder.encode(API_SECRET, CHARSET_UTF_8);

            // Apply Base64 encoding on the encoded string
            String authStringBase64 = Base64.encodeToString(authString.getBytes(CHARSET_UTF_8), Base64.NO_WRAP);

            // Set headers
            conn.setRequestProperty("Authorization", "Basic " + authStringBase64);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

            // Set body
            conn.setDoOutput(true);
            body = "grant_type=client_credentials".getBytes("UTF-8");
            conn.setFixedLengthStreamingMode(body.length);
            BufferedOutputStream os = new BufferedOutputStream(conn.getOutputStream());
            os.write(body);
            os.close();

            // get bearertoken
            InputStream inputStream = conn.getInputStream();
            String response = IOUtils.toString(inputStream, "UTF-8");
            bearerToken = (String) new JSONObject(response).get("access_token");
            IOUtils.closeQuietly(inputStream);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException pe) {
            pe.printStackTrace();
        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            return body;
        }
    }

    public String getBearerToken() {
        return bearerToken;
    }
}
