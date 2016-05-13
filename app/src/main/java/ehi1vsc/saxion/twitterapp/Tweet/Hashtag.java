package ehi1vsc.saxion.twitterapp.Tweet;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Gijs on 11-5-2016.
 */
public class Hashtag extends Entity {
    private String text;

    public Hashtag(JSONObject jsonObject) {
        super(jsonObject);
        try {
            text = jsonObject.getString("text");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getText() {
        return text;
    }

}
