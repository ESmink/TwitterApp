package ehi1vsc.saxion.twitterapp;

import android.content.Context;
import android.content.res.AssetManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import ehi1vsc.saxion.twitterapp.Tweet.Tweet;

/**
 * Created by edwin_000 on 25/04/2016.
 */
public class JSONreader {

    /**
     * Reads an asset file and returns a string with the full contents.
     *
     * @param filename  The filename of the file to read.
     * @return          The contents of the file.
     * @throws IOException  If file could not be found or not read.
     */
    private static String readAssetIntoString(String filename, Context context) throws IOException {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            InputStream is = context.getAssets().open(filename, AssetManager.ACCESS_BUFFER);
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    public static void readJSON(Context context){
        try {
            JSONObject object = new JSONObject(readAssetIntoString("tweets.json", context));
            Model.getInstance().getTweets().clear();
            for(int x =0 ; object.getJSONArray("statuses").length() > x;x++){
                Model.getInstance().getTweets().add(
                        new Tweet(object.getJSONArray("statuses").getJSONObject(x)));
            }

        }catch(IOException e){
            e.printStackTrace();
        }catch(org.json.JSONException e){
            e.printStackTrace();
        }
    }
}
