package ehi1vsc.saxion.twitterapp.Oauth;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by Gijs on 30-5-2016.
 */
public class GetBearerToken extends TwitterOauth {

    private final static String API_KEY = "b5NRVMTKd9Y6JXjRlIAOGBnkG";
    private final static String API_SECRET = "tye1OOYSifhEjvUsR1adfJs4vLyO1Qi7XSRB96wNtHpTP6kGO5q";
    private final static String CHARSET_UTF_8 = "UTF-8";


    public GetBearerToken() {
        ArrayList list = new ArrayList();
        list.add("https://api.twitter.com/oauth2/token");
        list.add("POST");

        try {
            // Encode API key and secret
            String authString = URLEncoder.encode(API_KEY, CHARSET_UTF_8) + ":" + URLEncoder.encode(API_SECRET, CHARSET_UTF_8);

            // Apply Base64 encoding on the encoded string
            String authStringBase64 = Base64.encodeToString(authString.getBytes(CHARSET_UTF_8), Base64.NO_WRAP);

            ArrayList<String[]> propeties = new ArrayList<>();
            propeties.add(new String[] {"Authorization", "Basic " + authStringBase64});
            propeties.add(new String[] {"Content-Type", "application/x-www-form-urlencoded;charset=UTF-8"});

            list.add(propeties);
            list.add("grant_type=client_credentials".getBytes("UTF-8"));

            super.execute(list.toArray());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}
