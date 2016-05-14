package ehi1vsc.saxion.twitterapp.Tweet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ehi1vsc.saxion.twitterapp.User;

/**
 * Created by edwin_000 on 25/04/2016.
 */
public class Tweet {
    private String id_str, text;
    private User user;

    public Tweet(JSONObject jsonObject) {
        try {
            //filling Tweet
            this.id_str = jsonObject.getString("id_str");
            this.text = jsonObject.getString("text");

            int htmloffset = 0;

            //managing Hashtags
            JSONArray hashtags = jsonObject.getJSONObject("entities").getJSONArray("hashtags");
            for (int x = 0; hashtags.length() > x; x++) {
                JSONArray indices = hashtags.getJSONObject(x).getJSONArray("indices");
                text = text.substring(0, indices.getInt(0) + htmloffset) +
                        "<font color='blue'>" +
                        "#" + hashtags.getJSONObject(x).getString("text") +
                        "</font>" +
                        text.substring(indices.getInt(1) + htmloffset);
                htmloffset += 26; //aantal html tekens extra in de text
            }

            //managing Usermentions
            //JSONArray usermentions = jsonObject.getJSONObject("entities").getJSONArray("");

            //managing user
             user = new User(jsonObject.getJSONObject("user"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String getId_str() {
        return id_str;
    }

    public String getText() {
        return text;
    }

    public User getUser() {
        return user;
    }
}
