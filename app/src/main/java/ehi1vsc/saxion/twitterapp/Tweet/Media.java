package ehi1vsc.saxion.twitterapp.Tweet;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Gijs on 11-5-2016.
 */
public class Media extends Entity {
    private String id_str;
    private String media_url, url, display_url;
    private String type;

    public Media(JSONObject jsonObject) {
        super(jsonObject);
        try {
            id_str = jsonObject.getString("id_str");
            media_url = jsonObject.getString("media_url");
            url = jsonObject.getString("url");
            display_url = jsonObject.getString("display_url");
            type = jsonObject.getString("type");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
