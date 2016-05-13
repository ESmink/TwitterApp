package ehi1vsc.saxion.twitterapp.Tweet;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Gijs on 11-5-2016.
 */
public class Url extends Entity {
    private String url, display_url;

    public Url(JSONObject jsonObject) {
        super(jsonObject);
        try {
            url = jsonObject.getString("url");
            display_url = jsonObject.getString("display_url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
